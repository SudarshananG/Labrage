package com.deadshot.labrage_v3.TableUserCatalogue;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_catalogue")
public class UserCatalogue implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	private int id;
	
	@Column(name = "user_id")
	private String user_id;
	
	@Column(name = "catalogue_id")
	private int catalogue_id;

	protected UserCatalogue() {}

	public UserCatalogue(String user_id, int catalogue_id, int id) {
		this.user_id = user_id;
		this.catalogue_id = catalogue_id;
		this.id = id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public int getCatalogue_id() {
		return catalogue_id;
	}

	public void setCatalogue_id(int catalogue_id) {
		this.catalogue_id = catalogue_id;
	}
	
}


