package com.example.filehub.commons.service.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * @author yinfelix
 * @date 2020/4/29
 */
@Entity
@Table(name = "role")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Role implements Serializable {
    public Role(String code, String name, LocalDateTime roleCreationTime) {
        this.code = code;
        if (!StringUtils.isEmpty(name)) {
            this.name = code;
        } else {
            this.name = StringUtils.capitalize(code);
        }
        this.creationTime = roleCreationTime;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "name")
    private String name;

    @NotNull(message = "角色编码不可为空")
    @Column(name = "code")
    private String code;

    @Column(name = "notes")
    private String notes;

    @Column(name = "creation_time")
    private LocalDateTime creationTime;

    @Column(name = "last_update_time")
    private LocalDateTime lastupdateTime;

    private static final long serialVersionUID = 1L;

    /*
    角色为被动方，应放弃外键维护权
     */
    @JsonIgnore
    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private Set<UserAccountInfo> users = new HashSet<>();

    @JsonIgnore
    @ManyToMany(targetEntity = Permission.class,
            fetch = FetchType.EAGER,
            cascade = CascadeType.MERGE)
    @JoinTable(
            name = "role_permission",
            joinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "permission_id", referencedColumnName = "id")})
    private Set<Permission> permissions = new HashSet<>();
}
