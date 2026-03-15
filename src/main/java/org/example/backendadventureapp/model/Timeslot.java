package org.example.backendadventureapp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Timeslot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private Activity activity;
    //optional = true tilader, at en reservation godt må være NULL i databasen
    @ManyToOne(optional = true)
    @JsonIgnoreProperties("timeslots")
    private Reservation reservation;
    @ManyToOne
    private Employee employee;
    private LocalDate dayOfActivity;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer participants;

    @Override
    public String toString() {
        return "Timeslot{" +
                "id=" + id +
                ", day=" + dayOfActivity +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", participants=" + participants +
                ", reservation=" + (reservation != null ? reservation.getBookingNumber() : "none") +
                '}';
    }
}