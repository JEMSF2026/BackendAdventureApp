package org.example.backendadventureapp.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @OneToOne
    private Customer customer;
    private String bookingNumber;
    private LocalDateTime dateOfReservation;
    private double price;
    @OneToMany(mappedBy = "reservation")
    @JsonManagedReference
    private List<Timeslot> timeslots;

}
