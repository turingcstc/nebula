package nebula.application.service.dto;

import lombok.Getter;
import lombok.Setter;
import nebula.application.config.Constants;
import nebula.application.domain.Authority;
import nebula.application.domain.User;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class UserDTO {

  private Long id;

  @NotBlank
  @Pattern(regexp = Constants.LOGIN_REGEX)
  @Size(min = 1, max = 50)
  private String login;

  @Size(max = 50)
  private String userName;

  @Email
  @Size(min = 5, max = 50)
  private String email;

  //
  //    @Size(max = 256)
  //    private String imageUrl;
  //
  //    private boolean activated = false;

  //    @Size(min = 2, max = 6)
  //    private String langKey;

  private String createdBy;

  private Instant createdDate;

  private String lastModifiedBy;

  private Instant lastModifiedDate;

  private Set<String> authorities;

  private boolean activated = false;

  public UserDTO() {
    // Empty constructor needed for Jackson.
  }

  public UserDTO(User user) {
    this.id = user.getId();
    this.login = user.getLogin();
    this.userName = user.getUsername();
    this.email = user.getEmail();

    //        this.lastName = user.getLastName();
    //        this.email = user.getEmail();
    //        this.activated = user.getActivated();
    //        this.imageUrl = user.getImageUrl();
    //        this.langKey = user.getLangKey();
    this.createdBy = user.getCreatedBy();
    this.createdDate = user.getCreatedDate();
    this.lastModifiedBy = user.getLastModifiedBy();
    this.lastModifiedDate = user.getLastModifiedDate();
    this.authorities =
        user.getAuthorities().stream().map(Authority::getName).collect(Collectors.toSet());
  }

  @Override
  public String toString() {
    return "UserDTO{"
        + "login='"
        + login
        + '\''
        + ", userName='"
        + userName
        + '\''
        +
        //                ", lastName='" + lastName + '\'' +
        ", email='"
        + email
        + '\''
        +
        //                ", imageUrl='" + imageUrl + '\'' +
        //                ", activated=" + activated +
        //                ", langKey='" + langKey + '\'' +
        ", createdBy="
        + createdBy
        + ", createdDate="
        + createdDate
        + ", lastModifiedBy='"
        + lastModifiedBy
        + '\''
        + ", lastModifiedDate="
        + lastModifiedDate
        + ", authorities="
        + authorities
        + "}";
  }
}
