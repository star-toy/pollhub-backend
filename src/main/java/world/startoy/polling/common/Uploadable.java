package world.startoy.polling.common;

public interface Uploadable {

    // 각 엔티티가 구현해야 할 메서드를 정의
    Long getId(); // 엔티티의 ID 반환 (PostId, PollOptionId 등)
    String getUploadableType(); // 업로드 가능한 타입 명시 (예: "Post", "PollOption")

}
