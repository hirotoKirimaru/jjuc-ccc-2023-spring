package kirimaru.biz.repository;

import java.util.List;
import kirimaru.biz.domain.Organization;
import kirimaru.biz.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface OrganizationsRepository {

  @Select("""
        SELECT *
         FROM app.organizations
      """)
  List<Organization> findAll();

  @Insert("""
      INSERT INTO app.organizations (organization_id, name) VALUES (#{organizationId}, #{name})
  """)
  void insert(Organization organization);
}
