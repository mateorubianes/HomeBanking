package com.mindhub.homebanking.dtos;
import com.mindhub.homebanking.models.Loan;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class LoanDTO
{
    // - - - - - - - - - ATRIBUTOS - - - - - - - - - //

    private long ID;
    private String name;
    private double maxAmount;
    private List<Integer> payments = new ArrayList<>();
    private Set<ClientLoanDTO> clientLoans = new HashSet<>();
    private double fees;

    // - - - - - - - - - CONSTRUCTORES - - - - - - - - - //

    public LoanDTO() {}

    public LoanDTO( Loan loan )
    {
        this.ID = loan.getID();
        this.name = loan.getName();
        this.maxAmount = loan.getMaxAmount();
        this.payments = loan.getPayments();
        this.clientLoans = loan.getClientLoans().stream().map(clientLoan -> new ClientLoanDTO(clientLoan)).collect(Collectors.toSet());
        this.fees = loan.getFees();
    }

    // - - - - - - - - - METODOS - - - - - - - - - //

    public long getID() {return ID;}
    public String getName() {
        return name;
    }
    public double getMaxAmount() {
        return maxAmount;
    }
    public List<Integer> getPayments() {
        return payments;
    }
    public Set<ClientLoanDTO> getClientLoans() {
        return clientLoans;
    }
    public double getFees() {
        return fees;
    }
}
