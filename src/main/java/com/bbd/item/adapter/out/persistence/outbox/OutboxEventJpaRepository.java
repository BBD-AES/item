package com.bbd.item.adapter.out.persistence.outbox;

import com.bbd.item.domain.model.outbox.OutboxStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OutboxEventJpaRepository extends JpaRepository<OutboxEventJpaEntity, String> {

    // 상태가 같은 애들만 조회
    List<OutboxEventJpaEntity> findByStatus(OutboxStatus outboxStatus);

    // 다른 인트턴스가 잡고 있는거 제외하고 PENDING 조회
    @Query(
            value = """
                    SELECT *
                    FROM outbox_event
                    WHERE status = :status
                    ORDER BY created_at ASC
                    LIMIT :batchSize
                    FOR UPDATE SKIP LOCKED
                    """,
            nativeQuery = true
    )
    List<OutboxEventJpaEntity> findByStatusOrderByCreatedAtAsc(
            @Param("status") String status,
            @Param("batchSize") int batchSize
    );

}
