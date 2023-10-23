package rekognition;

import java.nio.ByteBuffer;
import java.util.List;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;

//Copyright 2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.
//PDX-License-Identifier: MIT-0 (For details, see https://github.com/awsdocs/amazon-rekognition-developer-guide/blob/master/LICENSE-SAMPLECODE.)

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.AmazonRekognitionException;
import com.amazonaws.services.rekognition.model.DetectModerationLabelsRequest;
import com.amazonaws.services.rekognition.model.DetectModerationLabelsResult;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.ModerationLabel;

public class DetectModerationLabels {

	public static void main(String[] args) throws Exception {
		String urlText = "https://firebasestorage.googleapis.com/v0/b/resshare-request-cb5e2.appspot.com/o/books%2Fkhong-ai-co-quyen-phan-xet-bo-hay-khong-nhung-le-hoi-man-ro-152d71.jpg?alt=media&token=82ede1f3-27ee-4c5a-9229-b794c9683070";
		checkOffsensive(urlText);
	}

	public static boolean checkOffsensive(String urlText) throws Exception {
		if(urlText == null)
			return false;
		if ( "".equals(urlText))
			return false;
			
		// String photo =
		// "khong-ai-co-quyen-phan-xet-bo-hay-khong-nhung-le-hoi-man-ro-152d71.jpg";
		// String bucket = "devrekognitionimagebook";
		// ProfileCredentialsProvider creds = new
		// ProfileCredentialsProvider("myProfile");
		//// AmazonS3 s3Client = new AmazonS3ClientBuilder()
		//// .withCredentials(creds)
		//// .build()
		// InstanceProfileCredentialsProvider credentials =
		// InstanceProfileCredentialsProvider.createAsyncRefreshingProvider(true);
		//
		// AmazonS3Client.builder()
		// .withCredentials(credentials)
		// AmazonRekognition rekognitionClient =
		// AmazonRekognitionClientBuilder.defaultClient()
		// withCredentials(creds).build();
		AmazonRekognition rekognitionClient;

		AWSCredentials credentials;
		try {
			credentials = new BasicAWSCredentials("AKIAQWQRVSJ5CXM7OHDV", "BOFEzE2QGw9gdz5M1P00PQTp4XaiGzCswkcMFVPs");
		} catch (Exception e) {
			throw new AmazonClientException("Cannot load the credentials from the credential profiles file. "
					+ "Please make sure that your credentials file is at the correct "
					+ "location (/Users/userid/.aws/credentials), and is in a valid format.", e);
		}

		rekognitionClient = AmazonRekognitionClientBuilder.standard().withRegion(Regions.AP_SOUTHEAST_1)
				.withCredentials(new AWSStaticCredentialsProvider(credentials)).build();

		//
		// String urlText =
		// "https://firebasestorage.googleapis.com/v0/b/resshare-request-cb5e2.appspot.com/o/books%2Fnude.png?alt=media&token=9ae44734-dc84-4f96-aad9-94c13a3211c6";
		byte[] arrrbytes = ImageRecover.recoverImageFromUrl(urlText);
		ByteBuffer bytes = ByteBuffer.wrap(arrrbytes);
		// https://firebasestorage.googleapis.com/v0/b/resshare-request-cb5e2.appspot.com/o/books%2Fnude.png?alt=media&token=9ae44734-dc84-4f96-aad9-94c13a3211c6
		DetectModerationLabelsRequest request = new DetectModerationLabelsRequest()
				.withImage(new Image().withBytes(bytes));

		// DetectModerationLabelsRequest request = new DetectModerationLabelsRequest()
		// .withImage(new Image().withS3Object(new
		// S3Object().withName(photo).withBucket(bucket)))
		// .withMinConfidence(60F);

		try {
			DetectModerationLabelsResult result = rekognitionClient.detectModerationLabels(request);
			List<ModerationLabel> labels = result.getModerationLabels();
			// System.out.println("Detected labels for " + photo);
			int i = 0;
			for (ModerationLabel label : labels) {
				System.out.println("Label: " + label.getName() + "\n Confidence: " + label.getConfidence().toString()
						+ "%" + "\n Parent:" + label.getParentName());
				i++;
			}
			return i > 0;
		} catch (AmazonRekognitionException e) {
			e.printStackTrace();
		}
		return false;
	}
}
