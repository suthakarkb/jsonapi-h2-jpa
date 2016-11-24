package com.sc.csl.retail.crm.persistence.dao;

import com.sc.csl.retail.crm.persistence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Collection;

public interface UserRepository extends JpaRepository<User, Long> {
}
