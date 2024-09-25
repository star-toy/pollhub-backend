package world.startoy.polling.usecase.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VoteCreateResponse {
    private String voteUid;
    private String pollUid;
    private String votedPollOptionUid;
    private List<PollOptionResponse> pollOptions;
}
