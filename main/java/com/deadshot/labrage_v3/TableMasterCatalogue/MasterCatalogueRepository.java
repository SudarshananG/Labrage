package com.deadshot.labrage_v3.TableMasterCatalogue;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface MasterCatalogueRepository extends CrudRepository<MasterCatalogue, Long>{
	List<MasterCatalogue> findAll();
	
	@Query("select p.catalogue_name from MasterCatalogue p where p.catalogue_id = ?1")
	String findByCatalogue_Id(int catalogue_id);
	
}
