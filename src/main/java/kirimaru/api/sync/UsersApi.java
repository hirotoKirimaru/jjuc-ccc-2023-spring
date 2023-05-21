package kirimaru.api.sync;

import java.util.List;
import java.util.stream.Collectors;
import kirimaru.api.dto.UserDto;
import kirimaru.api.dto.ResponseUserDto;
import kirimaru.biz.domain.User;
import kirimaru.biz.service.date.DateTimeResolver;
import kirimaru.biz.service.UsersService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
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

//  @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  @RequestMapping(value = "", method = RequestMethod.GET)
  public ResponseEntity<ResponseUserDto> get(@Validated GetParam param) {
    List<User> users = usersService.execute();
    List<UserDto> userDtoList = users.stream().map(UserDto::of)
        .collect(Collectors.toList());

    return ResponseEntity.ok(new ResponseUserDto(userDtoList));
  }

  @Data
  @AllArgsConstructor
  private static class GetParam {

  }

}
