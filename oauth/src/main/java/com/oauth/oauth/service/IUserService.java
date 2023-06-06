package com.oauth.oauth.service;

import com.user.commons.usercommons.entity.User;

public interface IUserService {
    public User findByUsername(String username);
    public User update(User user, Long id);
}
