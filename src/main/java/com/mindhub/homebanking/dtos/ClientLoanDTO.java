package com.mindhub.homebanking.dtos;
import com.mindhub.homebanking.models.ClientLoan;

public class ClientLoanDTO
{
    // - - - - - - - - - ATRIBUTOS - - - - - - - - - //

    private long ID;
    private double amount;
    private int payments;
    private String name;
    private long loanId;

    // - - - - - - - - - CONSTRUCTORES - - - - - - - - - //

    public ClientLoanDTO() {}

    public ClientLoanDTO(ClientLoan clientLoan)
    {
        this.ID = clientLoan.getID();
        this.amount = clientLoan.getAmount();
        this.payments = clientLoan.getPayments();
        this.name = clientLoan.getLoan().getName();
        this.loanId = clientLoan.getLoan().getID();
    }
    // - - - - - - - - - METODOS - - - - - - - - - //

    public long getID() {
        return ID;
    }

    public double getAmount() {
        return amount;
    }

    public int getPayments() {
        return payments;
    }

    public String getName() {
        return name;
    }

    public long getLoanId() {
        return loanId;
    }
}
