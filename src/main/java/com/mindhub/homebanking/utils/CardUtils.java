package com.mindhub.homebanking.utils;
import com.mindhub.homebanking.repositories.CardRepository;

public final class CardUtils
{
    private CardUtils() {}

    public static int RandomCVV()
    {
        return  (int) ( (Math.random() * 999) ) ;
    }
    public static String RandomCardNumber(CardRepository cardRepository)
    {
        String cardNumber;
        boolean searcher = false;
        do
        {
            cardNumber = ( (int) (Math.random()*9999) ) + "-" +
                         ( (int) (Math.random()*9999) ) + "-" +
                         ( (int) (Math.random()*9999) ) + "-" +
                         ( (int) (Math.random()*9999) );
            if( cardRepository.findByNumber(cardNumber) == null )
                searcher = true;
        }
        while(searcher == false);
        return cardNumber;
    }

    
}
