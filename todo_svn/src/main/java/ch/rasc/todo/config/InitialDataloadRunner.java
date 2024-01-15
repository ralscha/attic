package ch.rasc.todo.config;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URI;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.fluttercode.datafactory.impl.DataFactory;
import org.fluttercode.datafactory.impl.DefaultContentDataValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import ch.rasc.todo.entity.Todo;
import ch.rasc.todo.entity.Type;
import de.svenjacobs.loremipsum.LoremIpsum;
import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;
import net.coobird.thumbnailator.Thumbnails;

@Service
public class InitialDataloadRunner implements ApplicationRunner {

	@Autowired
	private Db db;

	@Autowired
	private AppConfig appConfig;

	@Override
	@Transactional
	public void run(ApplicationArguments args) throws Exception {
		if (args.getNonOptionArgs().contains("dataload")) {

			System.out.println("LOADING INITIAL DATA");
			DataFactory df = new DataFactory();
			df.setContentDataValues(new DefaultContentDataValues());
			LoremIpsum loremIpsum = new LoremIpsum();
			
			List<Image> imgs = new ArrayList<>();
		    String user = appConfig.getSmbuser();
		    String pass = appConfig.getSmbpwd();
		    String path = URI.create(appConfig.getImagepath() ).toString();
		    
			if (!StringUtils.isEmpty(user) && !StringUtils.isEmpty(pass)
					&& !StringUtils.isEmpty(path) && pass.contains("smb:")) {
				NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(null,
						user, pass);
			    SmbFile dir = new SmbFile(path, auth);		
				for (SmbFile entry : dir.listFiles()) {
					try (SmbFileInputStream in = new SmbFileInputStream(entry)) {
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					try {
							Thumbnails.of(in).height(200).outputFormat("jpg")
									.toOutputStream(baos);
						imgs.add(new Image(entry.getName(), baos.toByteArray()));
					}
					catch (Exception e) {
							System.out
									.println(entry.getName() + " --> " + e.getMessage());
						continue;
					} 
				}
				}
			}
			else {
				Path local = Paths.get(path);
				try (DirectoryStream<Path> stream = Files.newDirectoryStream(local)) {
				if (stream != null) {
					for (Path entry : stream) {
					    File image = entry.toFile();
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						try {
								Thumbnails.of(image).height(200).outputFormat("jpg")
										.toOutputStream(baos);
							imgs.add(new Image(image.getName(), baos.toByteArray()));
						}
						catch (Exception e) {
								System.out.println(
										image.getName() + " --> " + e.getMessage());
							continue;
						}
					}
				}
		    }
			}
		    
			for (int i = 0; i < 100_000; i++) {
				if (i % 1000 == 0) {
					System.out.println(i);
				}
				Todo todo = new Todo();
				String lorem = loremIpsum.getWords(rnd.nextInt(250) + 50, rnd.nextInt(50));
				todo.setDescription(lorem);
				//todo.setDescription(df.getRandomText(50, 400));
				
				todo.setDue(Date.from(LocalDateTime
						.of(2017 + rnd.nextInt(2), rnd.nextInt(12) + 1,
								rnd.nextInt(28) + 1, rnd.nextInt(24), rnd.nextInt(60))
						.atZone(ZoneId.systemDefault()).toInstant()));
				lorem = loremIpsum.getWords(rnd.nextInt(25) + 10, rnd.nextInt(50));
				//todo.setTitle(df.getRandomText(10, 40));
				todo.setTitle(lorem);
				todo.setType(rnd.nextBoolean() ? Type.WORK : Type.PRIVATE);
				int rndi = rnd.nextInt(imgs.size()) + 1;
				todo.setImageName(imgs.get(rndi - 1).name);
				todo.setThumbnail(imgs.get(rndi - 1).data);

				this.db.em().persist(todo);
			}
			System.out.println("THE END");
		}
	}

	private static Random rnd = new Random();

	private class Image {
		public Image(String name, byte[] data) {
			super();
			this.data = data;
			this.name = name;
		}
		byte[] data;
		String name;
	}
}
