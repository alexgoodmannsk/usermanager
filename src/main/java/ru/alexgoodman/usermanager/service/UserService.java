package ru.alexgoodman.usermanager.service;

import ru.alexgoodman.usermanager.controller.Filter;
import ru.alexgoodman.usermanager.model.User;

import java.util.List;
import java.util.Map;


public interface UserService {
    public void addUser(User user);

    public void editUser(User user);

    public void deleteUser(int id);

    public User getUserById(int id);

    public Map<Integer,List<User>> userListMap(Filter filter, int numLineOfPage);

    public int userListOnPage(Filter filter, int numLineOfPage, int page, List<User> result);

    public int getPageById(Filter filter, int numLineOfPage, int id);

    public boolean validNumPage(Filter filter, int numLineOfPage, int page);
}
