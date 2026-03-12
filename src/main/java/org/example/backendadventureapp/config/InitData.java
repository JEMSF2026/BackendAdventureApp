package org.example.backendadventureapp.config;

import org.example.backendadventureapp.model.*;
import org.example.backendadventureapp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class InitData implements CommandLineRunner {

    @Autowired
    private TimeslotRepository timeslotRepository;
    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private EquipmentRepository equipmentRepository;
    @Autowired
    private EquipmentStateRepository equipmentStateRepository;
    @Autowired
    private CustomerTypeRepository customerTypeRepository;

    @Override
    public void run(String... args) throws Exception {

        Activity a1 = new Activity();
        a1.setName("Go-Kart");
        a1.setMinimumAge(16);
        a1.setMaxParticipants(4);
        a1.setDescription("Kom og udlev dine drømme som en ægte formel-1 kører. Vi har danmarks bedste track.");
        a1.setPrice(200.0);
        activityRepository.save(a1);

        Activity a2 = new Activity();
        a2.setName("Paintball");
        a2.setMinimumAge(14);
        a2.setMaxParticipants(10);
        a2.setDescription("Kom og skyd på dine venner på vores Counter-strike inspireret bane.");
        a2.setPrice(100.0);
        activityRepository.save(a2);

        // --- Rød dag: fuldt reserveret ---
        Employee e1 = new Employee();
        e1.setFirstName("Bo");
        employeeRepository.save(e1);

        Reservation r1 = new Reservation();
        r1.setBookingNumber("1234");
        reservationRepository.save(r1);

        Timeslot ts1 = new Timeslot();
        ts1.setActivity(a1);
        ts1.setEmployee(e1);
        ts1.setReservation(r1);
        ts1.setDayOfActivity(LocalDate.of(2026, 3, 7));
        ts1.setStartTime(LocalDateTime.of(2026, 3, 7, 14, 30));
        ts1.setEndTime(LocalDateTime.of(2026, 3, 7, 16, 30));
        ts1.setParticipants(10);
        timeslotRepository.save(ts1);
        System.out.println(ts1);

        // --- Grøn dag: ingen reserverede timeslots ---
        Employee e2 = new Employee();
        e2.setFirstName("Thor");
        employeeRepository.save(e2);

        Timeslot ts2 = new Timeslot();
        ts2.setActivity(a1);
        ts2.setEmployee(e2);
        ts2.setReservation(null); // ledig
        ts2.setDayOfActivity(LocalDate.of(2026, 3, 8));
        ts2.setStartTime(LocalDateTime.of(2026, 3, 8, 12, 0));
        ts2.setEndTime(LocalDateTime.of(2026, 3, 8, 14, 0));
        ts2.setParticipants(10);
        timeslotRepository.save(ts2);
        System.out.println(ts2);

        // --- Gul dag: delvist reserveret ---
        // Første timeslot ledig
        Employee e3 = new Employee();
        e3.setFirstName("Lars");
        employeeRepository.save(e3);

        Timeslot ts3 = new Timeslot();
        ts3.setActivity(a1);
        ts3.setEmployee(e3);
        ts3.setReservation(null); // ledig
        ts3.setDayOfActivity(LocalDate.of(2026, 3, 9));
        ts3.setStartTime(LocalDateTime.of(2026, 3, 9, 9, 0));
        ts3.setEndTime(LocalDateTime.of(2026, 3, 9, 10, 0));
        ts3.setParticipants(5);
        timeslotRepository.save(ts3);

        // Andet timeslot reserveret
        Employee e4 = new Employee();
        e4.setFirstName("Mette");
        employeeRepository.save(e4);

        Reservation r2 = new Reservation();
        r2.setBookingNumber("5678");
        reservationRepository.save(r2);

        Timeslot ts4 = new Timeslot();
        ts4.setActivity(a1);
        ts4.setEmployee(e4);
        ts4.setReservation(r2); // reserveret
        ts4.setDayOfActivity(LocalDate.of(2026, 3, 9));
        ts4.setStartTime(LocalDateTime.of(2026, 3, 9, 10, 0));
        ts4.setEndTime(LocalDateTime.of(2026, 3, 9, 11, 0));
        ts4.setParticipants(5);
        timeslotRepository.save(ts4);

        EquipmentState eState1 = new EquipmentState();
        eState1.setName("Active");
        equipmentStateRepository.save(eState1);
        EquipmentState eState2 = new EquipmentState();
        eState2.setName("Out of Order");
        equipmentStateRepository.save(eState2);
        EquipmentState eState3 = new EquipmentState();
        eState3.setName("Reparation");
        equipmentStateRepository.save(eState3);


        Equipment eq1 = new Equipment();
        eq1.setName("Go-Kart hjelm #1");
        eq1.setActivity(a1);
        eq1.setEquipmentState(eState1);
        equipmentRepository.save(eq1);

        Equipment eq2 = new Equipment();
        eq2.setName("Go-Kart #1");
        eq2.setActivity(a1);
        eq2.setEquipmentState(eState2);
        eq2.setDescription("venstre hjul er punkteret");
        equipmentRepository.save(eq2);

        Equipment eq3 = new Equipment();
        eq3.setName("Paintball hjelm #1");
        eq3.setActivity(a2);
        eq3.setEquipmentState(eState1);
        equipmentRepository.save(eq3);

        Equipment eq4 = new Equipment();
        eq4.setName("Paintball gun #1");
        eq4.setActivity(a2);
        eq4.setEquipmentState(eState3);
        eq4.setDescription("Klar den 2. april");
        equipmentRepository.save(eq4);

        CustomerType privateType = new CustomerType();
        privateType.setName("Private");
        customerTypeRepository.save(privateType);

        CustomerType companyType = new CustomerType();
        companyType.setName("Company");
        customerTypeRepository.save(companyType);
    }
}