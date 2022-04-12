package persi.sumu.departure.demo.dao;

import org.apache.ibatis.annotations.Mapper;
import persi.sumu.departure.demo.entity.User;

import java.util.List;

@Mapper
public interface UserDao {

    List<User> selectAll();

    int addUser(User user);

}
