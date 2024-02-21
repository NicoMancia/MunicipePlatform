package it.unicam.cs.ids.municipeplatform.Content;
import it.unicam.cs.ids.municipeplatform.Contest.ContestEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository

public interface ContentRepository extends CrudRepository<ContentEntity,Long>{
    @Query("SELECT c FROM ContentEntity c WHERE c.name = :name AND c.description = :description AND c.creationDate = :creationDate")
    List<ContentEntity> findByNameAndDescriptionAndCreationDate(
            @Param("name") String name,
            @Param("description") String description,
            @Param("creationDate") Date creationDate);
}
