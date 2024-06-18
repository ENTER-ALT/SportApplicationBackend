package be.ucll.repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import be.ucll.model.Event;
import be.ucll.model.Sport;
import be.ucll.model.User;
import jakarta.annotation.PostConstruct;

@Component
public class DbInitializer {

    private UserRepository userRepository;
    private EventRepository eventRepository;
    private SportRepository sportRepository;

    public DbInitializer(
        UserRepository userRepository,
        EventRepository eventRepository,
        SportRepository sportRepository) {
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.sportRepository = sportRepository;
    }

    @PostConstruct
    public void initialize() {

        List<User> users = createUsers();
        List<Event> events = createEvents();
        List<Sport> sports = createSports();
        
        connectSportsEvents(sports, events);

        sportRepository.saveAll(sports);
        eventRepository.saveAll(events);
        users.forEach(user -> userRepository.addUser(user));
    }

    public static List<User> createUsers() {
        List<User> users = new ArrayList<>();

        User user1 = new User("John Doe", 25, "john.doe@ucll.be");
        User user2 = new User("Jane Toe", 30, "jane.toe@ucll.be");

        users.add(user1);
        users.add(user2);

        return users;
    }

    public static List<Event> createEvents() {
        List<Event> events = new ArrayList<>();

        LocalDate startdate1 = LocalDate.parse("2024-03-08");
        LocalDate startdate2 = LocalDate.parse("2024-06-05");
        LocalDate enddate1 = LocalDate.parse("2024-03-28");
        LocalDate enddate2 = LocalDate.parse("2024-06-27");
        Event event1 = new Event("Sportsday-UCLL", startdate1, enddate1);
        Event event2 = new Event("Sportsday-KUL", startdate2, enddate2);

        events.add(event1);
        events.add(event2);

        return events;
    }

    public static List<Sport> createSports() {
        List<Sport> sports = new ArrayList<>();

        Sport sport1 = new Sport("aerobics", true, 50);
        Sport sport2 = new Sport("soccer", true, 15);
        Sport sport3 = new Sport("boxing", false, 1);
        

        sports.add(sport1);
        sports.add(sport2);
        sports.add(sport3);

        return sports;
    }

    public static void connectSportsEvents(List<Sport> sports, List<Event> events) {
        events.get(0).addSport(sports.get(2));
        events.get(1).addSport(sports.get(1));
        events.get(1).addSport(sports.get(0));

    }
}
