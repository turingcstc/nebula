package nebula.application.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

  private List<String> servers = new ArrayList<String>();

  public List<String> getServers() {
    return this.servers;
  }
}
