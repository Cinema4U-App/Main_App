package ro.sd.a2.mapper;

import ro.sd.a2.constants.RatingEnum;
import ro.sd.a2.dto.MovieDto;
import ro.sd.a2.entity.Movie;
import ro.sd.a2.factory.MovieFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MovieMapper {

    public MovieDto fromMovieToDto(Movie movie){

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return MovieDto.builder()
                .name(movie.getName())
                .releaseDate(dateFormatter.format(movie.getReleaseDate()))
                .description(movie.getDescription())
                .director(movie.getDirector())
                .rating(movie.getRating().toString())
                .trailerLink(movie.getTrailerLink())
                .build();

    }

    public Movie fromDtoToMovie(MovieDto movieDto){
        MovieFactory movieFactory = new MovieFactory();
        Movie movie = movieFactory.createEmptyMovie();
        int year = 1, month = 1, day = 1;
        LocalDate releaseDate = null;
        RatingEnum rating = null;
        try{
            String[] date = movieDto.getReleaseDate().split("[.]");
            year = Integer.parseInt(date[2]);
            month = Integer.parseInt(date[1]);
            day = Integer.parseInt(date[0]);
            releaseDate = LocalDate.of(year, month, day);
        }
        catch (NumberFormatException | IndexOutOfBoundsException | NullPointerException ex){}

        try{
            rating = RatingEnum.valueOf(movieDto.getRating());
        }
        catch (IllegalArgumentException | NullPointerException ex){}

        return Movie.builder()
                .movieId(movie.getMovieId())
                .updatedAt(movie.getUpdatedAt())
                .name(movieDto.getName())
                .releaseDate(releaseDate)
                .description(movieDto.getDescription())
                .director(movieDto.getDirector())
                .rating(rating)
                .trailerLink(movieDto.getTrailerLink())
                .build();
    }

}
