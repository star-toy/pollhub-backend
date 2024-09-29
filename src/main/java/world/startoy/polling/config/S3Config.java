package world.startoy.polling.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Config {
    private static final Logger logger = LoggerFactory.getLogger(S3Config.class);

    @Lazy
    @Bean
    public AmazonS3 amazonS3() {
        String accessKey = System.getenv("AWS_ACCESS_KEY");
        String secretKey = System.getenv("AWS_SECRET_KEY");
        String region = System.getenv("AWS_REGION");

        if (accessKey == null || secretKey == null || region == null) {
            logger.error("AWS credentials or region are not set. Please check your environment variables.");
            throw new IllegalStateException("AWS credentials or region are not properly configured");
        }

        try {
            AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
            return AmazonS3ClientBuilder
                    .standard()
                    .withCredentials(new AWSStaticCredentialsProvider(credentials))
                    .withRegion(region)
                    .build();
        } catch (Exception e) {
            logger.error("Failed to create AmazonS3 client", e);
            throw new RuntimeException("Failed to create AmazonS3 client", e);
        }
    }
}
