package com.sc.csl.retail.crm.persistence.dao;

import com.sc.csl.retail.crm.persistence.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Collection;

public interface ClientRepository extends JpaRepository<Client, Long> {
    @Query("from #{#entityName} a where a.relationshipManagerId = ?1 ")
    Collection<Client> getClientForUser(String user);
}
