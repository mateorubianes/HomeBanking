package com.mindhub.homebanking.dtos;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import java.time.LocalDateTime;

public class TransactionDTO
{
    // - - - - - - - - - ATRIBUTOS - - - - - - - - - //

    private long ID;
    private double amount;
    private String description;
    private LocalDateTime date;
    private TransactionType type;

    // - - - - - - - - - CONSTRUCTORES - - - - - - - - - //

    public TransactionDTO() {}
    public TransactionDTO(Transaction transaction)
    {
        this.ID = transaction.getID();
        this.amount = transaction.getAmount();
        this.description = transaction.getDescription();
        this.date = transaction.getDate();
        this.type = transaction.getType();
    }

    // - - - - - - - - - METODOS - - - - - - - - - //


    public long getID() {
        return ID;
    }
    public double getAmount() {
        return amount;
    }
    public String getDescription() {
        return description;
    }
    public LocalDateTime getDate() {
        return date;
    }
    public TransactionType getType() {
        return type;
    }

}
