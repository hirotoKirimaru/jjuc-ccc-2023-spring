package kirimaru.api;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/users")
public class UsersApi {

  public record ResponseUser(List<UserDto> users) {
  }

  public class UserDto {

  }

  //  @GetMapping("/users")
  @RequestMapping(value = "", method = RequestMethod.GET)
  public ResponseEntity<ResponseUser> get() {
    return ResponseEntity.ok(new ResponseUser(List.of()));
  }
}
