package ip.aws.s3;

public class S3MainClass {
	
	public static void main(String[] args) {
		AwsS3Client client = new AwsS3Client();
		String s3Url = client.getS3Client().getUrl("ip.aws.s3.staticwebsite", "index.html").toString();
		System.out.println("URL: " + s3Url);
	}

}
