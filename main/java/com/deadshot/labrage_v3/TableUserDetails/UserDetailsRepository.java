package com.deadshot.labrage_v3.TableUserDetails;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface UserDetailsRepository extends CrudRepository<UserDetails, String> {
	List<UserDetails> findAll();
	
	@Query("select count(*) from UserDetails p where p.mobile_primary = ?1")
	int countMobile_Primary(String mobile_primary);
	
	@Query("select count(*) from UserDetails p where p.mobile_secondary = ?1")
	int countMobile_Secondary(String mobile_secondary);

	@Transactional
	@Modifying
	@Query("update UserDetails ud set ud.status = :status where ud.user_id = :user_id")
	void updateStatus(int status, String user_id);
	
}
