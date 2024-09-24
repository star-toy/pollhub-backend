package world.startoy.polling.adapter.repository;

import world.startoy.polling.domain.FileStorage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileStorageRepository extends JpaRepository<FileStorage, String> {

    // fileUid를 기반으로 파일 정보를 조회하는 메서드
    Optional<FileStorage> findByFileUid(String fileUid);
}
