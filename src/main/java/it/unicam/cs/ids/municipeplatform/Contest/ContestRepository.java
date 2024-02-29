package it.unicam.cs.ids.municipeplatform.Contest;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
@Repository
public interface ContestRepository extends CrudRepository<ContestEntity,Long>{
    @Query("SELECT c FROM ContestEntity c WHERE c.name = :name AND c.initialDate BETWEEN :startDate AND :endDate AND c.type = :type")
    List<ContestEntity> findByNameAndInitialDateBetweenAndType(
            @Param("name") String name,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("type") String type);
}
