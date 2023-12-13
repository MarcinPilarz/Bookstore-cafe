package springboot.bookstorecafe.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin(origins = "http://localhost:3000")
public class CheckOutController {

//	@Value("${STRIPE_PUBLIC_KEY}")
//    private String stripePublicKey;

//    @RequestMapping("/checkout")
//    public String checkout(Model model) {
//        model.addAttribute("amount", 50 * 100); // in cents
//        model.addAttribute("stripePublicKey", stripePublicKey);
//        model.addAttribute("currency", ChargeRequest.Currency.EUR);
//        return "checkout";
//    }
}
