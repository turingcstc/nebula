package nebula.application.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import nebula.application.config.Constants;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
    name = "nebula_user",
    uniqueConstraints = {
      @UniqueConstraint(columnNames = {"login_id"}),
      @UniqueConstraint(columnNames = {"email"})
    })
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class User extends AbstractAuditingEntity implements UserDetails, Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @NotNull
  @Pattern(regexp = Constants.LOGIN_REGEX)
  @Size(min = 1, max = 50)
  @Column(name = "login_id", length = 50, unique = true, nullable = false)
  private String loginID;

  @JsonIgnore
  @NotNull
  @Size(min = 60, max = 60)
  @Column(name = "password", length = 60, nullable = false)
  private String password;

  @NotNull
  @Column(name = "user_name", nullable = false)
  private String username;

  @Email
  @Size(min = 5, max = 50)
  @Column(length = 50, unique = true)
  private String email;

  @NotNull
  @Column(name = "account_expired", nullable = false)
  private boolean accountExpired = false;

  @NotNull
  @Column(name = "account_locked", nullable = false)
  private boolean accountLocked = false;

  @NotNull
  @Column(name = "credentials_expired", nullable = false)
  private boolean credentialsExpired = false;

  @NotNull
  @Column(nullable = false)
  private boolean enabled = false;

  @JsonIgnore
  @ManyToMany
  @JoinTable(
      name = "nebula_user_authority",
      joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
      inverseJoinColumns = {@JoinColumn(name = "authority_id", referencedColumnName = "id")})
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
  @BatchSize(size = 20)
  private Set<Authority> authorities = new HashSet<>();

  //  @ManyToMany(fetch = FetchType.LAZY)
  //  @JoinTable(name = "nebula_user_authority", joinColumns = @JoinColumn(name = "user_id",
  // referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "authority_id",
  // referencedColumnName = "id"))
  //  @OrderBy
  //  @JsonIgnore
  //  private Collection<Authority> authorities;

  //    @JsonIgnore
  //    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user")
  //    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
  //    private Set<PersistentToken> persistentTokens = new HashSet<>();

  //    public Set<PersistentToken> getPersistentTokens() {
  //        return persistentTokens;
  //    }
  //
  //    public void setPersistentTokens(Set<PersistentToken> persistentTokens) {
  //        this.persistentTokens = persistentTokens;
  //    }

  @Override
  public boolean isAccountNonExpired() {
    return !isAccountExpired();
  }

  @Override
  public boolean isAccountNonLocked() {
    return !isAccountLocked();
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return !isCredentialsExpired();
  }

  @Override
  public String toString() {
    return "User{"
        + "loginID='"
        + loginID
        + '\''
        + "userName='"
        + username
        + '\''
        + "email='"
        + email
        + '\''
        + ", AccountExpired='"
        + accountExpired
        + '\''
        + ", AccountLocked='"
        + accountLocked
        + '\''
        + ", CredentialsExpired='"
        + credentialsExpired
        + '\''
        + ", Enabled='"
        + enabled
        + '\''
        + "}";
  }
}
