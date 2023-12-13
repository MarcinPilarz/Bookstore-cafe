package springboot.bookstorecafe.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.annotations.SerializedName;
import com.stripe.param.PaymentIntentCreateParams;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class PaymentController {

	
	
//
//	  static class CreatePayment {
//	    @SerializedName("items")
//	    CreatePaymentItem[] items;
//
//	    public CreatePaymentItem[] getItems() {
//	      return items;
//	    }
//	  }
//
//	  static class CreatePaymentResponse {
//	    private String clientSecret;
//	    public CreatePaymentResponse(String clientSecret) {
//	      this.clientSecret = clientSecret;
//	    }
//	  }
//	
//	@PostMapping("/create-payment-intent")
//	public void createPaymentIntent(CreatePayment createPayment) {
//		  
//			      
//
//
//			      PaymentIntentCreateParams params =
//			        PaymentIntentCreateParams.builder()
//			          .setAmount(new Long(calculateOrderAmount(postBody.getItems())))
//			          .setCurrency("pln")
//			      // In the latest version of the API, specifying the `automatic_payment_methods` parameter is optional because Stripe enables its functionality by default.
//			          .setAutomaticPaymentMethods(
//			            PaymentIntentCreateParams.AutomaticPaymentMethods
//			              .builder()
//			              .setEnabled(true)
//			              .build()
//			          )
//			          .build();
//
//			      // Create a PaymentIntent with the order amount and currency
//			      PaymentIntent paymentIntent = PaymentIntent.create(params);
//
//			      CreatePaymentResponse paymentResponse = new CreatePaymentResponse(paymentIntent.getClientSecret());
//			      return gson.toJson(paymentResponse);
//			    });
//	}
}
