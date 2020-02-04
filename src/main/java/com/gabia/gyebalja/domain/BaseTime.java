package com.gabia.gyebalja.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * @MappedSuperclass
 * :JPA Entity 클래스들이 BaseTime 을 상속할 경우 createdDate, modifiedDate 등의 필드들도 컬럼으로 인식하도록 함
 *
 * @EntityListeners(AuditingEntityListener.class)
 * :Auditing 기능을 포함
 * */
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseTime {

    @Column(name = "created_date")
    @CreatedDate
    private LocalDateTime createdDate;

    @Column(name = "modified_date")
    @LastModifiedDate
    private LocalDateTime modifiedDate;
}
