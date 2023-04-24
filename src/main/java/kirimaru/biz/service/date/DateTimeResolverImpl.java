package kirimaru.biz.service.date;

import java.time.LocalDateTime;
import java.time.ZoneId;
import org.springframework.stereotype.Component;

@Component
public class DateTimeResolverImpl implements DateTimeResolver {

  @Override
  public LocalDateTime now() {
    return LocalDateTime.now(ZoneId.of("Asia/Tokyo"));
  }
}
