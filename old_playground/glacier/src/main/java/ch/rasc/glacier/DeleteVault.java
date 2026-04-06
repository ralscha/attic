package ch.rasc.glacier;

import java.io.File;
import java.util.concurrent.ConcurrentNavigableMap;

import org.mapdb.DB;
import org.mapdb.DBMaker;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.glacier.AmazonGlacierClient;
import com.amazonaws.services.glacier.model.DeleteArchiveRequest;

public class DeleteVault {

	public static void main(String[] args) {

		if (args.length == 2) {
			AWSCredentials credentials = new BasicAWSCredentials(args[0], args[1]);
			AmazonGlacierClient client = new AmazonGlacierClient(credentials);
			client.setEndpoint("https://glacier.us-east-1.amazonaws.com/");

			// client.deleteVault(new
			// DeleteVaultRequest().withVaultName("testvault"));

			DB db = DBMaker.newFileDB(new File("D:\\ws\\playground\\glacier\\glacierdb"))
					.make();

			ConcurrentNavigableMap<Long, String> files = db.getTreeMap("glacier");
			for (String fileName : files.values()) {
				String[] split = fileName.split(";");
				String archivId = split[0];
				System.out.println(archivId);
				DeleteArchiveRequest deleteArchiveRequest = new DeleteArchiveRequest(
						"hosteurope", archivId);
				client.deleteArchive(deleteArchiveRequest);

			}

			db.commit();
			db.close();
		}
	}

}
