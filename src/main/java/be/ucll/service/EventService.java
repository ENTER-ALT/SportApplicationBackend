package be.ucll.service;

import java.util.List;

import org.springframework.stereotype.Service;

import be.ucll.model.Event;
import be.ucll.repository.EventRepository;

@Service
public class EventService {
    
    private EventRepository eventRepository;
    

    public EventService(
        EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event addEvent(Event eventData) {
        String eventName = eventData.getName();
        throwErrorIfExists(eventName);
        return eventRepository.save(eventData);
    }

    public Event updateEvent(Event event) {
        return eventRepository.save(event);
    }

    public Event getEventByName(String name) {
        throwErrorIfNotExists(name);
        Event existingEvent = eventRepository.findByNameIgnoreCase(name);
        return existingEvent;
    }

    public Event getEventById(Long id) {
        throwErrorIfNotExists(id);
        return eventRepository.findById(id).orElse(null);
    }



    public void throwErrorIfExists(String name) {
        if (eventRepository.existsByName(name)) {
            throw new ServiceException("Event with name " + name + " already exists");
        }
    }

    public void throwErrorIfNotExists(String name) {
        if (!eventRepository.existsByName(name)) {
            throw new ServiceException("Event with name " + name + " does not exist");
        }
    }

    public void throwErrorIfNotExists(Long id) {
        if (!eventRepository.existsById(id)) {
            throw new ServiceException("Event with id " + id + " does not exist");
        }
    }
}
