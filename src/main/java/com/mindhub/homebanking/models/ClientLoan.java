package com.mindhub.homebanking.models;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;

@Entity
public class ClientLoan
{
    // - - - - - - - - - ATRIBUTOS - - - - - - - - - //

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long ID;
    private double amount;
    private int payments;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="client_id")
    private Client client;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="loan_id")
    private Loan loan;

    // - - - - - - - - - CONSTRUCTORES - - - - - - - - - //

    public ClientLoan() {}

    public ClientLoan(double amount, int payments, Client client, Loan loan)
    {
        this.amount = amount;
        this.payments = payments;
        this.client = client;
        this.loan = loan;
    }

    // - - - - - - - - - METODOS - - - - - - - - - //


    public long getID() {
        return ID;
    }
    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    public int getPayments() {
        return payments;
    }
    public void setPayments(int payments) {
        this.payments = payments;
    }
    public Client getClient() {
        return client;
    }
    public void setClient(Client client) {
        this.client = client;
    }
    @JsonIgnore
    public Loan getLoan() {
        return loan;
    }
    public void setLoan(Loan loan) {
        this.loan = loan;
    }
}
