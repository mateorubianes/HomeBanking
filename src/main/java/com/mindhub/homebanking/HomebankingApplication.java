package com.mindhub.homebanking;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import com.mindhub.homebanking.utils.CardUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.time.LocalDateTime;
import java.util.Arrays;

import static com.mindhub.homebanking.models.CardType.DEBIT;

@SpringBootApplication //Declaramos que es una aplicacion de SpringBoot
public class HomebankingApplication
{
	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args)
	{
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean //Instanciamos los objetos
	public CommandLineRunner InitData(ClientRepository clientRepository,
									  AccountRepository accountRepository,
									  TransactionRepository transactionRepository,
									  LoanRepository loanRepository,
									  ClientLoanRepository clientLoanRepository,
									  CardRepository cardRepository)
	{
		return (args) ->
		{
			Client client1 = new Client("Mateo", "Rubianes","mateorubianes@gmail.com",passwordEncoder.encode("1234"));
			clientRepository.save(client1);
			Client client2 = new Client("Juan Roman", "Riquelme","boca0descensos@gmail.com",passwordEncoder.encode("bocampeon"));
			clientRepository.save(client2);
			Account account1 = new Account( client1, "VIN-00000001", LocalDateTime.now(), 5000, AccountType.CHECKING);
			accountRepository.save(account1);
			Account account2 = new Account( client1,"VIN-00000002", LocalDateTime.now().plusDays(1),7500, AccountType.SAVING);
			accountRepository.save(account2);
			Account account3 = new Account( client2, "VIN-00000003", LocalDateTime.now(), 10000, AccountType.CHECKING);
			accountRepository.save(account3);


			Transaction transaction1 = new Transaction(TransactionType.CREDIT, 2000, "transferencia recibida", account1);
			Transaction transaction2 = new Transaction(TransactionType.DEBIT, -4000, "Compra tienda xx", account1);
			Transaction transaction3 = new Transaction(TransactionType.CREDIT, 1000, "transferencia recibida", account2);
			Transaction transaction4 = new Transaction(TransactionType.DEBIT, -200, "Compra tienda xy", account2);
			Transaction transaction5 = new Transaction(TransactionType.CREDIT, 8000, "transferencia recibida", account3);
			Transaction transaction6 = new Transaction(TransactionType.DEBIT, -2000, "Compra tienda xz", account3);
			transactionRepository.save(transaction1);
			transactionRepository.save(transaction2);
			transactionRepository.save(transaction3);
			transactionRepository.save(transaction4);
			transactionRepository.save(transaction5);
			transactionRepository.save(transaction6);

			Loan loan1 = new Loan("Hipotecario",500000, Arrays.asList(12,24,36,48,60), 20);
			Loan loan2 = new Loan("Personal",100000, Arrays.asList(6,12,24),15);
			Loan loan3 = new Loan("Automotriz",300000, Arrays.asList(6,12,24,36),20);
			loanRepository.save(loan1);
			loanRepository.save(loan2);
			loanRepository.save(loan3);

			ClientLoan clientLoan1 = new ClientLoan(400000,60,client1,loan1);
			ClientLoan clientLoan2 = new ClientLoan(50000,12,client1,loan2);
			ClientLoan clientLoan3 = new ClientLoan(100000,24,client2,loan2);
			ClientLoan clientLoan4 = new ClientLoan(200000,36,client2,loan3);
			clientLoanRepository.save(clientLoan1);
			clientLoanRepository.save(clientLoan2);
			clientLoanRepository.save(clientLoan3);
			clientLoanRepository.save(clientLoan4);

			Card card1 = new Card( CardType.DEBIT,CardColor.GOLD, CardUtils.RandomCardNumber(cardRepository),client1);
			Card card2 = new Card( CardType.CREDIT,CardColor.TITANIUM, CardUtils.RandomCardNumber(cardRepository),client1);
			Card card3 = new Card( CardType.DEBIT,CardColor.GOLD, CardUtils.RandomCardNumber(cardRepository),client2);
			Card card4 = new Card( CardType.CREDIT,CardColor.SILVER, CardUtils.RandomCardNumber(cardRepository), client2);
			cardRepository.save(card1);
			cardRepository.save(card2);
			cardRepository.save(card3);
			cardRepository.save(card4);
		};
	}
}
