package ch.rasc.tttracker.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import ch.rasc.tttracker.domain.User;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {

	User findByUserName(String userName);

}