package ro.sd.a2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDto {
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String confirmPassword;
}
