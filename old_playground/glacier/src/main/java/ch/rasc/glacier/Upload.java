package ch.rasc.glacier;

import java.io.FileNotFoundException;
import java.nio.file.Paths;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.glacier.AmazonGlacierClient;
import com.amazonaws.services.glacier.model.CreateVaultRequest;
import com.amazonaws.services.glacier.transfer.ArchiveTransferManager;
import com.amazonaws.services.glacier.transfer.UploadResult;

public class Upload {
	public static void main(String[] args)
			throws AmazonServiceException, AmazonClientException, FileNotFoundException {
		if (args.length == 2) {
			AWSCredentials credentials = new BasicAWSCredentials(args[0], args[1]);
			AmazonGlacierClient client = new AmazonGlacierClient(credentials);
			client.setEndpoint("https://glacier.us-east-1.amazonaws.com/");

			client.createVault(new CreateVaultRequest().withVaultName("testvault"));

			ArchiveTransferManager atm = new ArchiveTransferManager(client, credentials);
			UploadResult result = atm.upload("testvault", "a test file",
					Paths.get("e:/test.txt").toFile());
			System.out.println("Archive ID: " + result.getArchiveId());

		}

	}
}
