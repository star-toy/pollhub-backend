package world.startoy.polling.usecase;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PollDTO {
    private String pollUid;
    private int pollSeq;
    private String pollCategory;
    private String pollDescription;
    private List<PollOptionDTO> options;
}
