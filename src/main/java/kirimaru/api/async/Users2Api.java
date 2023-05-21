package kirimaru.api.async;

//import java.util.List;
//import java.util.stream.Collectors;
//import kirimaru.api.dto.UserDto;
//import kirimaru.api.dto.ResponseUser;
//import kirimaru.biz.domain.User;
//import kirimaru.biz.service.UsersService;
//import kirimaru.biz.service.date.DateTimeResolver;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.RequiredArgsConstructor;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//import reactor.core.publisher.Mono;

//@RestController
//@Validated
//@RequestMapping("/users2")
//@RequiredArgsConstructor
//public class Users2Api {
//
//  private final UsersService usersService;
//  private final DateTimeResolver dateTimeResolver;
//
//  @RequestMapping(value = "", method = RequestMethod.GET)
//  public Mono<ResponseUser> get(@Validated GetParam param) {
//    List<User> users = usersService.execute();
//    List<UserDto> userDtoList = users.stream().map(e -> UserDto.builder().build())
//        .collect(Collectors.toList());
//
//    System.out.println(dateTimeResolver.now());
//
//    return Mono.just(new ResponseUser(userDtoList));
//  }
//
//  @Data
//  @AllArgsConstructor
//  private static class GetParam {
//
//  }
//
//}
