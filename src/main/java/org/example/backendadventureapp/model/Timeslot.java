package org.example.backendadventureapp.model;

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
    private int id;
    @ManyToOne
    private Activity activity;
    @ManyToOne
    private Reservation reservation;
    @ManyToOne
    private Employee employee;
    private LocalDate dayOfActivity;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int participants;
}
