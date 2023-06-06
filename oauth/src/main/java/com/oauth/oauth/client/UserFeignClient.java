package com.oauth.oauth.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.user.commons.usercommons.entity.User;

@FeignClient(name = "service-users")
public interface UserFeignClient {
    
    @GetMapping("/users/search/username")
    public User findByUsername(@RequestParam String username);

    @PutMapping("/users/{id}")
    public User update(@RequestBody User user, @PathVariable Long id);
}
