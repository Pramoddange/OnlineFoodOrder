package com.zosh.service.serviceInterface;

import com.zosh.model.User;

public interface UserService {
    public User findUserByJwtToken(String jwt) throws Exception;

    public User findUserByEmail(String email) throws Exception;

}
