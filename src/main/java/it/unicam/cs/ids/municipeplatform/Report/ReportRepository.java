package it.unicam.cs.ids.municipeplatform.Report;

import it.unicam.cs.ids.municipeplatform.User.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends CrudRepository<ReportEntity,Long> {
    @Query("SELECT t FROM ReportEntity t WHERE t.Id = :reportId")
    ReportEntity findReportById(@Param("reportId") Long reportId);
}

