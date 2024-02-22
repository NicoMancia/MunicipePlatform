package it.unicam.cs.ids.municipeplatform.Event;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.Date;
import java.util.List;
@Repository
public interface EventRepository extends CrudRepository<EventEntity,Long> {
    @Query("SELECT e FROM EventEntity e WHERE e.name = :name AND e.description = :description AND " +
            "(e.startDate BETWEEN :startDate AND :endDate OR e.endDate BETWEEN :startDate AND :endDate)")
    List<EventEntity> findEventsByNameAndDescriptionWithinDateRange(
            @Param("name") String name,
            @Param("description") String description,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate);
}
