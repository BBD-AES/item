package com.bbd.item.adapter.out.persistence.item;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemJpaRepository extends JpaRepository<ItemJpaEntity, String>, ItemQueryRepository  {


    @Query(
            value = """
                    WITH page_keys AS (
                        SELECT i.sku, i.name
                        FROM item i
                        ORDER BY i.name ASC, i.sku ASC
                        OFFSET :#{#pageable.offset}
                        LIMIT :#{#pageable.pageSize}
                    )
                    SELECT i.*
                    FROM page_keys pk
                    JOIN item i ON i.sku = pk.sku
                    ORDER BY pk.name ASC, pk.sku ASC
                    """,
            countQuery = """
                    SELECT COUNT(*)
                    FROM bbd.item
                    """,
            nativeQuery = true
    )
    Page<ItemJpaEntity> getNative(Pageable pageable);

    List<ItemJpaEntity> findAllByOrderBySkuAsc(Pageable pageable);

    List<ItemJpaEntity> findBySkuGreaterThanOrderBySkuAsc(String sku, Pageable pageable);

}
