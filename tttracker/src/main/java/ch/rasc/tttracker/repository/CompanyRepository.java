package ch.rasc.tttracker.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import ch.rasc.tttracker.domain.Company;

public interface CompanyRepository extends PagingAndSortingRepository<Company, Long> {
}