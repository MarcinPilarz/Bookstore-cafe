package springboot.bookstorecafe.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import springboot.bookstorecafe.repositories.LoginPersonRepository;

@Service
public class LoginPersonService {

	@Autowired
	private LoginPersonRepository loginRepo;
	
//	public void registerPerson() {
//		
//	}
}
