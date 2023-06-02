package kirimaru.biz.service.date;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;

public interface DateTimeResolver {
  default OffsetDateTime now() {
    return OffsetDateTime.now(ZoneId.of("Asia/Tokyo"));
  };

}
