package ch.rasc.glacier;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ConcurrentNavigableMap;

import org.mapdb.DB;
import org.mapdb.DBMaker;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.glacier.AmazonGlacierClient;
import com.amazonaws.services.glacier.model.CreateVaultRequest;
import com.amazonaws.services.glacier.transfer.ArchiveTransferManager;
import com.amazonaws.services.glacier.transfer.UploadResult;

public class Backup {

	public static void main(String[] args) {
		if (args.length == 5) {
			AWSCredentials credentials = new BasicAWSCredentials(args[0], args[1]);
			AmazonGlacierClient client = new AmazonGlacierClient(credentials);
			client.setEndpoint("https://glacier.us-east-1.amazonaws.com/");

			String plainFileToBackup = args[2];
			String encryptedFileToBackup = plainFileToBackup + ".enc";
			String vaultName = args[3];
			String password = args[4];

			client.createVault(new CreateVaultRequest().withVaultName(vaultName));

			try {

				Path encryptedPath = Paths.get(encryptedFileToBackup);
				Crypto.writeEncryptedFile(password, Paths.get(plainFileToBackup),
						encryptedPath);

				ArchiveTransferManager atm = new ArchiveTransferManager(client,
						credentials);

				UploadResult result = atm.upload(vaultName, encryptedFileToBackup,
						encryptedPath.toFile());
				System.out.println("Archive ID: " + result.getArchiveId());

				DB db = DBMaker.newFileDB(new File("glacierdb")).closeOnJvmShutdown()
						.make();
				ConcurrentNavigableMap<Long, String> files = db.getTreeMap("glacier");

				files.put(System.currentTimeMillis(),
						result.getArchiveId() + ";" + encryptedFileToBackup);

				db.commit();
				db.close();

			}
			catch (Exception e) {
				System.err.println(e);
			}

		}
		else {
			System.out.println("Backup <accessKey> <secretKey> <file> <vaultName>");
		}

	}

}
