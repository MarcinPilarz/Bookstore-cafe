package springboot.bookstorecafe.services;

import java.util.Collections;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.param.CustomerCreateParams;

import springboot.bookstorecafe.models.LoginPerson;
import springboot.bookstorecafe.models.Person;

import springboot.bookstorecafe.repositories.PersonRepository;

@Service
public class PersonService implements MainService<Person> {

	@Autowired
	private PersonRepository personRepo;

	@Value("${stripe.apikey}")
	String stripeKey;

	@Override
	public List<Person> findAllItems() {

		return personRepo.findAll();
	}

	public void registerPeopleToStripe(Person person, LoginPerson loginPerson) {

		try {

			Stripe.apiKey = stripeKey;
			Long personId = person.getIdPerson();
			Customer customer = createStripeCustomer(person, loginPerson, personId);
		} catch (StripeException e) {
			e.printStackTrace();
		}

	}

	private Customer createStripeCustomer(Person person, LoginPerson loginPerson, Long personId)
			throws StripeException {

		Customer customer = Customer.create(

				new CustomerCreateParams.Builder().setName(person.getFirstName()).setEmail(loginPerson.getEmail())
						.setMetadata(Collections.singletonMap("personId", personId.toString())) // Przypisz idPerson
																								// jako metadane
						.build()

		);

		return customer;
	}

	public Person addNewPerson(Person person) {

		return personRepo.save(person);
	}

	@Override
	public void deleteItem(Person person) {
		personRepo.delete(person);

	}

	@Override
	public void updateItem(Person person) {
		personRepo.save(person);

	}

	public Person displayUserInfo(Long id) {
		Person person = personRepo.findById(id)
				.orElseThrow(() -> new RuntimeException("There is no such person: " + id));

		Person getPerson = new Person();
		getPerson.setFirstName(person.getFirstName());
		getPerson.setEvent(person.getEvent());

		return person;
	}

	@Override
	public Person findById(Long id) {
		return personRepo.findById(id).orElse(null);
	}

	@Override
	public void addItem(Person object) {
		// TODO Auto-generated method stub

	}

}
