package be.ucll.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import be.ucll.model.Event;
import be.ucll.model.Sport;
import be.ucll.repository.DbInitializer;
import be.ucll.repository.SportRepository;
import be.ucll.service.EventService;
import be.ucll.service.SportService;
import be.ucll.utilits.TimeTracker;

@ExtendWith(MockitoExtension.class)
public class SportServiceTest {
    
    @Mock
    private SportRepository sportRepository;
    @Mock
    private EventService eventService;

    @InjectMocks
    private SportService sportService;
    
    @BeforeEach
    public void resetTime() {
        TimeTracker.resetToday();
        TimeTracker.resetYear();
    }

    @Test
    public void givenSportAndEvent_whenAddingSportToEvent_thenSportToEventAdded() {
        // Given
        List<Sport> sports = DbInitializer.createSports();
        List<Event> events = DbInitializer.createEvents();
        Event expectedEvent = events.get(0);
        Sport expectedSport = sports.get(0);

        when(eventService.getEventByName(expectedEvent.getName())).thenReturn(expectedEvent);
        when(sportRepository.save(expectedSport)).thenReturn(expectedSport);
        
        // When
        Sport actualSport = sportService.addSportToEvent(expectedSport, expectedEvent.getName());

        // Then
        assertEquals(expectedSport, actualSport);
        verify(eventService).getEventByName(expectedEvent.getName());
        verify(eventService).updateEvent(expectedEvent);
        verify(sportRepository).save(expectedSport);
    }
}
