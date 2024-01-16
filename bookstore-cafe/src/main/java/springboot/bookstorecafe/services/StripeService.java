package springboot.bookstorecafe.services;

import org.springframework.stereotype.Service;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Value;

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

	public Charge chargePayment(String token, double amount, String productName, String firstName, String lastName)
			throws StripeException {
		Map<String, Object> chargeParams = new HashMap<>();
		int amountInCents = (int) (amount * 100);
		if (amountInCents < 1) {
			throw new IllegalArgumentException("Kwota musi być większa niż 0");
		}
		chargeParams.put("amount", amountInCents);
		chargeParams.put("currency", "pln");
		chargeParams.put("source", token);
		chargeParams.put("description",
				"Opłata za zamówienie: " + " " + productName + ", Klient: " + firstName + " " + lastName);
		return Charge.create(chargeParams);
	}
}
