package ro.sd.a2.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieDto {
    private String name;
    private String releaseDate;
    private String description;
    private String director;
    private String rating;
    private String trailerLink;
}
