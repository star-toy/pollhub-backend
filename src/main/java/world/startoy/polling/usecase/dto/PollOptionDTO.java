package world.startoy.polling.usecase.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PollOptionDTO {
    private String pollOptionUid;
    private int pollOptionSeq;
    private String pollOptionText;
}