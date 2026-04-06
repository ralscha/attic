package ch.rasc.glacier;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.glacier.AmazonGlacierClient;
import com.amazonaws.services.glacier.model.DescribeVaultOutput;
import com.amazonaws.services.glacier.model.ListVaultsRequest;
import com.amazonaws.services.glacier.model.ListVaultsResult;

public class ListVaults {

	public static void main(String[] args) {

		if (args.length == 2) {
			AWSCredentials credentials = new BasicAWSCredentials(args[0], args[1]);
			AmazonGlacierClient client = new AmazonGlacierClient(credentials);
			client.setEndpoint("https://glacier.us-east-1.amazonaws.com/");

			ListVaultsResult lvResult = client.listVaults(new ListVaultsRequest());

			for (DescribeVaultOutput v : lvResult.getVaultList()) {
				System.out.println(v.getVaultName());
				System.out.println(v.getCreationDate());
				System.out.println(v.getLastInventoryDate());
				System.out.println(v.getNumberOfArchives());
			}
		}
	}

}
