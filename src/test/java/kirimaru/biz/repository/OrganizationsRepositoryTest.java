package kirimaru.biz.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import kirimaru.biz.domain.Organization;
import kirimaru.biz.domain.User;
import kirimaru.biz.repository.helper.RepositoryTestTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


class OrganizationsRepositoryTest extends RepositoryTestTemplate {

  @Autowired
  private OrganizationsRepository target;

  @Test
  public void testInsert() {
    Organization organization = Organization.builder()
        .organizationId("1")
        .name("123")
        .build();
    target.insert(organization);

    var actual = target.findAll();
    assertThat(actual).isEqualTo(List.of(organization));
//    assertThat(actual).isEqualTo(List.of(organization.toBuilder().organizationId("2").build()));
  }
}