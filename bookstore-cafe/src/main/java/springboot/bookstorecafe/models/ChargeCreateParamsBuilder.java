package springboot.bookstorecafe.models;

import com.stripe.param.ChargeCreateParams;

public class ChargeCreateParamsBuilder {

	 private Long amount;
	    private String currency;
	    private String source;
	    private String description;

	    public ChargeCreateParamsBuilder setAmount(Long amount) {
	        this.amount = amount;
	        return this;
	    }

	    public ChargeCreateParamsBuilder setCurrency(String currency) {
	        this.currency = currency;
	        return this;
	    }

	    public ChargeCreateParamsBuilder setSource(String source) {
	        this.source = source;
	        return this;
	    }

	    public ChargeCreateParamsBuilder setDescription(String description) {
	        this.description = description;
	        return this;
	    }

	    public ChargeCreateParams build() {
	        ChargeCreateParams params = new ChargeCreateParams.Builder()
	                .setAmount(amount)
	                .setCurrency(currency)
	                .setSource(source)
	                .setDescription(description)
	                .build();
	        return params;
	    }
}
