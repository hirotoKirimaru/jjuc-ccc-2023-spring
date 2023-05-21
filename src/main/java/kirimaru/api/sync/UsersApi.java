package kirimaru.api.sync;

import java.util.List;
import java.util.stream.Collectors;
import kirimaru.api.ControllerConstant;
import kirimaru.api.ControllerConstant.Uri;
import kirimaru.api.dto.UserDto;
import kirimaru.api.dto.ResponseUser;
import kirimaru.biz.domain.User;
import kirimaru.biz.service.date.DateTimeResolver;
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
  private final DateTimeResolver dateTimeResolver;

  @RequestMapping(value = "", method = RequestMethod.GET)
  public ResponseEntity<ResponseUser> get(@Validated GetParam param) {
    List<User> users = usersService.execute();
    List<UserDto> userDtoList = users.stream().map(e -> UserDto.builder().build())
        .collect(Collectors.toList());

    System.out.println(dateTimeResolver.now());

    return ResponseEntity.ok(new ResponseUser(userDtoList));
  }

  @Data
  @AllArgsConstructor
  private static class GetParam {

  }

}
