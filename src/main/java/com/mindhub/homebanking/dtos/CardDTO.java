package com.mindhub.homebanking.dtos;
import com.mindhub.homebanking.models.*;
import java.time.LocalDateTime;

public class CardDTO
{
    // - - - - - - - - - ATRIBUTOS - - - - - - - - - //

    private long ID;
    private String cardHolder;
    private CardType type;
    private CardColor color;
    private String number;
    private int cvv;
    private LocalDateTime thruDate;
    private LocalDateTime fromDate;

    // - - - - - - - - - CONSTRUCTORES - - - - - - - - - //

    public CardDTO() {}

    public CardDTO(Card card)
    {
        this.cardHolder = card.getCardholder();
        this.type = card.getType();
        this.color = card.getColor();
        this.number = card.getNumber();
        this.cvv = card.getCvv();
        this.thruDate = card.getThruDate();
        this.fromDate = card.getFromDate();
    }

    // - - - - - - - - - METODOS - - - - - - - - - //

    public long getID() {
        return ID;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public CardType getType() {
        return type;
    }

    public CardColor getColor() {
        return color;
    }

    public String getNumber() {
        return number;
    }

    public int getCvv() {
        return cvv;
    }

    public LocalDateTime getThruDate() {
        return thruDate;
    }

    public LocalDateTime getFromDate() {
        return fromDate;
    }
}
