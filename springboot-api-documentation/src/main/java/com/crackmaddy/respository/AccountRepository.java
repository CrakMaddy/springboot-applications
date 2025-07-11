package com.crackmaddy.respository;

import com.crackmaddy.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByCustomerId(Long id);

    @Transactional
    @Modifying
    void deleteByCustomerId(Long customerId);
}
