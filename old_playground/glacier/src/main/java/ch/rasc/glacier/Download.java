package ch.rasc.glacier;

import java.io.FileNotFoundException;
import java.nio.file.Paths;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.glacier.AmazonGlacierClient;
import com.amazonaws.services.glacier.transfer.ArchiveTransferManager;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sqs.AmazonSQSClient;

public class Download {
	@SuppressWarnings("unused")
	public static void main(String[] args)
			throws AmazonServiceException, AmazonClientException, FileNotFoundException {
		if (args.length == 2) {
			AWSCredentials credentials = new BasicAWSCredentials(args[0], args[1]);
			AmazonGlacierClient glacierClient = new AmazonGlacierClient(credentials);
			glacierClient.setEndpoint("https://glacier.us-east-1.amazonaws.com/");

			AmazonSQSClient sqsClient = new AmazonSQSClient(credentials);
			AmazonSNSClient snsClient = new AmazonSNSClient(credentials);
			sqsClient.setEndpoint("https://glacier.us-east-1.amazonaws.com/");
			snsClient.setEndpoint("https://glacier.us-east-1.amazonaws.com/");

			try {
				ArchiveTransferManager atm = new ArchiveTransferManager(glacierClient,
						sqsClient, snsClient);

				String archiveId = "K94...";
				atm.download("testvault", archiveId, Paths.get("e:/test.txt").toFile());

			}
			catch (Exception e) {
				System.err.println(e);
			}

		}

	}
}
