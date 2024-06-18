package be.ucll.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "SPORTS")
public class Sport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

        @NotBlank(message = INVALID_NAME_EXCEPTION)
    @Column(name = "NAME")
    private String name;

    @Column(name = "TEAM_SPORT")
    private boolean teamSport;

        @Positive(message = NEGATIVE_PLAYERS_EXCEPTION)
    @Column(name = "NUM_OF_PLAYERS")
    private int numOfPlayers;

    public static final String INVALID_NAME_EXCEPTION = "Event name is required";
    public static final String ENDDATE_BEFORE_STARTDATE_EXCEPTION = "Event endDate must be after the event startDate.";
    public static final String INVALID_STARTDATE_EXCEPTION = "Start date is required";
    public static final String INVALID_ENDDATE_EXCEPTION = "End date is required";
    public static final String NEGATIVE_PLAYERS_EXCEPTION = "Number of players must be positive";

    protected Sport() {};

    public Sport(String name, boolean teamSport, int numOfPlayers) {
        setName(name);
        setTeamSport(teamSport);
        setNumOfPlayers(numOfPlayers);
    }

    public String getName() {
        return name;
    }

    public boolean getTeamSport() {
        return teamSport;
    }

    public int getNumOfPlayers() {
        return numOfPlayers;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public void setTeamSport(boolean teamSport) {
        this.teamSport = teamSport;
    }

    private void setNumOfPlayers(int numOfPlayers) {
        this.numOfPlayers = numOfPlayers;
    }
}
