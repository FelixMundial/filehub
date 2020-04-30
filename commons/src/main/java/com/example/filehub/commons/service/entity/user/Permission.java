package com.example.filehub.commons.service.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * @author yinfelix
 * @date 2020/4/29
 */
@Entity
@Table(name = "permission")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Permission {
    public Permission(String code, String resourceUrl, LocalDateTime roleCreationTime) {
        this.code = code;
        if (!StringUtils.isEmpty(name)) {
            this.name = code;
        } else {
            this.name = StringUtils.capitalize(code);
        }
        this.resourceUrl = resourceUrl;
        this.creationTime = roleCreationTime;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "name")
    private String name;

    @NotNull(message = "权限编码不可为空")
    @Column(name = "code")
    private String code;

    @NotNull(message = "资源路径不可为空")
    @Column(name = "resource_url")
    private String resourceUrl;

    @Column(name = "resource_name")
    private String resourceName;

    @Column(name = "notes")
    private String notes;

    @Column(name = "creation_time")
    private LocalDateTime creationTime;

    @Column(name = "last_update_time")
    private LocalDateTime lastupdateTime;

    private static final long serialVersionUID = 1L;

    @JsonIgnore
    @ManyToMany(mappedBy = "permissions", fetch = FetchType.LAZY)
    private Set<Role> roles = new HashSet<>();
}
