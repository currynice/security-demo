package com.cxy.controller;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author chengxinyu
 * @version 1.1.0
 * @description TestController
 * @date 2019年06月04日17:17
 **/
@RestController
public class HelloController {


    @GetMapping("/hello")
    public String getWelcomeMsg() {
        return "Hello";
    }

    @GetMapping("/admin/hello")
    public String helloAdmin() {
        return "Hello,admin";
    }


    @GetMapping("/user/hello")
    public String helloUser() {
        return "Hello,user";
    }


    @GetMapping("/db/hello")
//    @PreAuthorize("hasRole('ADMIN') and hasRole('DBA')")
    public String helloDBA() {
        return "Hello,db";
    }

    @GetMapping("/special")
    @PreAuthorize("hasRole('SPECIAL')")
    // 或者 @Secured("ROLE_SPECIAL") 需要ROLE前缀
    public String helloSpecial() {
        return "这是必须有ROLE_SPECIAL角色";
    }

}
