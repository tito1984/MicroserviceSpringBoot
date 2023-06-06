package com.oauth.oauth.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.oauth.oauth.client.UserFeignClient;
import com.user.commons.usercommons.entity.User;

import brave.Tracer;
import feign.FeignException;

@Service
public class UserService implements IUserService, UserDetailsService {

    @Autowired
    private UserFeignClient feignClient;

    @Autowired
    private Tracer tracer;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = feignClient.findByUsername(username);

            List<GrantedAuthority> authorities = user.getRoles()
                    .stream()
                    .map(role -> new SimpleGrantedAuthority(role.getName()))
                    .collect(Collectors.toList());

            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                    user.getEnabled(), true, true, true, authorities);
        } catch (FeignException e) {
            String error = "Error in login, username: '" + username + "' is not in system";
            tracer.currentSpan().tag("error.message", error + e.getMessage());
            throw new UsernameNotFoundException(error);
        }

    }

    @Override
    public User findByUsername(String username) {
        return feignClient.findByUsername(username);
    }

    @Override
    public User update(User user, Long id) {
        return feignClient.update(user, id);
    }

}
