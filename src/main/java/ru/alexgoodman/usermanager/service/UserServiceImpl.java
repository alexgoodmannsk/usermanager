package ru.alexgoodman.usermanager.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.alexgoodman.usermanager.controller.Filter;
import ru.alexgoodman.usermanager.dao.UserDao;
import ru.alexgoodman.usermanager.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    @Transactional
    public void addUser(User user) {
        this.userDao.addUser(user);
    }

    @Override
    @Transactional
    public void editUser(User user) {
        this.userDao.editUser(user);
    }

    @Override
    @Transactional
    public void deleteUser(int id) {
        this.userDao.deleteUser(id);
    }

    @Override
    @Transactional
    public User getUserById(int id) {
        return this.userDao.getUserById(id);
    }


    //возвращает Map значений выбранных из БД по заданным параметрам
    //filter и numLineOfPage(кол-во строк в таблице для отображения)
    //в качестве ключа используется номер страницы начиная с 1
    //в качестве значения список пользователей на этой странице
    @Override
    @Transactional
    public Map<Integer, List<User>> userListMap(Filter filter, int numLineOfPage) {
        Map<Integer, List<User>> resultMap = new HashMap<>();
        List<User> userList = this.userDao.userList(filter);
        int count = 1;
        for(int i=0; i<userList.size();) {
            List<User> currentList = new ArrayList<>();
            for (int j = 0; j < numLineOfPage; j++) {
                if(i<userList.size()) currentList.add(userList.get(i++));
                else break;
            }
            resultMap.put(count++, currentList);
        }
        return resultMap;
    }


    //Возвращает общее количество страниц при текущих настройках отображения
    //настройки отображения передаются параметрами filter и numLineOfPage(кол-во строк в таблице для отображения)
    //также заполняет список result пользователями текущей страницы,
    // поэтому этот список должет быть создан до передачи сюда его ссылки
    //текущая страница задается параметром page
    @Override
    @Transactional
    public int userListOnPage(Filter filter, int numLineOfPage, int page, List<User> result) {
        Map<Integer, List<User>> resultMap = this.userListMap(filter, numLineOfPage);
        if(resultMap.size()!=0){
            result.addAll(resultMap.get(page));
            return resultMap.size();
        }else return 1;
    }

    //Возвращает номер страницы к которому принадлежит пользователь с текущим id,
    //с текущими настройками отображения filter и numLineOfPage(кол-во строк в таблице для отображения)
    @Override
    @Transactional
    public int getPageById(Filter filter, int numLineOfPage, int id) {
        if(filter.isEnabled()&&!getUserById(id).getName().equals(filter.getFilterName())) return 1;
        else {
            Map<Integer, List<User>> resultMap = this.userListMap(filter, numLineOfPage);
            int count = 1;
            for (int i = 1; i <= resultMap.size(); i++) {
                List<User> curList = resultMap.get(i);
                if (curList.get(curList.size() - 1).getId() >= id) break;
                else count++;
            }
            return count;
        }
    }

    //Проверяет валидность указанного номера страницы, при текущих параметрах отображения
    //filter и numLineOfPage(кол-во строк в таблице для отображения)
    @Override
    @Transactional
    public boolean validNumPage(Filter filter, int numLineOfPage, int page) {
        return this.userListMap(filter, numLineOfPage).size()>=page;
    }
}
