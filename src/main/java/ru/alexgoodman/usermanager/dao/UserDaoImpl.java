package ru.alexgoodman.usermanager.dao;

import org.hibernate.Query;
import ru.alexgoodman.usermanager.controller.Filter;
import ru.alexgoodman.usermanager.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public class UserDaoImpl implements UserDao {
    private static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void addUser(User user) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(user);
        logger.info("User added: " + user);
    }

    @Override
    public void editUser(User user) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(user);
        logger.info("User updated: " + user);
    }

    @Override
    public void deleteUser(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        User user = (User) session.load(User.class, new Integer(id));

        if(user !=null){
            session.delete(user);
        }
        logger.info("User removed: " + user);
    }

    @Override
    public User getUserById(int id) {
        Session session =this.sessionFactory.getCurrentSession();
        User user = (User) session.load(User.class, new Integer(id));
        logger.info("User loaded: " + user);

        return user;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> userList(Filter filter) {
        Session session = this.sessionFactory.getCurrentSession();
        List<User> userList;
        if(filter==null||!filter.isEnabled()||filter.getFilterName().isEmpty()){
            userList = session.createQuery("from User").list();
        }else {
            Query query = session.createQuery("from User where name = :paramName");
            query.setParameter("paramName", filter.getFilterName());
            userList = query.list();
        }

        return userList;
    }

}
