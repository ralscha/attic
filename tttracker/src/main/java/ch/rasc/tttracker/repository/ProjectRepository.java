package ch.rasc.tttracker.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import ch.rasc.tttracker.domain.Project;

public interface ProjectRepository extends PagingAndSortingRepository<Project, Long> {
}