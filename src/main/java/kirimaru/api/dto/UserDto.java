package kirimaru.api.dto;

import kirimaru.biz.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

  private String userId;
  private String email;
  private String name;

  public static UserDto of(User domain) {
    return UserDto.builder()
        .userId(domain.getUserId())
        .email(domain.getEmail())
        .name(domain.getName())
        .build();
  }
}
