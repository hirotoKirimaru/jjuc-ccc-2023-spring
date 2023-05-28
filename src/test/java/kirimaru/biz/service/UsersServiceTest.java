package kirimaru.biz.service;

import kirimaru.biz.repository.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UsersServiceTest {
  UsersService target;

  @Mock
  UsersRepository repository;

  @BeforeEach
  void setUp() {
    target = new UsersServiceImpl(repository);
  }

  @Test
  void test_01() {
    // 起動だけできればいい
    target.execute();
  }
}