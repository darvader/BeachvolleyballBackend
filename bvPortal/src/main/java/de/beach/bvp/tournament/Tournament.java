	package de.beach.bvp.tournament;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Tournament {
	@Id
	@GeneratedValue
	private Long id;
	private Date date;
	private String category;
	private String description;
	private String playMode;
	private String gender;
	private Double entryFee;
	private Double priceMoney;
	private String contact;
	private String name;
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPlayMode() {
		return playMode;
	}

	public void setPlayMode(String playMode) {
		this.playMode = playMode;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Double getEntryFee() {
		return entryFee;
	}

	public void setEntryFee(Double entryFee) {
		this.entryFee = entryFee;
	}

	public Double getPriceMoney() {
		return priceMoney;
	}

	public void setPriceMoney(Double priceMoney) {
		this.priceMoney = priceMoney;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Tournament() {
		
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
