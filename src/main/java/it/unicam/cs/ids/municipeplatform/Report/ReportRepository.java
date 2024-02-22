package it.unicam.cs.ids.municipeplatform.Report;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends CrudRepository<ReportEntity,Long> {

}

