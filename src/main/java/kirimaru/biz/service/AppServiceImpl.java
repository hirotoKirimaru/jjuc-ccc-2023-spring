package kirimaru.biz.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class AppServiceImpl implements AppService {

  @Value("${app.config.appName}")
  private String appName;

  @Override
  public String getAppName() {
    return appName;
//    return null;
  }
}
