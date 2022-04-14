package ro.sd.a2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.sd.a2.entity.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, String> {

    Room findRoomByRoomId(String roomId);

}
