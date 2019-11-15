package com.deadshot.labrage_v3.TableMasterCatalogue;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "master_catalogue")
public class MasterCatalogue implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "catalogue_id")
	private int catalogue_id;
	
	@Column(name = "catalogue_name")
	private String catalogue_name;

	protected MasterCatalogue() {}
	
	public MasterCatalogue(int catalogue_id, String catalogue_name) {
		this.catalogue_id = catalogue_id;
		this.catalogue_name = catalogue_name;
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

