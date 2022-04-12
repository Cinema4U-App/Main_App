package ro.sd.a2.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Seat {

    @Id
    private String seatId;

    @Column(nullable = false)
    private int rowNb;

    @Column(nullable = false)
    private int seatNumber;

    @Column(nullable = false)
    private boolean availability;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

}
