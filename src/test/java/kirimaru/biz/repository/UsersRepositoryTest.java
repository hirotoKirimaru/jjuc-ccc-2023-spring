package kirimaru.biz.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import kirimaru.biz.domain.User;
import kirimaru.biz.repository.helper.RepositoryTestTemplate;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;


// DI対象を絞りたい時に
@MapperScan("kirimaru.biz.repository")
//@ComponentScan("kirimaru.biz.repository.aaa") // これは意味がない
class UsersRepositoryTest extends RepositoryTestTemplate {

  @Autowired
  private UsersRepository target;

  @Test
  public void testInsert() {
    User user = User.builder()
        .userId("1")
        .name("123")
        .build();
    target.insert(user);

    var actual = target.findAll();
    assertThat(actual).isEqualTo(List.of(user));
  }
}