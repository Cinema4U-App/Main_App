package ro.sd.a2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.sd.a2.dto.CinemaDto;
import ro.sd.a2.dto.SessionDto;
import ro.sd.a2.entity.Cinema;
import ro.sd.a2.entity.Session;
import ro.sd.a2.mapper.CinemaMapper;
import ro.sd.a2.mapper.MovieMapper;
import ro.sd.a2.mapper.SessionMapper;
import ro.sd.a2.repository.CinemaRepository;
import ro.sd.a2.repository.MovieRepository;
import ro.sd.a2.repository.RoomRepository;
import ro.sd.a2.repository.SessionRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CinemaService {

    @Autowired
    private CinemaRepository cinemaRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private RoomRepository roomRepository;

    //CREATE CINEMA OPERATION:
    public CinemaDto addCinema(CinemaDto cinemaDto){
        //convert from dto to entity:
        CinemaMapper cinemaMapper = new CinemaMapper();
        Cinema cinema = cinemaMapper.fromDtoToCinema(cinemaDto);
        //add the cinema into the database:
        cinema = cinemaRepository.save(cinema);
        //from entity to dto:
        CinemaDto returnedCinemaDto = cinemaMapper.fromCinemaToDto(cinema);
        return returnedCinemaDto;
    }

    //SELECT ALL CINEMAS OPERATION:
    public List<CinemaDto> getAllCinemas(){
        //get the entities form the db:
        List<Cinema> cinemas = cinemaRepository.findAll();
        //map the to dtos:
        CinemaMapper cinemaMapper = new CinemaMapper();
        return cinemas.stream().map(cinemaMapper::fromCinemaToDto).collect(Collectors.toList());
    }

    //FIND BY NAME OPERATION:
    public Cinema findCinemaByName(String cinemaName){
        //find the cinema by the given name:
        return cinemaRepository.findCinemaByName(cinemaName);
    }

    //SELECT ALL SESSIONS OPERATION:
    public List<Session> findAllSessionsByCinema(Cinema cinema){
        return sessionRepository.findAllByCinema(cinema);
    }

    //SELECT CINEMAS OPERATION WITH CONSTRAINTS:
    public List<SessionDto> getCinemaSessionsForThisDate(String cinemaName, String scheduleDate){
        Cinema cinema = this.findCinemaByName(cinemaName);
        List<Session> sessions = this.findAllSessionsByCinema(cinema);
        //sessions.stream().forEach(e -> System.err.println(e.getSessionId()));
        SessionMapper sessionMapper = new SessionMapper();
        List<SessionDto> sessionDtos = sessions.stream()
                                        .map(e -> sessionMapper.fromSessionToDto(e,
                                        new MovieMapper().fromMovieToDto(movieRepository.findMovieByMovieId(e.getMovie().getMovieId())),
                                        roomRepository.findRoomByRoomId(e.getRoom().getRoomId()).getRoomNumber())).collect(Collectors.toList());
        //sessionDtos.stream().forEach(e -> System.err.println(e.getDate()));
        return sessionDtos.stream().filter(e -> e.getDate().equals(scheduleDate)).collect(Collectors.toList());
    }

}
