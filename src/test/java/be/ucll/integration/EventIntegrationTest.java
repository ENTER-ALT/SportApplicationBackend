package be.ucll.integration;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import be.ucll.model.Event;
import be.ucll.repository.DbInitializer;
import be.ucll.utilits.TimeTracker;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient()
@Sql("classpath:schema.sql")
public class EventIntegrationTest {
    
    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private DbInitializer dbInitializer;

    @BeforeEach
    public void setupDatabasesAndTime() {
        TimeTracker.resetToday();
        TimeTracker.resetYear();
        dbInitializer.initialize();
    }

    @Test
    public void givenEvent_whenAddEvent_thenEventAdded() {
        LocalDate startdate = LocalDate.parse("2023-03-28");
        LocalDate enddate = LocalDate.parse("2024-06-27");
        Event event = new Event("RandomLol", startdate, enddate);
        
        webTestClient.post()
                .uri("/events")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(event)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .json("{name: '" + event.getName() + "', startDate: " + event.getStartDate() + "}");
    }
}
