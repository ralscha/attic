package ch.rasc.perf;

import java.lang.reflect.Field;
import java.util.List;

import org.boon.core.reflection.BeanUtils;
import org.boon.core.reflection.Reflection;

public class Refl {

	public static void main(String[] args) {
		List<Field> fields = Reflection.getAllFields(User.class);
		for (Field field : fields) {
			System.out.println(field);
		}

		User u = new User();
		u.setId(1L);

		if (Reflection.respondsTo(u, "getId")) {
			System.out.println(Reflection.invoke(u, "getId"));
			System.out.println(BeanUtils.getPropertyLong(u, "id"));
		}
	}

}
