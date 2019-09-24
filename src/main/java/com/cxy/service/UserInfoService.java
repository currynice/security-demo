package com.cxy.service;


import com.cxy.entity.UserInfo;
import com.cxy.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoService {

    @Autowired
    private UserInfoRepository userInfoRepository;


    public UserInfo findByUsername(String username) {
        return userInfoRepository.findByUserName(username);
    }

}
