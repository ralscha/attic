package ch.rasc.tttracker.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import ch.rasc.tttracker.domain.Task;

public interface TaskRepository extends PagingAndSortingRepository<Task, Long> {
}