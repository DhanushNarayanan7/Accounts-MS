package com.accounts.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.accounts.constants.AccountConstants;
import com.accounts.dto.AccountDto;
import com.accounts.dto.CustomerDto;
import com.accounts.entity.Accounts;
import com.accounts.entity.Customer;
import com.accounts.exception.CustomerAlreadyExistsException;
import com.accounts.exception.ResourceNotFoundException;
import com.accounts.mapper.AccountMapper;
import com.accounts.mapper.CustomerMapper;
import com.accounts.repository.AccountRepository;
import com.accounts.repository.CustomerRepository;
import com.accounts.service.IAccountService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class AccountServiceImpl implements IAccountService {

	private AccountRepository accountRepository;
	private CustomerRepository customerRepository;

	@Override
	public void createAccount(CustomerDto customerDto) {
		Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
		Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customerDto.getMobileNumber());
		if (optionalCustomer.isPresent()) {
			throw new CustomerAlreadyExistsException(
					"Customer already registered with given mobileNumber " + customerDto.getMobileNumber());
		}
		Customer savedCustomer = customerRepository.save(customer);
		Accounts newAccount = createNewAccount(savedCustomer);
		accountRepository.save(newAccount);
	}

	private Accounts createNewAccount(Customer customer) {
		Accounts accounts = new Accounts();
		accounts.setCustomerId(customer.getCustomerId());
		long accNumber = 1000000000L + new Random().nextInt(900000000);
		;
		accounts.setAccountNumber(accNumber);
		accounts.setAccountType(AccountConstants.SAVINGS);
		accounts.setBranchAddress(AccountConstants.ADDRESS);
		return accounts;
	}

	@Override
	public CustomerDto fetchAccount(String mobileNumber) {
		Customer customer = customerRepository.findByMobileNumber(mobileNumber)
				.orElseThrow(() -> new ResourceNotFoundException("Customer", "MobileNumber", mobileNumber));

		Accounts account = accountRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
				() -> new ResourceNotFoundException("Account", "CustomerId", customer.getCustomerId().toString()));
		AccountDto accountDto = AccountMapper.mapToAccountsDto(account, new AccountDto());
		CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
		customerDto.setAccountDto(accountDto);
		return customerDto;
	}

	@Override
	public Boolean updateAccount(CustomerDto customerDto) {
		Boolean isUpdated = false;
		AccountDto accountDto = customerDto.getAccountDto();
		if (accountDto != null) {
			Accounts account = accountRepository.findById(accountDto.getAccountNumber())
					.orElseThrow(() -> new ResourceNotFoundException("Account", "Account Number",
							customerDto.getAccountDto().getAccountNumber().toString()));
			AccountMapper.mapToAccounts(accountDto, account);
			account = accountRepository.save(account);

			Customer customer = customerRepository.findById(account.getCustomerId()).orElseThrow(
					() -> new ResourceNotFoundException("Customer", "Mobile Number", customerDto.getMobileNumber()));
			CustomerMapper.mapToCustomer(customerDto, customer);
			customer = customerRepository.save(customer);
			isUpdated = true;
		}

		return isUpdated;
	}

	@Override
	public Boolean deleteAccount(String mobileNumber) {
		Boolean isDeleted = false;
		Customer customer = customerRepository.findByMobileNumber(mobileNumber)
				.orElseThrow(() -> new ResourceNotFoundException("Customer", "Mobile Number", mobileNumber));
		accountRepository.deleteByCustomerId(customer.getCustomerId());
		customerRepository.deleteById(customer.getCustomerId());
		isDeleted=true;
		return isDeleted;
	}

}
