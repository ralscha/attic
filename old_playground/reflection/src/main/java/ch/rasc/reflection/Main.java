package ch.rasc.reflection;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Main {
	public static void main(String... args) throws Exception {

		System.out.println(
				ModelGenerator.generateJavascript(User.class, OutputFormat.TOUCH));

		ObjectMapper mapper = new ObjectMapper();
		System.out.println(mapper.writeValueAsString(new User()));

		ModelBean model = ModelGenerator.createModel(User.class);
		model.addField(new ModelFieldBean("test", ModelType.BOOLEAN));
		System.out.println(ModelGenerator.generateJavascript(model, OutputFormat.EXTJS));
	}

}
