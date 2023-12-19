package springboot.bookstorecafe.services;

import org.springframework.stereotype.Service;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class StripeService {

	 @Value("${stripe.apikey}")
	    private String apiKey;
	 
	 @PostConstruct
	    public void init() {
	        Stripe.apiKey = apiKey;
	    }

	 public Charge chargePayment(String token, double amount) throws StripeException {
	        Map<String, Object> chargeParams = new HashMap<>();
	        int amountInCents = (int)(amount * 100);
	        if (amountInCents < 1) {
	            throw new IllegalArgumentException("Kwota musi być większa niż 0");
	        }
	        chargeParams.put("amount", amountInCents);
	        chargeParams.put("currency", "pln"); // Ustawienie waluty (tutaj USD)
	        chargeParams.put("source", token); // Token płatności otrzymany z frontendu
	        chargeParams.put("description", "Opłata za zamówienie"); // Opis transakcji

	        return Charge.create(chargeParams); // Wywołanie API Stripe do przetworzenia płatności
	    }
}
