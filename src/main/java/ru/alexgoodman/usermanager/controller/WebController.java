package ru.alexgoodman.usermanager.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.alexgoodman.usermanager.model.User;
import ru.alexgoodman.usermanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class WebController {
    private static final Logger logger = LoggerFactory.getLogger(WebController.class);
    private UserService userService;
    private Filter filter;
    private Paging paging;

    @Autowired(required = true)
    public void setFilter(Filter filter) {
        this.filter = filter;
    }

    @Autowired(required = true)
    public void setPaging(Paging paging) {
        this.paging = paging;
    }

    @Autowired(required = true)
    @Qualifier(value = "userService")
    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    //Точка входа в наше веб-приложение
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String generalPage() {
        return "redirect:/users";
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String userList(@RequestParam(value="page", required=false, defaultValue = "1") Integer page,Model model){
        //создаем список userList и заполняем его, передавая его в качестве параметра в userService.userListOnPage
        List<User> userList = new ArrayList<>();
        //countPage количество страниц при текущих параметрах отображения
        int countPage = userService.userListOnPage(filter, paging.getLineOfPage(), page, userList);
        model.addAttribute("numLineOfPage", paging);
        model.addAttribute("userFilter", filter);
        model.addAttribute("currentPage", page);
        model.addAttribute("countPage", countPage);
        model.addAttribute("user", new User());
        model.addAttribute("userList", userList);
        return "/users";
    }


    @RequestMapping(value = "/users/add", method = RequestMethod.POST)
    public String addUser(@Valid@ModelAttribute("user") User user, BindingResult bindingResult){
        //Проводим валидацию введенных параметров
        if (bindingResult.hasErrors()) {
            return "/errorform";
        }
        userService.addUser(user);
        int page = userService.getPageById(filter, paging.getLineOfPage(), user.getId());
        return "redirect:/users?page="+page;
    }

    @RequestMapping(value = "/users/edit", method = RequestMethod.POST)
    public String editUser(@Valid@ModelAttribute("user") User user, BindingResult bindingResult){
        //Проводим валидацию введенных параметров
        if (bindingResult.hasErrors()) {
            return "/errorform";
        }
        userService.editUser(user);
        return "redirect:/users?page=" + userService.getPageById(filter, paging.getLineOfPage(),user.getId());
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteUser(@PathVariable("id") int id){
        userService.deleteUser(id);
        int page = userService.getPageById(filter,paging.getLineOfPage(),id);
        //при удалении элемента может произойти так что колво страниц после удаления
        //уменьшилось на 1, сдесь учитываем это
        if(!userService.validNumPage(filter, paging.getLineOfPage(), page)) page = page-1;
        return "redirect:/users?page=" + page;
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editUser(@PathVariable("id") int id, Model model){
        //создаем список userList и заполняем его, передавая его в качестве параметра в userService.userListOnPage
        List<User> userList = new ArrayList<>();
        //тут получаем текущую страницу отображения
        int page = userService.getPageById(filter,paging.getLineOfPage(),id);
        //тут получаем общее колво страниц для отображения
        int countPage = userService.userListOnPage(filter, paging.getLineOfPage(), page, userList);
        model.addAttribute("numLineOfPage", paging);
        model.addAttribute("userFilter", filter);
        model.addAttribute("user", this.userService.getUserById(id));
        model.addAttribute("currentPage", page);
        model.addAttribute("countPage", countPage);
        model.addAttribute("userList", userList);

        return "/users";
    }

    @RequestMapping(value = "/users/filter", method = RequestMethod.POST)
    public String listFilter(@ModelAttribute("filter") Filter filter) {
        this.filter = filter;
        return "redirect:/users";
    }

    @RequestMapping(value = "/users/numLineOfPage", method = RequestMethod.POST)
    public String listFilter(@ModelAttribute("numLineOfPage") Paging paging) {
        this.paging = paging;
        this.paging.setLineOfPage(paging.getLineOfPage());
        return "redirect:/users";
    }
}
