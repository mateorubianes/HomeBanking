package com.mindhub.homebanking.controllers;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import com.mindhub.homebanking.utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class AccountController
{
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    @RequestMapping("/accounts")
    public  List<AccountDTO> getAccounts()
    {
        return accountRepository.findAll().stream().map(account -> new AccountDTO(account)).collect(Collectors.toList());
    }

    @RequestMapping("/accounts/{id}")
    public AccountDTO getAccounts(@PathVariable Long id)
    {
        if(accountRepository.findById(id).isPresent())
            return new AccountDTO(accountRepository.findById(id).get());
        return null;
    }

    @GetMapping( path = "/clients/current/accounts")
    public List<AccountDTO> getClientAccounts (Authentication authentication)
    {
        Client client = this.clientRepository.findByEmail(authentication.getName());

        return client.getAccounts().stream().map(AccountDTO::new).collect(Collectors.toList());
    }

    @PostMapping(path = "/clients/current/accounts")
    public ResponseEntity<Object> createAccount(Authentication authentication)
    {
        Client client = this.clientRepository.findByEmail(authentication.getName());

        if(client.getAccounts().size() >= 3)
        {
            return new ResponseEntity<>("Max accounts numbers reached",HttpStatus.FORBIDDEN);
        }
        accountRepository.save(new Account(client, AccountUtils.RandomVIN(accountRepository), LocalDateTime.now(),0, AccountType.CHECKING));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @DeleteMapping(path = "/clients/current/accounts")
    public ResponseEntity<Object> deleteAccount(Authentication authentication, @RequestParam String deletedAccountNumber, @RequestParam String foundsAccountNumber)
    {
        Client client = this.clientRepository.findByEmail(authentication.getName());
        Account deletedAccount = accountRepository.findByNumber(deletedAccountNumber);
        Account foundsAccount = accountRepository.findByNumber(foundsAccountNumber);

        if( accountRepository.findByNumber(deletedAccountNumber) == null )
        {
            return new ResponseEntity<>("The account that you want to delete does not exist",HttpStatus.FORBIDDEN);
        }
        if( accountRepository.findByNumber(foundsAccountNumber) == null )
        {
            return new ResponseEntity<>("The account that you want to transfer the founds does not exist",HttpStatus.FORBIDDEN);
        }
        if( !client.getAccounts().contains(deletedAccount) || !client.getAccounts().contains(foundsAccount))
        {
            return new ResponseEntity<>("One of the accounts or both does not belong to you",HttpStatus.FORBIDDEN);
        }

        transactionRepository.save( new Transaction( TransactionType.CREDIT, deletedAccount.getBalance(), "founds have been transferred because" + " " +deletedAccountNumber + " " + "has been deleted", foundsAccount) );
        foundsAccount.setBalance(foundsAccount.getBalance() + deletedAccount.getBalance());
        accountRepository.save(foundsAccount);
        List<Transaction>deletedTransactions = transactionRepository.findByAccount(deletedAccount);
        transactionRepository.deleteAll( deletedTransactions );
        accountRepository.delete( deletedAccount );
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping ("/AccountStatus")
    public ResponseEntity<?> downloadAccountStatus (@RequestParam String fromDate, @RequestParam String thruDate)
    {
        LocalDate localDateFrom = LocalDate.parse(fromDate);
        LocalDate localDateThru = LocalDate.parse(thruDate);

        LocalTime localTime = LocalTime.MIDNIGHT;

        LocalDateTime fromDateParsed = LocalDateTime.of(localDateFrom, localTime);
        LocalDateTime thruDateParsed = LocalDateTime.of(localDateThru, localTime);

        List<Transaction> transactions = transactionRepository.findByDateBetween(fromDateParsed, thruDateParsed);

        return new ResponseEntity<>(transactions,HttpStatus.CREATED);
    }
}
