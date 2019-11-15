package com.deadshot.labrage_v3.TableUserCatalogue;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserCatalogueRepository extends CrudRepository<UserCatalogue, Long>{
	List<UserCatalogue> findAll();
	
	@Query("select max(p.id) from UserCatalogue p")
	int getMaxIdCatalogue();
}
