package com.mindhub.homebanking.models;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Loan
{
    // - - - - - - - - - ATRIBUTOS - - - - - - - - - //

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long ID;
    private String name;
    private double maxAmount;
    private double fees;
    @ElementCollection
    @Column(name = "payments")
    private List<Integer> payments = new ArrayList<>();
    @OneToMany (mappedBy = "client", fetch = FetchType.EAGER)
    private Set<ClientLoan> clientLoans = new HashSet<>();

    // - - - - - - - - - CONSTRUCTORES - - - - - - - - - //

    public Loan() {}

    public Loan(String name, double maxAmount, List<Integer> payments, double fees)
    {
        this.name = name;
        this.maxAmount = maxAmount;
        this.payments = payments;
        this.fees = fees;
    }

    // - - - - - - - - - METODOS - - - - - - - - - //

    @JsonIgnore
    public Set<Client> getClients()
    {
        return clientLoans.stream().map(clientLoan -> clientLoan.getClient()).collect(Collectors.toSet());
    }
    public long getID() {
        return ID;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getMaxAmount() {
        return maxAmount;
    }
    public void setMaxAmount(double maxAmount) {
        this.maxAmount = maxAmount;
    }
    public List<Integer> getPayments() {
        return payments;
    }
    public void setPayments(List<Integer> payments) {
        this.payments = payments;
    }
    public Set<ClientLoan> getClientLoans() {
        return clientLoans;
    }
    public void setClientLoans(Set<ClientLoan> clientLoans) {
        this.clientLoans = clientLoans;
    }

    public double getFees() {
        return fees;
    }

    public void setFees(double fees) {
        this.fees = fees;
    }
}
