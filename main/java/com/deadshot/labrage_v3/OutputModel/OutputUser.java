package com.deadshot.labrage_v3.OutputModel;

import java.util.List;

import com.deadshot.labrage_v3.TableUserDetails.UserDetails;

public class OutputUser {
	
	private UserDetails userDetails;
	private List<UserCatalogue_string> userCatalogueArray_string;
	
	public OutputUser() {}
	
	public OutputUser(UserDetails userDetails, List<UserCatalogue_string> userCatalogueArray_string) {
		this.userDetails = userDetails;
		this.userCatalogueArray_string = userCatalogueArray_string;
	}
	public UserDetails getUserDetails() {
		return userDetails;
	}
	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}
	
	public List<UserCatalogue_string> getUserCatalogueArray() {
		return userCatalogueArray_string;
	}
	
	public void setUserCatalogueArray(List<UserCatalogue_string> userCatalogueArray_string) {
		this.userCatalogueArray_string = userCatalogueArray_string;
	}

}
