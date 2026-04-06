package ch.rasc.glacier;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.glacier.AmazonGlacierClient;
import com.amazonaws.services.glacier.model.DescribeJobRequest;
import com.amazonaws.services.glacier.model.DescribeJobResult;
import com.amazonaws.services.glacier.model.GetJobOutputRequest;
import com.amazonaws.services.glacier.model.GetJobOutputResult;
import com.amazonaws.services.glacier.model.InitiateJobRequest;
import com.amazonaws.services.glacier.model.InitiateJobResult;
import com.amazonaws.services.glacier.model.JobParameters;

public class VaultInventory {

	public static void main(String[] args) throws InterruptedException, IOException {

		if (args.length == 2) {
			AWSCredentials credentials = new BasicAWSCredentials(args[0], args[1]);
			AmazonGlacierClient client = new AmazonGlacierClient(credentials);
			client.setEndpoint("https://glacier.us-east-1.amazonaws.com/");

			InitiateJobRequest initJobRequest = new InitiateJobRequest()
					.withVaultName("testvault").withJobParameters(new JobParameters()
							.withFormat("CSV").withType("inventory-retrieval"));

			InitiateJobResult initJobResult = client.initiateJob(initJobRequest);
			String jobId = initJobResult.getJobId();
			System.out.println("Job ID: " + jobId);

			System.out.println("Waiting 3 hours....");
			TimeUnit.HOURS.sleep(3);

			DescribeJobRequest describeJobRequest = new DescribeJobRequest("testvault",
					jobId);
			DescribeJobResult result = client.describeJob(describeJobRequest);
			while (!result.isCompleted()) {
				System.out.println("Waiting 30 minutes....");
				TimeUnit.MINUTES.sleep(30);
				result = client.describeJob(describeJobRequest);
			}

			System.out.println("Job complete");

			GetJobOutputResult jobOutputResult = client
					.getJobOutput(new GetJobOutputRequest().withVaultName("testvault")
							.withJobId(jobId));

			Files.copy(jobOutputResult.getBody(), Paths.get("joboutput.csv"));

		}

	}

}
