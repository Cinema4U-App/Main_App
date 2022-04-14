package ro.sd.a2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ro.sd.a2.dto.MovieDto;
import ro.sd.a2.entity.Movie;
import ro.sd.a2.mapper.MovieMapper;
import ro.sd.a2.repository.MovieRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    //SELECT ALL OPERATION:
    public List<MovieDto> getAllMovies(){
        List<Movie> movies =  movieRepository.findAll();
        MovieMapper movieMapper = new MovieMapper();
        return movies.stream().map(movieMapper::fromMovieToDto).collect(Collectors.toList());
    }

    //FILTER OPERATION:
    public List<MovieDto> filterMovies(MovieDto movieDto){
        MovieMapper movieMapper = new MovieMapper();
        Movie movie = movieMapper.fromDtoToMovie(movieDto);
        //System.out.println("Name = |"+movie.getName()+"|, director = |"+movie.getDirector()+"|");
        List<Movie> movies = movieRepository.findAllByNameLikeAndDirectorLike(movie.getName(), movie.getDirector());
        return movies.stream().map(movieMapper::fromMovieToDto).collect(Collectors.toList());
    }

    //CREATE OPERATION:
    public void addMovie(MovieDto movieDto){

        MovieMapper movieMapper = new MovieMapper();
        Movie movie = movieMapper.fromDtoToMovie(movieDto);
        movieRepository.save(movie);

    }

}
