package ro.sd.a2.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CinemaDto {
    private String name;
    private String street;
    private int number;
}
