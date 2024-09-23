package world.startoy.polling.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "pl_vote")
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vote_id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "vote_uid", unique = true, nullable = false, length = 36)
    private String voteUid;

    @NotNull
    @Column(name = "poll_uid", nullable = false, length = 36)
    private String pollUid;

    @NotNull
    @Column(name = "poll_option_uid", nullable = false, length = 36)
    private String pollOptionUid;

    @NotNull
    @Column(name = "voter_ip", nullable = false, length = 255)
    private String voterIp;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
