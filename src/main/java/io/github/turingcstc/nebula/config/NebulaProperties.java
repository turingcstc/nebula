package io.github.turingcstc.nebula.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import javax.validation.constraints.NotNull;

@ConfigurationProperties(prefix = "nebula", ignoreUnknownFields = false)
public class NebulaProperties {

  private final Security security = new Security();

  public Security getSecurity() {
    return security;
  }

  public static class Security {

    private final Authentication authentication = new Authentication();

    private final RememberMe rememberMe = new RememberMe();

    public Authentication getAuthentication() {
      return authentication;
    }

    public RememberMe getRememberMe() {
      return rememberMe;
    }

    public static class Authentication {

      private final Jwt jwt = new Jwt();

      public Jwt getJwt() {
        return jwt;
      }

      public static class Jwt {

        private String secret = NebulaDefaults.Security.Authentication.Jwt.secret;

        private String base64Secret = NebulaDefaults.Security.Authentication.Jwt.base64Secret;

        private long tokenValidityInSeconds =
                NebulaDefaults.Security.Authentication.Jwt.tokenValidityInSeconds;

        private long tokenValidityInSecondsForRememberMe =
                NebulaDefaults.Security.Authentication.Jwt.tokenValidityInSecondsForRememberMe;

        public String getSecret() {
          return secret;
        }

        public void setSecret(String secret) {
          this.secret = secret;
        }

        public String getBase64Secret() {
          return base64Secret;
        }

        public void setBase64Secret(String base64Secret) {
          this.base64Secret = base64Secret;
        }

        public long getTokenValidityInSeconds() {
          return tokenValidityInSeconds;
        }

        public void setTokenValidityInSeconds(long tokenValidityInSeconds) {
          this.tokenValidityInSeconds = tokenValidityInSeconds;
        }

        public long getTokenValidityInSecondsForRememberMe() {
          return tokenValidityInSecondsForRememberMe;
        }

        public void setTokenValidityInSecondsForRememberMe(
            long tokenValidityInSecondsForRememberMe) {
          this.tokenValidityInSecondsForRememberMe = tokenValidityInSecondsForRememberMe;
        }
      }
    }

    public static class RememberMe {

      @NotNull private String key = NebulaDefaults.Security.RememberMe.key;

      public String getKey() {
        return key;
      }

      public void setKey(String key) {
        this.key = key;
      }
    }
  }
}
