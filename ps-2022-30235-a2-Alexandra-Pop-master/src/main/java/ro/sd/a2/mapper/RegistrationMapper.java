package ro.sd.a2.mapper;

import ro.sd.a2.dto.RegisterDto;
import ro.sd.a2.dto.UserDto;
import ro.sd.a2.entity.User;
import ro.sd.a2.factory.UserFactory;

public class RegistrationMapper {

    public RegisterDto fromUserToRegisterDto(User user){
        return null;
    }

    public User fromRegisterDtoToUser(RegisterDto userDto){
        UserFactory userFactory = new UserFactory();
        User user = userFactory.createEmptyUser();
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPassword(userDto.getPassword());
        return user;
    }

}
