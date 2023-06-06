package com.oauth.oauth.security.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import com.oauth.oauth.service.IUserService;
import com.user.commons.usercommons.entity.User;

import brave.Tracer;

@Component
public class AuthenticationSuccessErrorHandler implements AuthenticationEventPublisher {

    private Logger log = LoggerFactory.getLogger(AuthenticationSuccessErrorHandler.class);

    @Autowired
    private Tracer tracer;

    @Autowired
    private IUserService userService;

    @Override
    public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {
        String messaje = "Error Login: " + exception.getMessage();
        System.out.println(messaje);
        log.info(messaje);

        try {
            StringBuilder errors = new StringBuilder();
            errors.append(messaje);
            User user = userService.findByUsername(authentication.getName());
            if (user.getTries() == null) {
                user.setTries(0);
            }
            user.setTries(user.getTries()+1);
            if (user.getTries()>=3) {
                String errorMaxTries = String.format("The user %s is disable by max tries", user.getUsername());
                log.error(errorMaxTries);
                errors.append(errorMaxTries);
                user.setEnabled(false);
            }
            userService.update(user, user.getId());

            tracer.currentSpan().tag("error.message", errors.toString());
        } catch (Exception e) {
            log.error(String.format("The user %s is not in system", authentication.getName()));
        }
    }

    @Override
    public void publishAuthenticationSuccess(Authentication authentication) {
        if (authentication.getDetails() instanceof WebAuthenticationDetails) {
            return;
        }
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        String messaje = "Success Login: " + userDetails.getUsername();
        System.out.println(messaje);
        log.info(messaje);

        User user = userService.findByUsername(authentication.getName());
        if (user.getTries() != null && user.getTries() > 0) {
            user.setTries(0);
            userService.update(user, user.getId());
        }
        

    }
    
}
