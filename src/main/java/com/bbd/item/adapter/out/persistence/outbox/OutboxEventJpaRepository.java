package com.bbd.item.adapter.out.persistence.outbox;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutboxEventJpaRepository extends JpaRepository<OutboxEventJpaEntity, String> {

}
