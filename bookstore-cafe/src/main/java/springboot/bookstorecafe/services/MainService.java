package springboot.bookstorecafe.services;

import java.util.List;

public interface MainService<T> {

	List<T> findAllItems();
	void addItem (T object);
	void deleteItem (T obejct);
	void updateItem (T object);
	
	T findById(Long id);
	
}
