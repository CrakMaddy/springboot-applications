package com.crackmaddy.service.impl;

import com.crackmaddy.constants.AccountConstants;
import com.crackmaddy.dto.CustomerDto;
import com.crackmaddy.entity.Account;
import com.crackmaddy.entity.Customer;
import com.crackmaddy.exception.CustomerAlreadyExistsException;
import com.crackmaddy.mapper.CustomerMapper;
import com.crackmaddy.respository.AccountRepository;
import com.crackmaddy.respository.CustomerRepository;
import com.crackmaddy.service.IAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements IAccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;

    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer = CustomerMapper.toCustomer(customerDto, new Customer());
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customer.getMobileNumber());

        if (optionalCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer already registered with given mobile number: " + customer.getMobileNumber());
        }
        customer.setCreatedAt(LocalDateTime.now());
        customer.setCreatedBy("Anonymous");
        Customer savedCustomer = customerRepository.save(customer);
        accountRepository.save(createNewAccount(savedCustomer));
    }

    private Account createNewAccount(Customer customer) {
        Account account = new Account();
        account.setCustomerId(customer.getId());
        long randomAccountNumber = 1000000000L + new Random().nextInt(900000000);

        account.setCreatedAt(LocalDateTime.now());
        account.setCreatedBy("Anonymous");
        account.setAccountNumber(randomAccountNumber);
        account.setAccountType(AccountConstants.SAVINGS);
        account.setBranchAddress(AccountConstants.ADDRESS);
        return account;
    }
}
