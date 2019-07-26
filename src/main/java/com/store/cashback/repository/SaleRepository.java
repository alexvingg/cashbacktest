package com.store.cashback.repository;

import com.store.cashback.entity.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    Page<Sale> findByCreatedAtBetween(Date dateStart, Date dateFinal, Pageable pageable);

}
