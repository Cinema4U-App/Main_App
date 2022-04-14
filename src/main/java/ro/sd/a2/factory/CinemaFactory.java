package ro.sd.a2.factory;

import ro.sd.a2.entity.Cinema;

import java.util.UUID;

public class CinemaFactory {

    public Cinema createEmptyCinema(){
        Cinema cinema = new Cinema();
        cinema.setCinemaId(UUID.randomUUID().toString());
        return cinema;
    }

}
