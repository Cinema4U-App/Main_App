package ro.sd.a2.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Cinema {

    @Id
    private String cinemaId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private int number;

    @OneToMany(mappedBy = "cinema")
    private List<Room> roomList;

    @OneToMany(mappedBy = "cinema")
    private List<Session> sessionList;

}
