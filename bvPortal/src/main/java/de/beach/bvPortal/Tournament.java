package de.beach.bvPortal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Tournament {
	@Id
	@GeneratedValue
	private Long id;
	
	

}
