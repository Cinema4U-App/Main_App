package ro.sd.a2.dto;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SessionDto {
    private String date;
    private String time;
    private MovieDto movieDto;
    private Integer roomNumber;
}
