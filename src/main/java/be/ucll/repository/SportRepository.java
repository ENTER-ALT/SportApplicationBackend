package be.ucll.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import be.ucll.model.Sport;

@Repository
public interface SportRepository extends JpaRepository<Sport, Long> { 
    Sport findByNameIgnoreCase(String name); 
    List<Sport> findByNumOfPlayersGreaterThan(int numOfPLayers);
}
