package com.mindhub.homebanking.models;
import com.mindhub.homebanking.utils.CardUtils;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Card
{
    // - - - - - - - - - ATRIBUTOS - - - - - - - - - //

    @Id //le da un valor único a cada cliente
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native") // Genera valor numerico irrepetible de manera ordenada para el id
    @GenericGenerator(name = "native", strategy = "native") // Se asigna el id generado a la variable id
    private long ID;
    private String cardholder;
    private CardType type;
    private CardColor color;
    private String number;
    private int cvv;
    private LocalDateTime thruDate;
    private LocalDateTime fromDate;
    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn (name = "client_id")
    private Client client;

    // - - - - - - - - - CONSTRUCTORES - - - - - - - - - //

    public Card() {
    }

    public Card( CardType type, CardColor color, String number ,Client client)
    {
        this.cardholder = client.getFirstName() + " " + client.getLastName();
        this.type = type;
        this.color = color;
        this.number = number;
        this.cvv = CardUtils.RandomCVV();
        this.thruDate = LocalDateTime.now().plusYears(5);
        this.fromDate = LocalDateTime.now();
        this.client = client;
    }
    // - - - - - - - - - METODOS - - - - - - - - - //

    public long getID() {
        return ID;
    }

    public String getCardholder() {
        return cardholder;
    }

    public void setCardholder(String cardholder) {
        this.cardholder = cardholder;
    }

    public CardType getType() {
        return type;
    }

    public void setType(CardType type) {
        this.type = type;
    }

    public CardColor getColor() {
        return color;
    }

    public void setColor(CardColor color) {
        this.color = color;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public LocalDateTime getThruDate() {
        return thruDate;
    }

    public void setThruDate(LocalDateTime thruDate) {
        this.thruDate = thruDate;
    }

    public LocalDateTime getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDateTime fromDate) {
        this.fromDate = fromDate;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
