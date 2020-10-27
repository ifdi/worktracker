package com.worktracker.repository;

import com.worktracker.model.Work;
import com.worktracker.repository.projection.ReportByProjectProjection;
import com.worktracker.repository.projection.ReportByUserProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface WorkRepository extends JpaRepository<Work, Long> {

    @Query(value = "select t.id        as taskid, "
            + "       t.task_type as taskType, "
            + "       t.name      as taskName, "
            + "       w.hours     as hours, "
            + "       u.id        as userId , "
            + "       u.name      as username "
            + "from work w "
            + "         join tasks t on w.task_id = t.id "
            + "         join projects p on t.project_id = p.id and p.id = ?1 "
            + "         join users u on w.user_id = u.id "
            + "where w.date between ?2 and ?3 "
            + "order by t.task_type, w.date; ", nativeQuery = true)
    List<ReportByProjectProjection> getReportByProject(Integer projectId, LocalDate startDate, LocalDate endDate);

    @Query(value = "select p.id        as projectId, "
            + "       p.name      as projectName, "
            + "       t.task_type as taskType, "
            + "       t.name      as taskName,"
            + "       t.id        as taskId, "
            + "       w.hours     as hours, "
            + "       w.date      as date "
            + "from work w "
            + "         join users u on w.user_id = u.id and u.id = ?1 "
            + "         join tasks t on t.id = w.task_id "
            + "         join projects p on p.id = t.project_id "
            + "where w.date between ?2 and ?3 "
            + "order by p.id, t.task_type, w.date", nativeQuery = true)
    List<ReportByUserProjection> getReportByUser(Long userId, LocalDate startDate, LocalDate endDate);

}

