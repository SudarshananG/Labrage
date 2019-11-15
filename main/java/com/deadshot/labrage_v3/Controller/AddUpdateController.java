package com.deadshot.labrage_v3.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.deadshot.labrage_v3.InputModel.OtpCheck;
import com.deadshot.labrage_v3.OutputModel.UserCatalogue_string;
import com.deadshot.labrage_v3.TableCredential.Credential;
import com.deadshot.labrage_v3.TableCredential.CredentialRepository;
import com.deadshot.labrage_v3.TableMasterCatalogue.MasterCatalogueRepository;
import com.deadshot.labrage_v3.TableUserCatalogue.UserCatalogue;
import com.deadshot.labrage_v3.TableUserCatalogue.UserCatalogueRepository;
import com.deadshot.labrage_v3.TableUserDetails.UserDetails;
import com.deadshot.labrage_v3.TableUserDetails.UserDetailsRepository;

@RestController
@RequestMapping("/a")
public class AddUpdateController {
	
	private static String uniqueID1 = "aaa";
	private static int uniqueID2 = 000;
	
	@Autowired
	MasterCatalogueRepository masterCatalogueRepository;
	
	@Autowired
	UserDetailsRepository userDetailsRepository;
	
	@Autowired
	UserCatalogueRepository userCatalogueRepository;
	
	@Autowired
	CredentialRepository credentialRepository;
	
	// service to add new user
	@RequestMapping(method = RequestMethod.POST, value = "/newUser")
	public int newUser(@RequestBody OtpCheck otp) {
		
		String userID;
		
		try {
			if(otp.getCommand().equals("add")) {
				uniqueID2++;
				userID = uniqueID1 + String.format("%03d", uniqueID2);
			}else if(otp.getCommand().equals("update")) {
				userID = otp.getOutputUser().getUserDetails().getUser_id();
				
				for(UserCatalogue uc: userCatalogueRepository.findAll()) {
					if(uc.getUser_id().equals(otp.getOutputUser().getUserDetails().getUser_id())) {
						userCatalogueRepository.delete(uc);
					}
				}
				
			}else {
				System.out.println("Error: AddUpdateController -> newUser");
				System.out.println("return code : 90");
				return 90;
			}
			
			System.out.println("a_newUser -> adding new user mob " + userID + " " + otp.getOutputUser().getUserDetails().getMobile_primary());
			
			credentialRepository.save(new Credential(userID, otp.getPassword()));
			
			userDetailsRepository.save(new UserDetails(userID,
														otp.getOutputUser().getUserDetails().getShop_name(),
														otp.getOutputUser().getUserDetails().getOwner_name(),
														otp.getOutputUser().getUserDetails().getMobile_primary(),
														otp.getOutputUser().getUserDetails().getMobile_secondary(),
														otp.getOutputUser().getUserDetails().getMail_id(),
														otp.getOutputUser().getUserDetails().getAddress_line(),
														otp.getOutputUser().getUserDetails().getArea(),
														otp.getOutputUser().getUserDetails().getCity(),
														otp.getOutputUser().getUserDetails().getPincode(),
														otp.getOutputUser().getUserDetails().getGooglemap_link(),
														otp.getOutputUser().getUserDetails().getCovering_area(),
									   					1));
			
			for(UserCatalogue_string catId: otp.getOutputUser().getUserCatalogueArray()) {
				userCatalogueRepository.save(new UserCatalogue(userID, catId.getCatalogue_id(), (userCatalogueRepository.getMaxIdCatalogue() + 1)));
			}
			
			System.out.println("return code : 1");
			return 1;
			
		}catch(Exception ex) {
			System.out.println(ex);
			System.out.println("Error: AddUpdateController -> newUser");
			System.out.println("return code : 90");
			return 90;
		}	
	}
	
}
