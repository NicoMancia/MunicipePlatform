package it.unicam.cs.ids.municipeplatform.Event;

import jdk.jfr.Event;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
@Repository
public interface EventRepository extends CrudRepository<EventEntity,Long> {
    @Query("SELECT e FROM EventEntity e WHERE e.name = :name AND e.description = :description AND e.category = :category AND " +
            "(e.startDate BETWEEN :startDate AND :endDate OR e.endDate BETWEEN :startDate AND :endDate)")
    List<EventEntity> findEventsByNameAndDescriptionAndCategoryWithinDateRange(
            @Param("name") String name,
            @Param("description") String description,
            @Param("category") EventCategory category,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);
}
