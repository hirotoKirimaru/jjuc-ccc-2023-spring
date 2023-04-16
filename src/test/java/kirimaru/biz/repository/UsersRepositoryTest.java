package kirimaru.biz.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import kirimaru.biz.domain.User;
import kirimaru.biz.repository.helper.RepositoryTestTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


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