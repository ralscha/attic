#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ${package}.entity.Configuration;

public interface ConfigurationRepository extends JpaRepository<Configuration, Long> {
	// nothing here
}
