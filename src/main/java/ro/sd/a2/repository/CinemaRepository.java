package ro.sd.a2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.sd.a2.entity.Cinema;

@Repository
public interface CinemaRepository extends JpaRepository<Cinema, String> {

    Cinema findCinemaByName(String name);

}
