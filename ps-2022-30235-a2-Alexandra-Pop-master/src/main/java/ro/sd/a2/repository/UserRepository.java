package ro.sd.a2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ro.sd.a2.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    User findUserByEmail(String email);
    User findUserByEmailAndPassword(String email, String password);

}
