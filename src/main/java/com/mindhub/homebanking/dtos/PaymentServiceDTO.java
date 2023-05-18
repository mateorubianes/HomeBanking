package com.mindhub.homebanking.dtos;

public class PaymentServiceDTO
{
    // - - - - - - - - - ATRIBUTOS - - - - - - - - - //

    private long paymentId;
    private double amount;
    private String description;
    private String number;
    private int cvv;

    // - - - - - - - - - CONSTRUCTORES - - - - - - - - - //


    public PaymentServiceDTO() { }

    public PaymentServiceDTO(long paymentId, double amount, String description, String number, int cvv)
    {
        this.paymentId = paymentId;
        this.amount = amount;
        this.description = description;
        this.number = number;
        this.cvv = cvv;
    }

    // - - - - - - - - - METODOS - - - - - - - - - //


    public long getPaymentId() {
        return paymentId;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public String getNumber() {
        return number;
    }

    public int getCvv() {
        return cvv;
    }
}