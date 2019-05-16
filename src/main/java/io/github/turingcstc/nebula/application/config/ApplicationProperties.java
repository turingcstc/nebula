package io.github.turingcstc.nebula.application.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

  //private List<String> servers = new ArrayList<String>();

//  public List<String> getServers() {
//    return this.servers;
//  }
}
