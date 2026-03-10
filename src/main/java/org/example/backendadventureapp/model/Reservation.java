package org.example.backendadventureapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    //Note to self: why is this not a OneToMany?
    @OneToOne(fetch = FetchType.EAGER)
    private Customer customer;
    private String bookingNumber;
    private LocalDateTime dateOfReservation;
    private double price;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "reservation")
    private List<Timeslot> timeslots;

}
