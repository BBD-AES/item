package com.bbd.item.adapter.out.persistence;

import com.bbd.item.domain.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemJpaRepository extends JpaRepository<ItemJpaEntity, String> {



}
