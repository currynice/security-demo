package com.cxy.repository;

import com.cxy.entity.UserInfoPO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author chengxinyu
 * @version 1.1.0
 * @description UserInfoPO jpa接口
 * @date 2019年06月05日9:58
 **/
public interface UserInfoRepository extends JpaRepository<UserInfoPO,Long> {

     UserInfoPO findUserInfoPOByUsername(String username);
}
