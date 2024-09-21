package world.startoy.polling.usecase;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PollOptionDTO {
    private String pollOptionUid;
    private int pollOptionSeq;
    private String pollOptionText;
}
