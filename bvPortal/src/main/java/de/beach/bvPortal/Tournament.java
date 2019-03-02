package de.beach.bvPortal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Tournament {
	@Id
	@GeneratedValue
	private Long id;
	
	private String name;
	private String date;
	private String category;
	private String description;
	private String playMode;
	private String gender;
	private String entryFee;
	private String priceMoney;
	private String contact;
	private String string;

	public Tournament() {
		
	}
}
