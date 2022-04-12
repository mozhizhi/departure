package persi.sumu.departure.demo.service;

import persi.sumu.departure.demo.entity.User;

import java.util.List;

public interface UserService {

    List<User> selectAll();

    int addUser(User user);

}
