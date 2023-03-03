package com.oss.vjezba4.repositories;

import com.oss.vjezba4.models.Address;
import com.oss.vjezba4.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findByAddress(Address address);
}
