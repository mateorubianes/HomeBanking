package com.mindhub.homebanking.utils;
import com.mindhub.homebanking.repositories.AccountRepository;

public final class AccountUtils
{
    public AccountUtils() {}

    public static String RandomVIN(AccountRepository accountRepository)
    {
        boolean searcher = false;
        String VIN;
        do
        {
            VIN = "VIN-"+ ((int) ( (Math.random() * 99999999) ) );
            if(accountRepository.findByNumber(VIN) == null)
                searcher = true;
        }
        while(searcher == false);
        return VIN;
    }
}
