package be.ucll.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import be.ucll.model.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {  
    Event findByNameIgnoreCase(String name);
    Boolean existsByName(String name);
}
