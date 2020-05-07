package com.example.filehub.commons.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * @author yinfelix
 * @date 2020/4/14
 */
@Entity
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserAccountInfo implements Serializable {
    public UserAccountInfo(String userLoginName, String userLoginPassword, LocalDateTime userCreationTime, LocalDateTime userLastUpdateTime) {
        this.userLoginName = userLoginName;
        this.userLoginPassword = userLoginPassword;
        this.userCreationTime = userCreationTime;
        this.userLastUpdateTime = userLastUpdateTime;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uid", nullable = false, unique = true)
    private Long userId;

    @NotNull(message = "登录账户名不可为空")
    @Column(name = "login_name")
    private String userLoginName;

    @Column(name = "nickname")
    private String userNickname;

    @NotNull(message = "登录密码不可为空")
    @Column(name = "login_password")
    private String userLoginPassword;

    @Column(name = "creation_time")
    private LocalDateTime userCreationTime;

    @Column(name = "last_update_time")
    private LocalDateTime userLastUpdateTime;

    @JsonIgnore
    @ManyToMany(targetEntity = Role.class,
            fetch = FetchType.EAGER,
            cascade = CascadeType.MERGE)
    @JoinTable(
            name = "user_role",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "uid")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private Set<Role> roles = new HashSet<>();
}
