package kirimaru.api.security;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.springframework.http.HttpStatus;

public interface AuthErrorHelper {

  default void createErrorResponse(HttpServletResponse res, HttpStatus httpStatus)
      throws IOException {
    res.setCharacterEncoding(StandardCharsets.UTF_8.name());
    res.setContentType("application/json");
    res.setStatus(httpStatus.value());

    ObjectMapper om = new ObjectMapper();
    res.getWriter().write(om.writeValueAsString(""));
  }

}
