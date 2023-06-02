package kirimaru.biz.service.date;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;

@Component
public class DateTimeResolverImpl implements DateTimeResolver {

  public LocalDateTime currentLocalDateTime() {
    return now().toLocalDateTime();
  }

  public String formatCurrentToyyyyMMddHHmmss() {
    return now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
  }
}
