package com.cxy.repository;



import com.cxy.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;



public interface PermissionRepository extends JpaRepository<Permission,Long> {

}
