package springboot.bookstorecafe.models;

import jakarta.persistence.*;

@Entity
@Table(name="role")
public class Role {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_role")
	private Long id_role;

	public Long getId_role() {
		return id_role;
	}

	public void setId_role(Long id_role) {
		this.id_role = id_role;
	}
	
	
	
	
	//tu będzie role_type sprawdzić czym jest enum w springu 
	
}
