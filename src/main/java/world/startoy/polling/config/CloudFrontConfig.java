package world.startoy.polling.config;

import org.springframework.stereotype.Component;

@Component
public class CloudFrontConfig {


    String cloudfrontUrl = System.getenv("CLOUDFRONT_URL");
    
    public String getCloudfrontUrl() {
        return cloudfrontUrl;
    }

    public String getCloudfrontUrl(String fileName) {
        return cloudfrontUrl.concat("/").concat(fileName);
    }
}
