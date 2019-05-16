package io.github.turingcstc.nebula.application.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(
    name = "nebula_authority",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Authority implements GrantedAuthority, Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @Size(max = 50)
  @Column(length = 50)
  private String name;

  @Override
  public String getAuthority() {
    return getName();
  }

  @Override
  public String toString() {
    return "Authority{" + "name='" + name + '\'' + "}";
  }
}
