package it.unicam.cs.ids.municipeplatform.Itinerary;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
@Repository
public interface ItineraryRepository extends CrudRepository<ItineraryEntity,Long> {
    List<ItineraryEntity> findByNameAndDescriptionAndCreationDate(
            String name, String description, LocalDateTime creationDate);
}
