package kirimaru.api.security;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import org.springframework.http.HttpHeaders;

class SecurityConstants {
  static final String HEADER = HttpHeaders.AUTHORIZATION;

  static final String TOKEN_PREFIX = "Bearer ";

  static final Key KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);
}
