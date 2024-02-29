package it.unicam.cs.ids.municipeplatform.Report;

import it.unicam.cs.ids.municipeplatform.Content.ContentEntity;
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
    @Query("SELECT t FROM ReportEntity t WHERE t.status = 2")
    List<ReportEntity> findAllPending();
    @Query("SELECT t FROM ReportEntity t WHERE t.eventEntity.Id = :contentId")
    List<ReportEntity> findReportsByEventId(@Param("contentId") Long contentId);
    @Query("SELECT t FROM ReportEntity t WHERE t.poiEntity.Id = :contentId")
    List<ReportEntity> findReportsByPOIId(@Param("contentId") Long contentId);
    @Query("SELECT t FROM ReportEntity t WHERE t.itineraryEntity.Id = :contentId")
    List<ReportEntity> findReportsByItineraryId(@Param("contentId") Long contentId);
}

