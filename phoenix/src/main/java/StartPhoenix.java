import ch.rasc.embeddedtc.EmbeddedTomcat;

public class StartPhoenix {
	public static void main(String[] args) throws Exception {
		String skipJars = "antlr*.jar;aopalliance*.jar;aspectjrt*.jar;asm*.jar;cglib*.jar;commons*.jar;dom4j*.jar;guava*.jar;extjs*.jar;hibernate*.jar;";
		skipJars += "jackson*.jar;jandex*.jar;javassist*.jar;jboss*.jar;jcl-over-slf4j*.jar;jsr305*.jar;jul-to-slf4j*.jar;liquibase*.jar;";
		skipJars += "logback*.jar;mysema-commons-lang*.jar;querydsl*.jar;slf4j-api*.jar;snakeyaml*.jar;spring*.jar;";
		skipJars += "validation-api*.jar;yuicompressor*.jar";
		skipJars += "tomcat*.jar;ecj*.jar;h2*.jar;extdirectspring*.jar";

		// Comment out the following line to activate production profile
		System.setProperty("spring.profiles.active", "development");

		EmbeddedTomcat.create().skipJarsDefaultJarScanner(skipJars)
				.setContextFile("./src/main/config/tomcat.xml").startAndWait();
	}
}
