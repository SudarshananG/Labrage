package com.deadshot.labrage_v3.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.deadshot.labrage_v3.TableOtpGenerator.OtpGeneratorRepository;
import com.deadshot.labrage_v3.TableUserDetails.UserDetails;
import com.deadshot.labrage_v3.TableUserDetails.UserDetailsRepository;

@RestController
@RequestMapping("/d")
public class DeleteController {

	@Autowired
	UserDetailsRepository userDetailsRepository;
	
	@Autowired
	OtpGeneratorRepository otpGeneratorRepository;
	
	@RequestMapping(method = RequestMethod.POST, value = "/deleteUser")
	public int deleteUser(@RequestBody String mob) {
		
		// 00 - mobile number not found for deletion
		// 01 - deleted successfully
		// 90 - exception in deletion

		System.out.println("d_deleteUser -> deleting mobile " + mob);
		
		try {	
			for(UserDetails ud: userDetailsRepository.findAll()) {
				if(ud.getMobile_primary().equals(mob) && ud.getStatus() == 1) {
					userDetailsRepository.updateStatus(0, ud.getUser_id());
					System.out.println("return code : 1");
					return 1;
				}
			}
			System.out.println("return code : 0");
			return 0;
		}catch(Exception ex) {
			System.out.println(ex);
			System.out.println("Error: DeleteController -> deleteUser");
			System.out.println("return code : 90");
			return 90;
		}
		
	}
	
}
