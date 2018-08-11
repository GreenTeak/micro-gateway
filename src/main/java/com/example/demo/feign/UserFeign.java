package com.example.demo.feign;

import com.example.demo.model.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "user-service")
public interface UserFeign {
    @PostMapping("/verifications")
    User verifyToken(String token);
}
