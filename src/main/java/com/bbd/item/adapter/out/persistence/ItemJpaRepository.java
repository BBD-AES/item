package com.bbd.item.adapter.out.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemJpaRepository extends JpaRepository<ItemJpaEntity, String>, ItemQueryRepository  {

    @Query(
            value = """
                    WITH page_keys AS (
                        SELECT i.sku, i.name
                        FROM bbd2.item i
                        ORDER BY i.name ASC, i.sku ASC
                        OFFSET :#{#pageable.offset}
                        LIMIT :#{#pageable.pageSize}
                    )
                    SELECT i.*
                    FROM page_keys pk
                    JOIN bbd2.item i ON i.sku = pk.sku
                    ORDER BY pk.name ASC, pk.sku ASC
                    """,
            countQuery = """
                    SELECT COUNT(*)
                    FROM bbd2.item
                    """,
            nativeQuery = true
    )
    Page<ItemJpaEntity> getNative(Pageable pageable);

}
