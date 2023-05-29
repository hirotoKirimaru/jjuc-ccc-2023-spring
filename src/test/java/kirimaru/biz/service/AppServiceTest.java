package kirimaru.biz.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(AppServiceTest.Config.class)
@TestPropertySource(properties = {"app.config.appName=kirimaru"})
class AppServiceTest {

  @Autowired
  AppService target;

  @ComponentScan(
      value = {"kirimaru.biz.service"},
      includeFilters = @ComponentScan.Filter(value = AppService.class, type = FilterType.ASSIGNABLE_TYPE),
      excludeFilters = @ComponentScan.Filter(
          value = {UsersService.class, OrganizationsService.class}, // 全てのファイルを除外する必要があるので、非効率
          type = FilterType.ASSIGNABLE_TYPE
      )
  )
  public static class Config {

  }

  @Test
  void test() {
    assertThat(target.getAppName()).isEqualTo("kirimaru");
  }
}