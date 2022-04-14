package ro.sd.a2.mapper;

import ro.sd.a2.dto.CinemaDto;
import ro.sd.a2.entity.Cinema;
import ro.sd.a2.factory.CinemaFactory;

public class CinemaMapper {

    public Cinema fromDtoToCinema(CinemaDto cinemaDto){
        CinemaFactory cinemaFactory = new CinemaFactory();
        Cinema cinema = cinemaFactory.createEmptyCinema();
        return Cinema.builder()
                .cinemaId(cinema.getCinemaId())
                .name(cinemaDto.getName())
                .street(cinemaDto.getStreet())
                .number(cinemaDto.getNumber())
                .build();
    }

    public CinemaDto fromCinemaToDto(Cinema cinema){
        return CinemaDto.builder()
                .name(cinema.getName())
                .street(cinema.getStreet())
                .number(cinema.getNumber())
                .build();
    }

}
