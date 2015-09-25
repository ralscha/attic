#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.repository;

import java.util.List;

import ${package}.entity.User;

public interface UserRepositoryCustom {
	List<User> filter(String name, String firstName, String email);
}
