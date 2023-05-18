package com.mindhub.homebanking.models;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity //Hace que el objeto cliente se guarde en una base de datos generando una tabla
public class Client
{
    // - - - - - - - - - ATRIBUTOS - - - - - - - - - //

    @Id //le da un valor único a cada cliente
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native") // Genera valor numerico irrepetible de manera ordenada para el id
    @GenericGenerator(name = "native", strategy = "native") // Se asigna el id generado a la variable id
    private long ID;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    @OneToMany (mappedBy = "client", fetch = FetchType.EAGER) //mappedby = "client" al atributo que mapeamos
    private Set<Account> accounts = new HashSet<>();
    @OneToMany (mappedBy = "client", fetch = FetchType.EAGER)
    private Set<ClientLoan> clientLoans = new HashSet<>();
    @OneToMany (mappedBy = "client", fetch = FetchType.EAGER)
    private Set<Card> cards = new HashSet<>();

    // - - - - - - - - - CONSTRUCTORES - - - - - - - - - //

    public Client() { }

    public Client(String firstName, String lastName, String email, String password)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    // - - - - - - - - - METODOS - - - - - - - - - //

    public Set<Card> getCards() {
        return cards;
    }
    public void setCards(Set<Card> cards) {
        this.cards = cards;
    }
    @JsonIgnore
    public Set<Loan> getLoans()
    {
        return clientLoans.stream().map(clientLoan -> clientLoan.getLoan()).collect(Collectors.toSet());
    }
    public Set<ClientLoan> getClientLoans() {
        return clientLoans;
    }
    public void setClientLoans(Set<ClientLoan> clientLoans) {
        this.clientLoans = clientLoans;
    }
    public long getID()
    {
        return ID;
    }
    public String getFirstName()
    {
        return firstName;
    }
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }
    public String getLastName()
    {
        return lastName;
    }
    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }
    public String getEmail()
    {
        return email;
    }
    public void setEmail(String email)
    {
        this.email = email;
    }
    public Set<Account> getAccounts()
    {
        return accounts;
    }
    public void setAccounts(Set<Account> accounts)
    {
        this.accounts = accounts;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
