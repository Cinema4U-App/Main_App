package ro.sd.a2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.sd.a2.constants.ExistingUserException;
import ro.sd.a2.constants.NonexistentUserException;
import ro.sd.a2.constants.NotMatchingPasswordsException;
import ro.sd.a2.dto.LoginDto;
import ro.sd.a2.dto.RegisterDto;
import ro.sd.a2.dto.UserDto;
import ro.sd.a2.entity.User;
import ro.sd.a2.mapper.RegistrationMapper;
import ro.sd.a2.mapper.UserMapper;
import ro.sd.a2.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    //CREATE OPERATION:
    public void addUser(RegisterDto userDto) throws NotMatchingPasswordsException, ExistingUserException{
        //Verify if password and confirm password are matching:
        if(!((userDto.getPassword()).equals(userDto.getConfirmPassword()))){
            throw new NotMatchingPasswordsException();
        }

        //Convert from dto to entity:
        RegistrationMapper registrationMapper = new RegistrationMapper();
        User user = registrationMapper.fromRegisterDtoToUser(userDto);
        System.out.println(user.getUserId());
        System.out.println(user.getEmail());
        System.out.println(user.getFirstName());
        System.out.println(user.getLastName());
        System.out.println(user.getPassword());

        //Verify if a user with this email exists already:
        if(userRepository.findUserByEmail(user.getEmail()) == null){
            //If not, add the user:
            userRepository.save(user);
        }
        else{
            throw new ExistingUserException();
        }
    }

    //SELECT OPERATION:
    public UserDto loginUser(LoginDto userDto) throws NonexistentUserException {
        User user = userRepository.findUserByEmailAndPassword(userDto.getEmail(), userDto.getPassword());
        if(user != null){
            //System.out.println(user.getUserId());
            //transform the user to userDto
            UserMapper userMapper = new UserMapper();
            return userMapper.fromUserToDto(user);
        }
        else{
            throw new NonexistentUserException();
        }
    }

    //UPDATE OPERATION:
    public UserDto editProfile(UserDto userDto){
        //Search for the user and convert the dto data to entity data with the mapper:
        UserMapper userMapper = new UserMapper();
        User user = userMapper.fromUserDtoToUser(userDto, userRepository.findUserByEmail(userDto.getEmail()));
        user = userRepository.save(user);

        //Convert from User to dto:
        return userMapper.fromUserToDto(user);
    }

    //DELETE OPERATION:
    public void deleteUser(UserDto userDto){
        //Search for the user and convert the dto data to entity data with the mapper:
        UserMapper userMapper = new UserMapper();
        User user = userMapper.fromUserDtoToUser(userDto, userRepository.findUserByEmail(userDto.getEmail()));
        //Delete the user:
        userRepository.delete(user);
    }

}
