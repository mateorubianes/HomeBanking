package com.mindhub.homebanking.controllers;
import com.mindhub.homebanking.dtos.PaymentServiceDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import com.mindhub.homebanking.utils.CardUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CardController {
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private AccountRepository accountRepository;

    @PostMapping(path = "/clients/current/cards")
    public ResponseEntity<Object> createCard (@RequestParam CardColor cardColor, @RequestParam CardType cardType, Authentication authentication)
    {
        Client client = this.clientRepository.findByEmail(authentication.getName());

        if( cardRepository.findByClientAndType(client, cardType).size() >= 3 )
        {
            return new ResponseEntity<>("Max cards numbers reached", HttpStatus.FORBIDDEN);
        }
        cardRepository.save(new Card(cardType, cardColor, CardUtils.RandomCardNumber(cardRepository), client));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @DeleteMapping(path = "/clients/current/cards")
    public ResponseEntity<Object> deleteCard (@RequestParam String cardNumber, Authentication authentication)
    {
        Client client = this.clientRepository.findByEmail(authentication.getName());

        if( !client.getCards().contains(cardRepository.findByNumber(cardNumber)) )
        {
            return new ResponseEntity<>("This card does not belong to you", HttpStatus.FORBIDDEN);
        }
        if( cardRepository.findByNumber(cardNumber) == null )
        {
            return new ResponseEntity<>("Invalid card Number", HttpStatus.FORBIDDEN);
        }

        cardRepository.delete(cardRepository.findByNumber(cardNumber));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(path = "/payments")
    public ResponseEntity<Object> paymentService (@RequestBody PaymentServiceDTO paymentServiceDTO, Authentication authentication)
    {
        Client client = this.clientRepository.findByEmail(authentication.getName());
        double amount = paymentServiceDTO.getAmount();;
        String description = paymentServiceDTO.getDescription();
        Set<Account> accounts = client.getAccounts().stream().filter(account -> account.getBalance() > amount).collect(Collectors.toSet());
        if( accounts.isEmpty() )
        {
            return new ResponseEntity<>("Insufficient founds", HttpStatus.FORBIDDEN);
        }
        Account account = accounts.stream().findAny().get();
        transactionRepository.save( new Transaction(TransactionType.DEBIT,-amount,description,account) );
        account.setBalance( account.getBalance() - amount );
        accountRepository.save(account);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
