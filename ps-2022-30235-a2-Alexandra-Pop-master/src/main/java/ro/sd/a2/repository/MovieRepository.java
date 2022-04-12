package ro.sd.a2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ro.sd.a2.entity.Movie;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, String> {

    @Query("SELECT u FROM Movie u WHERE (u.name like %?1%) and (u.director like %?2%)")
    List<Movie> findAllByNameLikeAndDirectorLike(String name, String director);

}
