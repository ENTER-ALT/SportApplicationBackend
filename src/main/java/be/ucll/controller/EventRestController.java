package be.ucll.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import be.ucll.model.DomainException;
import be.ucll.model.Event;
import be.ucll.model.Sport;
import be.ucll.service.EventService;
import be.ucll.service.ServiceException;
import be.ucll.service.SportService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/events")
public class EventRestController {

    private EventService eventService;
    private SportService sportService;

    public EventRestController(
        EventService eventService,
        SportService sportService) {
        this.eventService = eventService;
        this.sportService = sportService;
    }

    @GetMapping
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    @GetMapping("/kudos/{eventId}/{sportId}")
    public int calculateKudosByEventAndSport(
        @PathVariable(value = "eventId") Long eventId,
        @PathVariable(value = "sportId") Long sportId) {
        return sportService.calculateKudos(eventId, sportId);
    }

    @GetMapping("/sport")
    public List<Event> calculateKudosByEventAndSport(
        @RequestParam(value = "minNumOfPlayers", required = true) int minNumOfPlayers) {
        return sportService.getEventsBySportWithMinNumOfPLayers(minNumOfPlayers);
    }

    @PostMapping()
    public Event addEvent(@Valid @RequestBody Event Event) {
        return eventService.addEvent(Event);
    }

    @PostMapping("/sport/{eventName}")
    public Sport addSportToEvent(
        @Valid @RequestBody Sport sport,
        @PathVariable(value = "eventName") String eventName) {
        return sportService.addSportToEvent(sport, eventName);
    }

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<Map<String, String>> handlDomainException(DomainException ex, WebRequest request) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("DomainException", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<Map<String, String>> handlServiceException(ServiceException ex, WebRequest request) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("ServiceException", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getFieldErrors()) {
            String fieldName = error.getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        }
        return errors;
    }

}
