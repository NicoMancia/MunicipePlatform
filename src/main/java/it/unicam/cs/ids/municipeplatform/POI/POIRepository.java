package it.unicam.cs.ids.municipeplatform.POI;

import it.unicam.cs.ids.municipeplatform.Location;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface POIRepository extends CrudRepository<POIEntity,Long>{
    List<POIEntity> findByNameAndDescriptionAndCategoryAndLocation(
            String name, String description, PoiCategory category, Location location);
}
