package ch.rasc.eds.starter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import ch.rasc.eds.starter.bean.Department;
import ch.rasc.eds.starter.bean.User;
import ch.rasc.eds.starter.repository.DepartmentRepository;
import ch.rasc.eds.starter.repository.UserRepository;

@Component
public class InitialDataLoad {

	private final UserRepository userRepository;

	private final DepartmentRepository departmentRepository;

	@Autowired
	public InitialDataLoad(UserRepository userRepository,
			DepartmentRepository departmentRepository) throws IOException {
		this.userRepository = userRepository;
		this.departmentRepository = departmentRepository;
		init(new ClassPathResource("randomdata.csv.compressed"));
	}

	private void init(ClassPathResource randomDataResource) throws IOException {
		if (this.userRepository.count() == 0) {

			try (InputStream is = randomDataResource.getInputStream();
					BufferedReader reader = new BufferedReader(new InputStreamReader(
							new InflaterInputStream(is, new Inflater(true)),
							StandardCharsets.UTF_8))) {
				Set<String> departments = new HashSet<>();

				reader.lines().map(line -> line.split(Pattern.quote("|")))
						.map(s -> new User(s[0], s[1], s[2], s[3]))
						.peek(u -> departments.add(u.getDepartment()))
						.forEach(this.userRepository::save);

				departments.stream().map(Department::new)
						.forEach(this.departmentRepository::save);
			}
		}

	}

}
