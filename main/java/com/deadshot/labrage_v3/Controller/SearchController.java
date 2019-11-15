package com.deadshot.labrage_v3.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.deadshot.labrage_v3.OutputModel.OutputSearch;
import com.deadshot.labrage_v3.OutputModel.OutputSearchArray;
import com.deadshot.labrage_v3.TableMasterCatalogue.MasterCatalogueRepository;
import com.deadshot.labrage_v3.TableUserCatalogue.UserCatalogue;
import com.deadshot.labrage_v3.TableUserCatalogue.UserCatalogueRepository;
import com.deadshot.labrage_v3.TableUserDetails.UserDetails;
import com.deadshot.labrage_v3.TableUserDetails.UserDetailsRepository;

@RestController
@RequestMapping("/s")
public class SearchController {
	
	@Autowired
	MasterCatalogueRepository masterCatalogueRepository;
	
	@Autowired
	UserDetailsRepository userDetailsRepository;
	
	@Autowired
	UserCatalogueRepository userCatalogueRepository;
	
	@RequestMapping("/search")
	public ResponseEntity<?> search(@RequestParam("pincode") String pincode,
									@RequestParam("category") int category) {
		
		// json - successful fetch
		// 90 - database issue
		
		System.out.println("s_search -> pincode,category " + pincode + "," + category);
		
		try {
			OutputSearchArray outputSearchArray = new OutputSearchArray();
			List<OutputSearch> outputSearchList = new ArrayList<OutputSearch>();
			for(UserCatalogue userCatalogue: userCatalogueRepository.findAll()) {
				if(userCatalogue.getCatalogue_id() == category) {
					for(UserDetails userDetails: userDetailsRepository.findAll()) {
						if(userCatalogue.getUser_id().equals(userDetails.getUser_id()) && userDetails.getPincode().equals(pincode)
								&& userDetails.getStatus() == 1) {
							outputSearchList.add(new OutputSearch(userDetails.getUser_id(), userDetails.getShop_name(), userDetails.getMobile_primary()));
						}
					}
				}
			}
			outputSearchArray.setOutputSearchArray(outputSearchList);
			System.out.println("return code : json");
			return ResponseEntity.ok(outputSearchArray);
		}catch(Exception ex) {
			System.out.println(ex);
			System.out.println("Error: SearchController -> search");
			System.out.println("return code : 90");
			return ResponseEntity.ok(90);
		}
		
	}

}
