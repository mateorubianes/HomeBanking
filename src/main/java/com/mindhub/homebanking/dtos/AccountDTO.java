package com.mindhub.homebanking.dtos;
import com.mindhub.homebanking.models.Account;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class AccountDTO
{
    // - - - - - - - - - ATRIBUTOS - - - - - - - - - //

    private long ID;
    private String number;
    private LocalDateTime creationDate;
    private double balance;
    private Set<TransactionDTO> transactions = new HashSet<>();

    // - - - - - - - - - CONSTRUCTORES - - - - - - - - - //

    public AccountDTO() {}
    public AccountDTO(Account account)
    {
        this.ID = account.getID();
        this.number = account.getNumber();
        this.creationDate = account.getCreationDate();
        this.balance = account.getBalance();
        this.transactions = account.getTransactions().stream().map(transaction -> new TransactionDTO(transaction)).collect(Collectors.toSet());
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
    public LocalDateTime getCreationDate()
    {
        return creationDate;
    }
    public double getBalance()
    {
        return balance;
    }
    public Set<TransactionDTO> getTransactions() {
        return transactions;
    }
}
