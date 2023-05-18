package com.mindhub.homebanking.controllers;
import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;

@RestController
@RequestMapping("/api")
public class LoanController
{
    @Autowired
    LoanRepository loanRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    ClientLoanRepository clientLoanRepository;
    @Autowired
    TransactionRepository transactionRepository;

    @GetMapping( path = "/loans" )
    public List<LoanDTO> getLoans ()
    {
        return loanRepository.findAll().stream().map(LoanDTO::new).collect(Collectors.toList());
    }

    @Transactional
    @PostMapping(path = "/loans")
    public ResponseEntity<Object> loanApplication (@RequestBody LoanApplicationDTO loanApplicationDTO, Authentication authentication)
    {
        Client client = clientRepository.findByEmail(authentication.getName());
        double amount = loanApplicationDTO.getAmount();
        int payments = loanApplicationDTO.getPayments();
        String accountToNumber = loanApplicationDTO.getToAccountNumber();
        Loan loan = loanRepository.findById(loanApplicationDTO.getLoanId()).orElse(null);
        Account account = accountRepository.findByNumber(accountToNumber);
        double fees = loan.getFees();

        if( amount <= 0 || payments <= 0 )
        {
            return new ResponseEntity<>("Invalid data", HttpStatus.FORBIDDEN);
        }
        if( loan == null )
        {
            return new ResponseEntity<>("The loan does not exist", HttpStatus.FORBIDDEN);
        }
        if( !loan.getPayments().contains(payments) )
        {
            return new ResponseEntity<>("The requested amount of payments is invalid", HttpStatus.FORBIDDEN);
        }
        if( amount > loan.getMaxAmount() )
        {
            return new ResponseEntity<>("You can not request that amount from that loan", HttpStatus.FORBIDDEN);
        }
        if( accountRepository.findByNumber(accountToNumber) == null )
        {
            return new ResponseEntity<>("Destiny account does not exist", HttpStatus.FORBIDDEN);
        }
        if( !client.getAccounts().contains(account) )
        {
            return new ResponseEntity<>("That account does not belong to you", HttpStatus.FORBIDDEN);
        }
        transactionRepository.save( new Transaction(TransactionType.CREDIT, amount, loan.getName() + " " + "loan approved", account) );
        account.setBalance( account.getBalance() + amount );
        accountRepository.save(account);
        amount = ((fees*amount)/100) + amount;
        clientLoanRepository.save( new ClientLoan(amount,payments,client,loan) );

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
