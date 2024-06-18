package be.ucll.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "EVENTS")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

        @NotBlank(message = INVALID_NAME_EXCEPTION)
    @Column(name = "NAME")
    private String name;

    @Column(name = "START_DATE")
    private LocalDate startDate;

    @Column(name = "END_DATE")
    private LocalDate endDate;

    @ManyToMany
    @JoinTable(
        name = "EVENTS_SPORTS",
        joinColumns = @JoinColumn(name = "EVENT_ID"),
        inverseJoinColumns = @JoinColumn(name = "SPORT_ID")
        )
    private List<Sport> sports;

    public static final String INVALID_NAME_EXCEPTION = "Event name is required";
    public static final String ENDDATE_BEFORE_STARTDATE_EXCEPTION = "Event endDate must be after the event startDate.";
    public static final String INVALID_STARTDATE_EXCEPTION = "Start date is required";
    public static final String INVALID_ENDDATE_EXCEPTION = "End date is required";

    protected Event() {};

    public Event(String name, LocalDate startDate, LocalDate endDate) {
        setName(name);
        setStartDate(startDate);
        setEndDate(endDate);
        sports = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public void setStartDate(LocalDate startDate) {
        if (startDate == null) {
            throw new DomainException(INVALID_STARTDATE_EXCEPTION);
        }
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        if (endDate == null) {
            throw new DomainException(INVALID_ENDDATE_EXCEPTION);
        }
        if (startDate == null) {
            throw new DomainException(INVALID_STARTDATE_EXCEPTION);
        }
        if (endDate.isBefore(this.startDate)) {
            throw new DomainException(ENDDATE_BEFORE_STARTDATE_EXCEPTION);
        }
        this.endDate = endDate;
    }

    public List<Sport> getSports() {
        return sports;
    }

    public void addSport(Sport sport) {
        sports.add(sport);
    }
}
