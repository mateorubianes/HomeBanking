package com.mindhub.homebanking.models;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Account
{
    // - - - - - - - - - ATRIBUTOS - - - - - - - - - //

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long ID;
    @ManyToOne (fetch = FetchType.EAGER) //Usamos EAGER por que
    @JoinColumn (name = "client_id")
    private Client client;
    @OneToMany (mappedBy = "account", fetch = FetchType.EAGER)
    private Set<Transaction> transactions = new HashSet<>();
    private String number;
    private LocalDateTime creationDate;
    private double balance;
    private AccountType accountType;

    // - - - - - - - - - CONSTRUCTORES - - - - - - - - - //

    public Account() {}

    public Account(Client client,String number, LocalDateTime creationDate, double balance, AccountType accountType)
    {
        this.client = client;
        this.number = number;
        this.creationDate = creationDate;
        this.balance = balance;
        this.accountType = accountType;
    }

    // - - - - - - - - - METODOS - - - - - - - - - //


    public long getID()
    {
        return ID;
    }
    public String getNumber()
    {
        return number;
    }
    public void setNumber(String number)
    {
        this.number = number;
    }
    public LocalDateTime getCreationDate()
    {
        return creationDate;
    }
    public void setCreationDate(LocalDateTime creationDate)
    {
        this.creationDate = creationDate;
    }
    public double getBalance()
    {
        return balance;
    }
    public void setBalance(double balance)
    {
        this.balance = balance;
    }
    public Client getClient()
    {
        return client;
    }
    public void setClient(Client client)
    {
        this.client = client;
    }
    public Set<Transaction> getTransactions() {
        return transactions;
    }
    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }
    public AccountType getAccountType() {
        return accountType;
    }
    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }
}
