package com.cxy.userDetailService;

import com.cxy.repository.UserInfoRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class InitUserTest {
    @Autowired
    private UserInfoRepository repository;
    @Test
    public void run() throws Exception {

    }

}