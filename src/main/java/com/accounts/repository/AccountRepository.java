package com.accounts.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.accounts.entity.Accounts;

@Repository
public interface AccountRepository extends JpaRepository<Accounts,Long>{

	Optional<Accounts> findByCustomerId(int customerId);
	
	@Transactional
	@Modifying
	void deleteByCustomerId(int customerId);
}
