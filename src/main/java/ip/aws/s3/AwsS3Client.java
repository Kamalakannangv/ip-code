package ip.aws.s3;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

public class AwsS3Client {
	
	AmazonS3 amazonS3;

	public AwsS3Client() {      
		amazonS3 = AmazonS3ClientBuilder.standard()
                .withRegion(Regions.US_EAST_1)
                .build();   	
	}
		
	public AmazonS3 getS3Client() {
		return amazonS3;
	}

}
