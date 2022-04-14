package ro.sd.a2.mapper;

import ro.sd.a2.dto.MovieDto;
import ro.sd.a2.dto.SessionDto;
import ro.sd.a2.entity.Session;

import java.time.format.DateTimeFormatter;

public class SessionMapper {

    public SessionDto fromSessionToDto(Session session, MovieDto movieDto, Integer roomNumber){
        DateTimeFormatter dateFormatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter dateFormatterTime = DateTimeFormatter.ofPattern("hh:mm");

        return SessionDto.builder()
                .date(dateFormatterDate.format(session.getDateAndTime()))
                .time(dateFormatterTime.format(session.getDateAndTime()))
                .movieDto(movieDto)
                .roomNumber(roomNumber)
                .build();
    }

}
