package springboot.bookstorecafe.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import springboot.bookstorecafe.models.LoginPerson;

import springboot.bookstorecafe.repositories.LoginPersonRepository;

@Service
public class LoginPersonService {

	@Autowired
	private LoginPersonRepository loginRepo;

	public List<LoginPerson> findAllItems() {

		return loginRepo.findAll();
	}

	public void deleteItem(LoginPerson loginPerson) {
		loginRepo.delete(loginPerson);
	}

	public LoginPerson findById(Long id) {
		return loginRepo.findById(id).orElse(null);
	}

	public void addLoginPerson(LoginPerson loginPerson) {
		loginRepo.save(loginPerson);
	}
}
