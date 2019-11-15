package com.deadshot.labrage_v3.OutputModel;

public class UserCatalogue_string {

	private String user_id;
	private int catalogue_id;
	private String catalogue_name;

	protected UserCatalogue_string() {}

	public UserCatalogue_string(String user_id, int catalogue_id, String catalogue_name) {
		this.user_id = user_id;
		this.catalogue_id= catalogue_id;
		this.catalogue_name = catalogue_name;
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
	
	public String getCatalogue_name() {
		return catalogue_name;
	}

	public void setCatalogue_name(String catalogue_name) {
		this.catalogue_name = catalogue_name;
	}

}
