package com.startoy.pollhub.domain;

/*
    baseEntity에 설정할 내용... 모든 테이블에서 공통적으로 사용하는 컬럼을 작성
    @MappedSuperClass를 사용하여 공통 속성 적용!!
    이를 통해서 공통으로 사용되는 컬럼을 지정하여 해당 클래스를 상속하여 사용할 수 있음.
 */

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass // 상속받는 엔티티의 컬럼 매핑
@EntityListeners(value = {AuditingEntityListener.class}) // JPA Auditing 기능을 사용하여 생성일시, 수정일시 등을 자동으로 관리합니다.
@Getter
public class BaseEntities {

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "created_by", length = 20, nullable = false)
    private String createdBy;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "updated_by", length = 20)
    private String updatedBy;



}
