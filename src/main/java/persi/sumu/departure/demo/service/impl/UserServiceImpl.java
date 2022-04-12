package persi.sumu.departure.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import persi.sumu.departure.demo.dao.UserDao;
import persi.sumu.departure.demo.entity.User;
import persi.sumu.departure.demo.service.UserService;

import java.util.List;

/**
 * @author mobai
 * @version 1.0
 * @Description
 * @date 2022/4/9 16:36
 */
@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public List<User> selectAll() {
        return userDao.selectAll();
    }

    @Override
    public int addUser(User user) {
        return userDao.addUser(user);
    }

}
