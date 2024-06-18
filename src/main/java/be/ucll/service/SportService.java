package be.ucll.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import be.ucll.model.Event;
import be.ucll.model.Sport;
import be.ucll.repository.SportRepository;
import be.ucll.utilits.TimeTracker;

@Service
public class SportService {
    
    private SportRepository sportRepository;
    private EventService eventService;

    public SportService(
        SportRepository sportRepository,
        EventService eventService) {
        this.sportRepository = sportRepository;
        this.eventService = eventService;
    }

    public List<Sport> getAllSports() {
        return sportRepository.findAll();
    }

    public Sport addSport(Sport sportData) {
        return sportRepository.save(sportData);
    }

    public Sport addSportToEvent(Sport sportData, String eventName) {
        Event event = eventService.getEventByName(eventName);
        Sport addedSPort = addSport(sportData);
        event.addSport(addedSPort);
        eventService.updateEvent(event);
        return addedSPort;
    }

    public int calculateKudos(Long eventId, Long sportId) {
        Event event = eventService.getEventById(eventId);
        Sport sport = sportRepository.findById(sportId).orElse(null);

        List<Sport> eventSports = event.getSports();
        Boolean hasSport = eventSports.stream().anyMatch(s -> s.getName().equals(sport.getName()));
        if (!hasSport) {
            throw new ServiceException("Sport is not present in the event.");
        }

        LocalDate today = TimeTracker.getToday();
        LocalDate startDate = event.getStartDate();
        LocalDate endDate = event.getEndDate();
        Boolean kudoAssignedDuringTheEvent = today.isAfter(startDate) && today.isBefore(endDate);
        if (!kudoAssignedDuringTheEvent){
            throw new ServiceException("Kudos can only be assigned during the event.");
        }

        int result = 0;
        Boolean teamSport = sport.getTeamSport();
        if (!teamSport) {
            result += 5;
        }
        else {
            result += 2 * sport.getNumOfPlayers();
        } 
        return result;
    }

    public Sport getEventById(Long id) {
        throwErrorIfNotExists(id);
        return sportRepository.findById(id).orElse(null);
    }

    public List<Sport> getSportByNumOfPlayerGreater(int numOfPLayers) {
        return sportRepository.findByNumOfPlayersGreaterThan(numOfPLayers);
    }

    public List<Event> getEventsBySportWithMinNumOfPLayers(int minNumOfPlayers) {
        List<Sport> sports = getSportByNumOfPlayerGreater(minNumOfPlayers);
        List<Event> events = eventService.getAllEvents();

        List<Event> result = new ArrayList<>();
        events.forEach(event -> {
            sports.forEach(sport -> {
                if (event.getSports().contains(sport) && !result.contains(event)){
                    result.add(event);
                }
            }); 
        });
        return result;
    }

    public void throwErrorIfExists(String name) {
        if (sportRepository.findByNameIgnoreCase(name) != null) {
            throw new ServiceException("Sport with name " + name + " already exists");
        }
    }

    public void throwErrorIfNotExists(Long id) {
        if (!sportRepository.existsById(id)) {
            throw new ServiceException("Sport with id " + id + " does not exist");
        }
    }
}
