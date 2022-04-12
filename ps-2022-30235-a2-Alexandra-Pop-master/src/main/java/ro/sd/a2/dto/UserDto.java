package ro.sd.a2.dto;

import lombok.*;

@Getter
@Setter
@Builder
public class UserDto {

    private String email;
    private String firstName;
    private String lastName;

}
