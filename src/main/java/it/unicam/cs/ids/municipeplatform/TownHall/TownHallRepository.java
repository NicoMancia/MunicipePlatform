package it.unicam.cs.ids.municipeplatform.TownHall;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
@Repository


public interface TownHallRepository extends CrudRepository<TownHallEntity,Long> {
}
