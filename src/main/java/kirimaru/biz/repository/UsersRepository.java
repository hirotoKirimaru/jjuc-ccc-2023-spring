package kirimaru.biz.repository;

import java.util.List;
import kirimaru.api.dto.UserDto;
import kirimaru.biz.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UsersRepository {

  @Select("""
    SELECT *
     FROM app.users
  """)
  List<User> findAll();

  @Insert("""
      INSERT INTO app.users (user_id, name) VALUES (#{userId}, #{name})
      """)
  void insert(User user);


}
