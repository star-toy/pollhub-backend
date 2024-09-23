package world.startoy.polling.usecase.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PollCreateRequest {
    private int pollSeq;
    private String pollCategory;
    private String pollDescription;
    private List<PollOptionCreateRequest> pollOptions;
}
