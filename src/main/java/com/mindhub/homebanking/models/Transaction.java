package com.mindhub.homebanking.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Transaction
{
    // - - - - - - - - - ATRIBUTOS - - - - - - - - - //

    @Id //le da un valor único a cada cliente
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native") // Genera valor numerico irrepetible de manera ordenada para el id
    @GenericGenerator(name = "native", strategy = "native") // Se asigna el id generado a la variable id
    private long ID;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn (name = "account_id")
    private Account account;
    private double amount;
    private String description;
    private LocalDateTime date;
    private TransactionType type;

    // - - - - - - - - - CONSTRUCTORES - - - - - - - - - //

    public Transaction() {}

    public Transaction(TransactionType type ,double amount, String description, Account account)
    {
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.date = LocalDateTime.now();
        this.account = account;
    }

    // - - - - - - - - - METODOS - - - - - - - - - //


    public long getID() {
        return ID;
    }
    @JsonIgnore
    public Account getAccount()
    {
        return account;
    }
    public void setAccount(Account account)
    {
        this.account = account;
    }
    public double getAmount()
    {
        return amount;
    }
    public void setAmount(double amount)
    {
        this.amount = amount;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public LocalDateTime getDate() {
        return date;
    }
    public void setDate(LocalDateTime date) {
        this.date = date;
    }
    public TransactionType getType() {
        return type;
    }
    public void setType(TransactionType type) {
        this.type = type;
    }
}
