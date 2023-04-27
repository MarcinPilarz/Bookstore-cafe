package springboot.bookstorecafe.models;

import jakarta.persistence.*;

@Entity
@Table(name="role")
public class Role {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_role")
	private Long id_role;
	
	
}
