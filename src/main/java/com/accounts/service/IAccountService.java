package com.accounts.service;

import java.util.Optional;

import com.accounts.dto.CustomerDto;
import com.accounts.entity.Customer;

public interface IAccountService {

	void createAccount(CustomerDto customerDto);

	CustomerDto fetchAccount(String mobileNumber);
	
	Boolean updateAccount(CustomerDto customerDto);

	Boolean deleteAccount(String mobileNumber);
}
