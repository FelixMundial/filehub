package com.example.filehub.service.uaa;

import com.example.filehub.commons.service.entity.UserAccountInfo;
import com.example.filehub.service.uaa.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class UaaServiceApplicationTests {
    @Autowired
    UserService userService;

    @Test
    void testLogin() {
        final UserAccountInfo user = userService.findUserByLoginNameAndLoginPassword("admin001", "root");
        System.out.println(user);
    }

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Test
    void testPasswordEncoder() {
        System.out.println(passwordEncoder.encode("root"));
        System.out.println(passwordEncoder.encode("sammy"));
        System.out.println(passwordEncoder.encode("guest"));
    }

}
