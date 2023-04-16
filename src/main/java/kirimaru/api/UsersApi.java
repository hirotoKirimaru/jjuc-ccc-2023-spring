package kirimaru.api;

import java.util.List;
import java.util.stream.Collectors;
import kirimaru.api.dto.ResponseUser;
import kirimaru.api.dto.UserDto;
import kirimaru.biz.domain.User;
import kirimaru.biz.service.UsersService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersApi {

  private final UsersService usersService;

  @RequestMapping(value = "", method = RequestMethod.GET)
  public ResponseEntity<ResponseUser> get(@Validated GetParam param) {
    List<User> users = usersService.execute();
    List<UserDto> userDtoList = users.stream().map(e -> UserDto.builder().build())
        .collect(Collectors.toList());
    return ResponseEntity.ok(new ResponseUser(userDtoList));
  }

  @Data
  @AllArgsConstructor
  private static class GetParam {

  }

}
