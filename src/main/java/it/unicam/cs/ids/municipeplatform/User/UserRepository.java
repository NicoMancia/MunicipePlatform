package it.unicam.cs.ids.municipeplatform.User;
import it.unicam.cs.ids.municipeplatform.TownHall.TownHallEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<UserEntity,Long>
{
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM UserEntity u WHERE u.username = :username OR u.email = :email")
    boolean exists(@Param("username") String username, @Param("email") String email);

    @Query("SELECT t FROM UserEntity t WHERE t.idUser = :userId")
    List<UserEntity> findTownHallRolesByUserId(@Param("userId") Long userId);
}
