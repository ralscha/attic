#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.service;

import static ch.ralscha.extdirectspring.annotation.ExtDirectMethodType.TREE_LOAD;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ${package}.repository.DepartmentRepository;
import ${package}.repository.UserRepository;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Service
public class TreeLoadService {

	private final UserRepository userRepository;
	private final DepartmentRepository departmentRepository;

	@Autowired
	public TreeLoadService(UserRepository userRepository,
			DepartmentRepository departmentRepository) {
		this.userRepository = userRepository;
		this.departmentRepository = departmentRepository;
	}

	@ExtDirectMethod(TREE_LOAD)
	public List<Node> getTree(String node) {

		if ("root".equals(node) || !StringUtils.hasText(node)) {

			List<Node> children = departmentRepository
					.findAll(new Sort(Direction.ASC, "name")).stream()
					.map(d -> new Node(d.getName(), d.getName(), false, false, null))
					.collect(Collectors.toList());
			Node root = new Node("Company", "Company", false, true, children);

			return Collections.singletonList(root);
		}

		return userRepository
				.findByDepartmentOrderByLastNameAsc(node)
				.stream()
				.map(u -> {
					return new Node(u.getId().toString(), u.getLastName() + " "
							+ u.getFirstName(), true, false, null);
				}).collect(Collectors.toList());

	}

	@JsonInclude(Include.NON_NULL)
	public static class Node {
		public final String id;

		public final String text;

		public final boolean leaf;

		public final boolean expanded;

		public final List<Node> children;

		public Node(String id, String text, boolean leaf, boolean expanded,
				List<Node> children) {
			this.id = id;
			this.text = text;
			this.leaf = leaf;
			this.expanded = expanded;
			this.children = children;
		}

	}

}
