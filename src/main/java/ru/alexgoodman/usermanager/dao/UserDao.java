package ru.alexgoodman.usermanager.dao;

import ru.alexgoodman.usermanager.controller.Filter;
import ru.alexgoodman.usermanager.model.User;

import java.util.List;
import java.util.Map;

public interface UserDao {
    public void addUser(User user);

    public void editUser(User user);

    public void deleteUser(int id);

    public User getUserById(int id);

    public List<User> userList(Filter filter);
}
