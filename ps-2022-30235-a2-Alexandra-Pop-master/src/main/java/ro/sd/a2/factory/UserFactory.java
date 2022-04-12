package ro.sd.a2.factory;

import ro.sd.a2.entity.User;

import java.util.UUID;

public class UserFactory {

    public User createEmptyUser(){
        User user = new User();
        user.setUserId(UUID.randomUUID().toString());
        return user;
    }

    public User createUserWithRandomPassword(){
        User user = new User();
        user.setUserId(UUID.randomUUID().toString());
        user.setPassword(UUID.randomUUID().toString());
        return user;
    }

}
