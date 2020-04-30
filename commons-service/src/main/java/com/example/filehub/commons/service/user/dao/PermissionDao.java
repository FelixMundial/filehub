package com.example.filehub.commons.service.user.dao;

import com.example.filehub.commons.service.entity.user.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author yinfelix
 * @date 2020/4/30
 */
public interface PermissionDao extends JpaRepository<Permission, Long>, JpaSpecificationExecutor<Long> {
    List<Permission> findByResourceUrlStartingWith(String resourceUrl);

    List<Permission> findByResourceUrlContaining(String resourceUrl);

    List<Permission> findByResourceUrlEndingWith(String resourceUrl);

    Permission findByResourceUrl(String resourceUrl);
}
