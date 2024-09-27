package world.startoy.polling.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CloudFrontConfig {


    String cloudfrontUrl = System.getenv("CLOUDFRONT_URL");

    public String getCloudfrontUrl() {
        return cloudfrontUrl;
    }
}