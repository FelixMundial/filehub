package com.example.filehub.service.uaa;

import com.example.filehub.service.uaa.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.config.BeanIds;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService userService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Qualifier(value = BeanIds.USER_DETAILS_SERVICE)
    @Autowired
    private UserDetailsService userDetailsService;

    @Test
    void testLogin() {
        UserDetails userDetails = userDetailsService.loadUserByUsername("root");
        System.out.println(userDetails.getAuthorities());
    }

    @Test
    void testPasswordEncoder() {
        System.out.println(passwordEncoder.encode("root"));
        System.out.println(passwordEncoder.encode("sammy"));
        System.out.println(passwordEncoder.encode("guest"));
    }
}
