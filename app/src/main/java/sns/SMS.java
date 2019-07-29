package sns;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;

public class SMS {
    private String message;
    private String number;
    private AmazonSNSClient snsClient;

    public SMS(String number, String message) {
        //Demo IAM Role
        AWSCredentials awsCredentials = new BasicAWSCredentials("AKIAUO6Y6LMDLWY77MVQ", "4P5prqGLSgCpMZ58dL9KBm6MIlgO2YOhoKgsVBby");
        final AmazonSNSClient client = new AmazonSNSClient(awsCredentials);
        client.setRegion(Region.getRegion(Regions.US_EAST_1));
        snsClient = new AmazonSNSClient(awsCredentials);

        this.message = message;
        this.number = number;
    }

    public void sendSMSMessage() {
        PublishResult result = snsClient.publish(new PublishRequest()
                .withMessage(message)
                .withPhoneNumber(number));
        System.out.println(result); // Prints the message ID.
    }
}
