package com.deadshot.labrage_v3.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.deadshot.labrage_v3.OutputModel.OutputUser;
import com.deadshot.labrage_v3.OutputModel.OutputUserArray;
import com.deadshot.labrage_v3.OutputModel.UserCatalogue_string;
import com.deadshot.labrage_v3.TableMasterCatalogue.MasterCatalogueArray;
import com.deadshot.labrage_v3.TableMasterCatalogue.MasterCatalogueRepository;
import com.deadshot.labrage_v3.TableUserCatalogue.UserCatalogue;
import com.deadshot.labrage_v3.TableUserCatalogue.UserCatalogueRepository;
import com.deadshot.labrage_v3.TableUserDetails.UserDetails;
import com.deadshot.labrage_v3.TableUserDetails.UserDetailsRepository;

@RestController
@RequestMapping("/o")
public class CommonController {
	
	@Autowired
	MasterCatalogueRepository masterCatalogueRepository;
	
	@Autowired
	UserDetailsRepository userDetailsRepository;
	
	@Autowired
	UserCatalogueRepository userCatalogueRepository;
	
	@Autowired
	RestTemplate restTemplate;
	
	// service to get all catalogue items
	@RequestMapping(method = RequestMethod.GET, value="/allCatalogue")
	public ResponseEntity<?> allCatalogue() {
		
		// json - mobile number not found in user_details table
		// 90 - database issue
		
		System.out.println("o_allCatalogue -> fetching all catalogue items");
		
		try {	
			MasterCatalogueArray list = new MasterCatalogueArray();
			list.setMasterCatalogueArray(masterCatalogueRepository.findAll());
			System.out.println("return code : json");
			return ResponseEntity.ok(list);	
		}catch(Exception ex) {
			System.out.println(ex);
			System.out.println("Error: CommonController -> allCatalogue");
			System.out.println("return code : 90");
			return ResponseEntity.ok(90);
		}

	}
	
	// service to find user using primary_mobile
	@RequestMapping(method = RequestMethod.POST, value = "findMobile")
	public ResponseEntity<?> findMobile(@RequestBody String mobile_number) {
		
		// json - successful
		// 00 - mobile number not found in user_details table
		// 90 - database issue
		
		System.out.println("o_findMobile -> checking mobile " + mobile_number);
		
		try {
			for(UserDetails ud: userDetailsRepository.findAll()) {
				if(ud.getMobile_primary().equals(mobile_number)) {
					System.out.println("return code : json");
				    return detailsUser(ud.getUser_id());
					//return restTemplate.postForEntity("http://localhost:8080/o/user", ud.getUser_id(), ResponseEntity.class);
				}
			}
			System.out.println("return code : 0");
			return ResponseEntity.ok(0);
		}catch(Exception ex) {
			System.out.println(ex);
			System.out.println("Error: CommonController -> findMobile");
			System.out.println("return code : 90");
			return ResponseEntity.ok(90);
		}
				
	}
	
	// service to fetch details of the user
	@RequestMapping(method = RequestMethod.POST, value = "/user")
	public ResponseEntity<?> detailsUser(@RequestBody String user_id) {
		
		// json - successful
		// 00 - user not found
		// 90 - database issue
		
		System.out.println("o_detailsUser -> fetching user details " + user_id);
		
		try {	
			for(UserDetails userDetails: userDetailsRepository.findAll()) {
				if(userDetails.getUser_id().equals(user_id)) {
					OutputUserArray outputArray = new OutputUserArray();
					List<UserCatalogue_string> userCatalogueArray_string = new ArrayList<UserCatalogue_string>();
					for(UserCatalogue userCatalogue: userCatalogueRepository.findAll()) {
						if(userCatalogue.getUser_id().equals(userDetails.getUser_id())) {
							userCatalogueArray_string.add(new UserCatalogue_string(userCatalogue.getUser_id(), userCatalogue.getCatalogue_id(), masterCatalogueRepository.findByCatalogue_Id(userCatalogue.getCatalogue_id())));
						}
					}
					outputArray.setOutputUser(new OutputUser(userDetails, userCatalogueArray_string));
					System.out.println("return code : json");
					return ResponseEntity.ok(outputArray);
				}
			}
			System.out.println("return code : 0");
			return ResponseEntity.ok(0);
			
		}catch(Exception ex){
			System.out.println(ex);
			System.out.println("Error: CommonController -> detailsUser");
			System.out.println("return code : 90");
			return ResponseEntity.ok(90);
		}
		
	}
	
//	// service to fetch details of the user
//	@RequestMapping(method = RequestMethod.POST, value = "/user2")
//	public ResponseEntity<?> detailsUser2(@RequestBody String user_id) {
//		
//		// json - successful
//		// 00 - user not found
//		// 90 - database issue
//		
//		System.out.println("o_detailsUser2 -> fetching user details " + user_id);
//		
//		try {	
//			for(UserDetails userDetails: userDetailsRepository.findAll()) {
//				if(userDetails.getUser_id().equals(user_id)) {
//					OutputUserArray outputArray = new OutputUserArray();
//					List<UserCatalogue_string> userCatalogueArray_string = new ArrayList<UserCatalogue_string>();
//					for(UserCatalogue userCatalogue: userCatalogueRepository.findAll()) {
//						if(userCatalogue.getUser_id().equals(userDetails.getUser_id())) {
//							userCatalogueArray_string.add(new UserCatalogue_string(userCatalogue.getUser_id(), userCatalogue.getCatalogue_id(), String.valueOf(userCatalogue.getCatalogue_id())));
//						}
//					}
//					outputArray.setOutputUser(new OutputUser(userDetails, userCatalogueArray_string));
//					System.out.println("return code : json");
//					return ResponseEntity.ok(outputArray);
//				}
//			}
//			System.out.println("return code : 0");
//			return ResponseEntity.ok(0);
//			
//		}catch(Exception ex){
//			System.out.println(ex);
//			System.out.println("Error: CommonController -> detailsUser2");
//			System.out.println("return code : 90");
//			return ResponseEntity.ok(90);
//		}
//		
//	}

}