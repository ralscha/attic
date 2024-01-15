package ch.rasc.tttracker.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import ch.rasc.tttracker.domain.TaskLog;

public interface TaskLogRepository extends PagingAndSortingRepository<TaskLog, Long> {
}