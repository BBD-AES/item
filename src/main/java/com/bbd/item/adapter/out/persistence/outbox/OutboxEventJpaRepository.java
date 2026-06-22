package com.bbd.item.adapter.out.persistence.outbox;

import com.bbd.item.domain.model.outbox.OutboxStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OutboxEventJpaRepository extends JpaRepository<OutboxEventJpaEntity, String> {

    // 상태가 같은 애들만 조회
    List<OutboxEventJpaEntity> findByStatus(OutboxStatus outboxStatus);


    // 상태가 같은 애들만 조회 (페이징)
    List<OutboxEventJpaEntity> findByStatusOrderByCreatedAtAsc(
            OutboxStatus outboxStatus,
            Pageable pageable
    );

}
