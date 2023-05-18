package com.mindhub.homebanking.controllers;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RestController
@RequestMapping("/api")
public class TransactionController
{
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    ClientRepository clientRepository;

    @Transactional
    @PostMapping(path = "/transactions")
    public ResponseEntity<Object> makeTransaction(@RequestParam String fromAccountNumber, @RequestParam String toAccountNumber,
                                                  @RequestParam double amount, @RequestParam String description,
                                                  Authentication authentication)
    {
        Client client = clientRepository.findByEmail(authentication.getName());
        Account sourceAccount = accountRepository.findByNumber(fromAccountNumber);
        Account destinationAccount = accountRepository.findByNumber(toAccountNumber);

        if( !client.getAccounts().contains(sourceAccount) )
        {
            return new ResponseEntity<>("the source account does not belong to this client", HttpStatus.FORBIDDEN);
        }
        if( description.isEmpty() || amount == 0 || toAccountNumber.isEmpty() || fromAccountNumber.isEmpty() )
        {
            return new ResponseEntity<>("The fields must be complete", HttpStatus.FORBIDDEN);
        }
        if( sourceAccount.getBalance() < amount )
        {
            return new ResponseEntity<>("Insufficient funds", HttpStatus.FORBIDDEN);
        }
        if( destinationAccount == null )
        {
            return new ResponseEntity<>("Destination account not found", HttpStatus.FORBIDDEN);
        }
        if( sourceAccount == null )
        {
            return new ResponseEntity<>("Source account not found", HttpStatus.FORBIDDEN);
        }
        if( fromAccountNumber.equals(toAccountNumber) || toAccountNumber.equals(fromAccountNumber) )
        {
            return new ResponseEntity<>("You can not transfer funds to the same account ", HttpStatus.FORBIDDEN);
        }
        if( amount < 0 )
        {
            return new ResponseEntity<>("can not transfer negative founds", HttpStatus.FORBIDDEN);
        }

        transactionRepository.save( new Transaction(TransactionType.DEBIT,-amount,description + " " + "("+ toAccountNumber + ")",sourceAccount) );
        transactionRepository.save( new Transaction(TransactionType.CREDIT,amount,description + " " + "("+ fromAccountNumber + ")",destinationAccount) );
        sourceAccount.setBalance( sourceAccount.getBalance() - amount );
        destinationAccount.setBalance(destinationAccount.getBalance() + amount );
        accountRepository.save(sourceAccount);
        accountRepository.save(destinationAccount);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }


}
