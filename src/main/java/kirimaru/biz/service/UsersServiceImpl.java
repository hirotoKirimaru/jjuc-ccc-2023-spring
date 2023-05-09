package kirimaru.biz.service;

import java.util.List;
import kirimaru.biz.domain.User;
import kirimaru.biz.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {
  private final UsersRepository usersRepository;

  @Override
  public List<User> execute() {
    return usersRepository.findAll();
  }
}
