package ro.sd.a2.mapper;

import lombok.NoArgsConstructor;
import ro.sd.a2.dto.UserDto;
import ro.sd.a2.entity.User;

@NoArgsConstructor
public class UserMapper {

    public UserDto fromUserToDto(User user){
        return UserDto.builder().email(user.getEmail()).firstName(user.getFirstName()).lastName(user.getLastName()).build();
    }

    public User fromUserDtoToUser(UserDto userDto, User user){
        return User.builder().email(userDto.getEmail())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .userId(user.getUserId())
                .password(user.getPassword())
                .build();

    }

}
