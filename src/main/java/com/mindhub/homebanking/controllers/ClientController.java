package com.mindhub.homebanking.controllers;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.AccountType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController //retorna en formato JSON, Indica que mi clase es un controlador
@RequestMapping("/api") //Me define la ruta del controlador
public class ClientController
{
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AccountRepository accountRepository;

    @RequestMapping("/clients")
    public List<ClientDTO> getClients()
    {
        return clientRepository.findAll().stream().map(client -> new ClientDTO(client)).collect(Collectors.toList());
    }

    @RequestMapping("/clients/{id}")
    public ClientDTO getClients(@PathVariable Long id)
    {
        if(clientRepository.findById(id).isPresent())
            return new ClientDTO(clientRepository.findById(id).get());
        return null;
    }

    @RequestMapping("/clients/current")
    public ClientDTO getAuthClient(Authentication authentication)
    {
        Client client = clientRepository.findByEmail(authentication.getName());
        return new ClientDTO(client);
    }

    @PostMapping(path = "/clients")
    public ResponseEntity<Object> createClient(@RequestParam String firstName,@RequestParam String lastName,
                                               @RequestParam String email,@RequestParam String password)
    {
        if(firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty())
        {
            return new ResponseEntity<>("Missing data",HttpStatus.FORBIDDEN);
        }
        if(clientRepository.findByEmail(email) != null)
        {
            return new ResponseEntity<>("Name already in use",HttpStatus.FORBIDDEN);
        }
        Client client = new Client(firstName, lastName, email, passwordEncoder.encode(password));
        clientRepository.save(client);
        accountRepository.save(new Account(client, RandomVIN(), LocalDateTime.now(),0, AccountType.CHECKING));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public String RandomVIN()
    {
        return "VIN-"+ ((int) ( (Math.random() * 99999999) ) );
    }
}
