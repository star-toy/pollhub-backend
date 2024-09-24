package world.startoy.polling.common;

public interface Uploadable {

    // 각 엔티티가 구현해야 할 메서드를 정의
    String getLinkedUid(); // 엔티티의 고유 식별자(UID) 반환 (예: postUid, optionUid)
    String getUploadableType(); // 업로드 가능한 타입 명시 (예: "Post", "PollOption")

}
