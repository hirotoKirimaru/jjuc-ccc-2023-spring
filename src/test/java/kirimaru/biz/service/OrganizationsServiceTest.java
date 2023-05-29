package kirimaru.biz.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OrganizationsServiceTest {
  OrganizationsService target;

  @BeforeEach
  void setUp() {
    target = new OrganizationsServiceImpl();
  }

  @Test
  void test() {
    target.execute();
  }
}