#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ${package}.entity.AccessLog;

public interface AccessLogRepository extends JpaRepository<AccessLog, Long> {
	// nothing here
}
