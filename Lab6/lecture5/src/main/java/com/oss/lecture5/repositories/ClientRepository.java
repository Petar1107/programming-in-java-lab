package com.oss.lecture5.repositories;

import com.oss.lecture5.models.Address;
import com.oss.lecture5.models.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findByAddress(Address address);

    Page<Client> findAll (Pageable pageable);
}
