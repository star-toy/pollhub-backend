package world.startoy.polling.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CloudFrontConfig {
    @Value("${cloudfront.url}")
    private String cloudfrontUrl;

    public String getCloudfrontUrl() {
        return cloudfrontUrl;
    }
}