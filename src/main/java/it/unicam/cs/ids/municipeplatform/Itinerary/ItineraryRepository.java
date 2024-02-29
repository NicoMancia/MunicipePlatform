package it.unicam.cs.ids.municipeplatform.Itinerary;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
@Repository
public interface ItineraryRepository extends CrudRepository<ItineraryEntity,Long> {
    @Query("SELECT e FROM ItineraryEntity e WHERE e.name = :name AND e.description = :description AND e.creationDate = :creationDate")
    List<ItineraryEntity> findByNameAndDescriptionAndCreationDate(
            String name, String description, LocalDateTime creationDate);
}
