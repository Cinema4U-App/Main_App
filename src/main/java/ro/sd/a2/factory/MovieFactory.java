package ro.sd.a2.factory;

import ro.sd.a2.entity.Movie;

import java.time.LocalDateTime;
import java.util.UUID;

public class MovieFactory {

    public Movie createEmptyMovie(){
        Movie movie = new Movie();
        movie.setMovieId(UUID.randomUUID().toString());
        movie.setUpdatedAt(LocalDateTime.now());
        return movie;
    }

}
