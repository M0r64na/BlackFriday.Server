package data.domain;

import data.domain.base.EntityBase;
import data.domain.enums.RoleName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role extends EntityBase {
    @Column(unique = true, nullable = false)
    @Enumerated(value = EnumType.STRING)
    private RoleName roleName;
}