//populates all catalogue items to search dropdown during startup
$.getJSON(url_o_allCatalogue, function(data) {

    var dropdown = document.getElementById("category_dropdown");
    var hold = document.getElementById("category_div");

    for(var i = 0; i < data.masterCatalogueArray.length; i++){

        //search page - category dropdown
        var option = document.createElement("OPTION");
        var txt = document.createTextNode(data.masterCatalogueArray[i].catalogue_name);
        option.appendChild(txt);
        option.setAttribute("value",data.masterCatalogueArray[i].catalogue_id);
        dropdown.insertBefore(option,dropdown.lastChild);

        //add page - list of category
        var checkbox = document.createElement('input');
        checkbox.type = "checkbox";
        checkbox.name = "checkbox_category";
        checkbox.value = data.masterCatalogueArray[i].catalogue_id;
        checkbox.id = "cbId_"  + data.masterCatalogueArray[i].catalogue_id;

        var textLabel = document.createTextNode("  " + data.masterCatalogueArray[i].catalogue_name);

        var createP = document.createElement('p');
        createP.appendChild(checkbox);
        createP.appendChild(textLabel); 

        hold.appendChild(createP);

    }
});

// search using pincode & category in search page
function search_find(){
   
    // document.getElementById('searchtable').style.display = "block";

    // checks for null pincode or category
	if(document.getElementById("txtPincode").value == "" || document.getElementById("category_dropdown").value == "default"){

		if(document.getElementById("txtPincode").value == ""){
            document.getElementById("txtPincode").style.borderColor = "red";
		}else{
			document.getElementById("txtPincode").style.borderColor = "#999";
		}

		if(document.getElementById("category_dropdown").value == "default"){
            document.getElementById("category_dropdown").style.borderColor = "red";
		}else{
			document.getElementById("category_dropdown").style.borderColor = "#999";
		}

		return null;
	}
	
	// resetting the alert required fields
	document.getElementById("txtPincode").style.borderColor = "#999";
	document.getElementById("category_dropdown").style.borderColor = "#999";

    var s_pincode = document.getElementById("txtPincode").value;
    var s_category = document.getElementById("category_dropdown").value;

    var searchUrl =  url_s_search + "?pincode=" + s_pincode +"&category=" + s_category;

	$.getJSON(searchUrl, function(data) {

		if(data.outputSearchArray.length == 0){

			var select = document.getElementById("results_dropdown");
			var length = select.options.length;

			for (i = 0; i < length; i++) {
				select.remove(select.options[i]);
			}

			var x = document.getElementById("results_dropdown");
			var option = document.createElement("option");
			option.text =  "No results found!";
			x.add(option);

		}else{

			var select = document.getElementById("results_dropdown");
			var length = select.options.length;
			for (i = 0; i < length; i++) {
				select.remove(select.options[i]);
			}

			for(var i = 0; i < data.outputSearchArray.length; i++){
		
				var x = document.getElementById("results_dropdown");
				var option = document.createElement("option");
				option.text = data.outputSearchArray[i].shop_name;
				option.id = data.outputSearchArray[i].user_id;
				option.addEventListener("click", function(){ 
					openForm(this.id); 
				});
				x.add(option);
				document.getElementById("results_div").appendChild(x);

			}
			
		}

    });

}

// reset in search page
function search_reset(){
	document.getElementById("txtPincode").value = "";
	document.getElementById("category_dropdown").value = "default";
	document.getElementById("txtPincode").style.borderColor = "#999";
	document.getElementById("category_dropdown").style.borderColor = "#999";
	document.getElementById('searchtable').style.display = "none";

	var select = document.getElementById("results_dropdown");
	var length = select.options.length;
	var length = document.getElementById("results_dropdown").options.length;
	for (i = 0; i < length; i++) {
		select.remove(select.options[i]);
	}

}

// get details about user using button_id
function openForm(clicked_id) {
	document.getElementById('searchtable').style.display = "block";
	
	$.ajax({
		url: url_o_user,
		type: "POST",
		contentType:"text/plain",
		dataType: "json",
		data: clicked_id,
		success: function(data){
			document.getElementById("s_shopname").innerHTML = data.outputUser.userDetails.shop_name;
			document.getElementById("s_ownername").innerHTML = data.outputUser.userDetails.owner_name;
			document.getElementById("s_contact1").innerHTML = data.outputUser.userDetails.mobile_primary;
			document.getElementById("s_contact2").innerHTML = data.outputUser.userDetails.mobile_secondary;
			document.getElementById("s_mail").innerHTML = data.outputUser.userDetails.mail_id;
			document.getElementById("s_address").innerHTML = data.outputUser.userDetails.address_line;
			document.getElementById("s_areacity").innerHTML = data.outputUser.userDetails.area + ", " + data.outputUser.userDetails.city;
			document.getElementById("s_pincode").innerHTML = data.outputUser.userDetails.pincode;
			document.getElementById("s_location").innerHTML = data.outputUser.userDetails.googlemap_link;
			document.getElementById("s_coveringareas").innerHTML = data.outputUser.userDetails.covering_area;

			document.getElementById("s_profession").innerHTML = "";
			for(var i = 0; i < data.outputUser.userCatalogueArray.length; i++){
				document.getElementById("s_profession").innerHTML += data.outputUser.userCatalogueArray[i].catalogue_name;
				if(i != data.outputUser.userCatalogueArray.length - 1){
					document.getElementById("s_profession").innerHTML += ", ";
				}
				
			}
		}

	});

}

// delete page1 - reset
function delete_reset(){
	tabcontent = document.getElementsByClassName("links");
		for (i = 0; i < tabcontent.length; i++) {
		tabcontent[i].style.display = "none";
	}
	document.getElementById('a-delete-1').style.display = "block";

	document.getElementById("deleteContact1").value = "";
	document.getElementById("deleteContact1").style.borderColor = "#999";
	document.getElementById("deleteContact1").style.color = "#999";
}

// delete page1 - mobile number verification
function delete_find(){

	if(document.getElementById("deleteContact1").value.length != 10 || isNaN(document.getElementById("deleteContact1").value) == true){

		if(document.getElementById("deleteContact1").length != 10){
            document.getElementById("deleteContact1").style.borderColor = "red";
		}else{
			document.getElementById("deleteContact1").style.borderColor = "#999";
		}
		
		if(isNaN(document.getElementById("deleteContact1").value) == true){
			document.getElementById("deleteContact1").style.color = "red";
		}
		else{
			document.getElementById("deleteContact1").style.color = "#999";
		}

		return null;
	}

	var del_contact1 = document.getElementById("deleteContact1").value;
	document.getElementById("deleteContact1").style.borderColor = "#999";
	document.getElementById("deleteContact1").style.color = "#999";

    $.ajax({
        async: false,
		url: url_o_findMobile,
		type: "POST",
		contentType:"text/plain",
		dataType: "json",
		data: del_contact1,
		success: function(data){

            if(data == 90){
                alert("Apologies, technical issue. Please try again later!");
            }else if(data == 0 || data.outputUser.userDetails.status == 0){
                alert("Sorry! We don't remember this contact. Please do check the number & try again.");
            }else if(data.outputUser.userDetails.status == 1){
                tabcontent = document.getElementsByClassName("links");
                    for (i = 0; i < tabcontent.length; i++) {
                    tabcontent[i].style.display = "none";
                }
                document.getElementById('a-delete-2').style.display = "block";

                document.getElementById("d_shopname").innerHTML = data.outputUser.userDetails.shop_name;
                document.getElementById("d_ownername").innerHTML = data.outputUser.userDetails.owner_name;
                document.getElementById("d_contact1").innerHTML = data.outputUser.userDetails.mobile_primary;
                document.getElementById("d_contact2").innerHTML = data.outputUser.userDetails.mobile_secondary;
                document.getElementById("d_mail").innerHTML = data.outputUser.userDetails.mail_id;
                document.getElementById("d_address").innerHTML = data.outputUser.userDetails.address_line;
                document.getElementById("d_areacity").innerHTML = data.outputUser.userDetails.area + ", " + data.outputUser.userDetails.city;
                document.getElementById("d_pincode").innerHTML = data.outputUser.userDetails.pincode;
                document.getElementById("d_location").innerHTML = data.outputUser.userDetails.googlemap_link;
                document.getElementById("d_coveringareas").innerHTML = data.outputUser.userDetails.covering_area;

                document.getElementById("d_profession").innerHTML = "";
                for(var i = 0; i < data.outputUser.userCatalogueArray.length; i++){
                    document.getElementById("d_profession").innerHTML += data.outputUser.userCatalogueArray[i].catalogue_name;
                    if(i != data.outputUser.userCatalogueArray.length - 1){
                        document.getElementById("d_profession").innerHTML += ", ";
                    }
                    
                }

            }
		}
	});
	
}

// delete page 2 - after confirming
function delete_confirm(){

	var del_contact1 = document.getElementById("d_contact1").innerHTML;

	$.ajax({
        async: false,
		url: url_o_findMobile,
		type: "POST",
		contentType:"text/plain",
		dataType: "json",
		data: del_contact1,
		success: function(data){

            if(data == 90){
                alert("Apologies, technical issue. Please try again later!");
			}else{
				$.ajax({
					async: false,
					url: url_otp_send,
					type: "POST",
					contentType:"text/plain",
					dataType: "json",
					data: del_contact1,
					success: function(data1){
						if(data1 == 90){
							alert("Apologies, technical issue. Please try again later!");
						}else if(data1 == 1){
							var tries = 2;
							var checkOTP = false;
			
							while(tries > 0 && !checkOTP){
								var userOTP = prompt("Enter the OTP sent to mobile number: (Number of tries remaining : " + tries + " )");
								tries--;
			
								var command = "delete";
								var otpobj = {
									command: command,
									otp_number: userOTP,
									password: null,
									outputUser: data.outputUser
								};
			
								$.ajax({
									async: false,
									url: url_otp_check,
									type: "POST",
									contentType:"application/json; charset=utf-8",
									dataType: "json",
									data: JSON.stringify(otpobj),
									success: function(result){
										if(result == 1){
											alert("We deleted you but we always remember you!");
											checkOTP = true;
											delete_reset();
										}else if(result == 2 && tries > 0){
											alert("Incorrect OTP, Please try again later!");
										}else if(result == 2 && tries == 0){
											alert("Incorrect OTP, Your limit is exhausted. Please try again later!");
											delete_reset();
										}else if(result == 0){
											alert("Sorry! We don't remember this contact. Please do check the number & try again.");
											tries = 0;
											delete_reset();
										}else if(result == 3){
											alert("OTP expired! Please try again from first.");
											tries = 0;
											delete_reset();
										}else{
											alert("Technical Error! Please report to us in User Section.(" + result + ")");
											tries = 0;
											delete_reset();
										}
									}
								});
			
							}
			
						}
					}
				});
			}
		}
	});

}


// addupdate page1 - mobile number verification
function addupdate1_redirect(){

	if(document.getElementById("addupdateContact").value.length != 10 || isNaN(document.getElementById("addupdateContact").value) == true){

		if(document.getElementById("addupdateContact").length != 10){
			console.log("length problem");
            document.getElementById("addupdateContact").style.borderColor = "red";
		}else{
			document.getElementById("addupdateContact").style.borderColor = "#999";
		}
		
		if(isNaN(document.getElementById("addupdateContact").value) == true){
			document.getElementById("addupdateContact").style.color = "red";
		}
		else{
			document.getElementById("addupdateContact").style.color = "#999";
		}

		return null;
	}

	var del_contact1 = document.getElementById("addupdateContact").value;
	document.getElementById("addupdateContact").style.borderColor = "#999";
	document.getElementById("addupdateContact").style.color = "#999";

	addupdate_reset();

    $.ajax({
        async: false,
		url: url_o_findMobile,
		type: "POST",
		contentType:"text/plain",
		dataType: "json",
		data: del_contact1,
		success: function(data){

            if(data == 90){
                alert("Apologies, technical issue. Please try again later!");
            }else if(data == 0){
                console.log("inside new user registration page");
                tabcontent = document.getElementsByClassName("links");
                    for (i = 0; i < tabcontent.length; i++) {
                    tabcontent[i].style.display = "none";
                }
                
                document.getElementById("a-addupdate").style.display = "block";
				document.getElementById("addupdate-head").innerHTML = "Register yourself !";
				document.getElementById("addupdate-des").innerHTML = "Seems like you're new to our group!";
				document.getElementById("addContact1").value = del_contact1;

            }else{
                console.log("inside old user udpate page");
                tabcontent = document.getElementsByClassName("links");
                    for (i = 0; i < tabcontent.length; i++) {
                    tabcontent[i].style.display = "none";
                }
                
                document.getElementById("a-addupdate").style.display = "block";
                document.getElementById("addupdate-head").innerHTML = "Update yourself !";
				document.getElementById("addupdate-des").innerHTML = "Seems like you're already registered to our group!";

                document.getElementById("addShopName").value = data.outputUser.userDetails.shop_name;
                document.getElementById("addOwnerName").value = data.outputUser.userDetails.owner_name;
                document.getElementById("addContact1").value = data.outputUser.userDetails.mobile_primary;
                document.getElementById("addContact2").value = data.outputUser.userDetails.mobile_secondary;
                document.getElementById("addEmail").value = data.outputUser.userDetails.mail_id;
                document.getElementById("addAddress").value = data.outputUser.userDetails.address_line;
                document.getElementById("addArea").value = data.outputUser.userDetails.area;
                document.getElementById("addCity").value = data.outputUser.userDetails.city;
                document.getElementById("addPincode").value = data.outputUser.userDetails.pincode;
                document.getElementById("addMap").value = data.outputUser.userDetails.googlemap_link;
                document.getElementById("addCoverArea").value = data.outputUser.userDetails.covering_area;

                // document.getElementById("d_profession").innerHTML = "";
                for(var i = 0; i < data.outputUser.userCatalogueArray.length; i++){
                    document.getElementById("cbId_" + data.outputUser.userCatalogueArray[i].catalogue_id).checked = true;
                }

            }
		}
	});
	
}

// add update page 2 - reset 
function addupdate_reset(){
	document.getElementById("addupdateContact").value= "";
	document.getElementById("addupdateContact").style.borderColor = "#999";
	document.getElementById("addupdateContact").style.color = "#999";

	document.getElementById("addShopName").value = "";
	document.getElementById("addOwnerName").value = "";
	document.getElementById("addContact1").value = "";
	document.getElementById("addContact2").value = "";
	document.getElementById("addEmail").value = "";
	document.getElementById("addAddress").value = "";
	document.getElementById("addArea").value = "";
	document.getElementById("addCity").value = "";
	document.getElementById("addPincode").value = "";
	document.getElementById("addMap").value = "";
	document.getElementById("addCoverArea").value = "";
	document.getElementById("addPassword").value = "";

	checkboxes = document.getElementsByName('checkbox_category');
	for(var i=0, n=checkboxes.length;i<n;i++) {
		checkboxes[i].checked = false;
	}
}

// verify and add/update user
function addupdate_confirm(){
	
	//validating details
	if(document.getElementById("addShopName").value == "" ||
		document.getElementById("addOwnerName").value == "" ||
		document.getElementById("addContact1").value.length != 10 ||
		document.getElementById("addArea").value == "" ||
		document.getElementById("addCity").value == "" ||
		document.getElementById("addPincode").value == "" ||
		document.getElementById("addPassword").value.length != 4 ){

			if(document.getElementById("addShopName").value == ""){
				document.getElementById("addShopName").style.borderColor = "red";
			}else{
				document.getElementById("addShopName").style.borderColor = "#999";
			}

			if(document.getElementById("addOwnerName").value == ""){
				document.getElementById("addOwnerName").style.borderColor = "red";
			}else{
				document.getElementById("addOwnerName").style.borderColor = "#999";
			}

			if(document.getElementById("addContact1").value.length != 10){
				document.getElementById("addContact1").style.borderColor = "red";
			}else{
				document.getElementById("addContact1").style.borderColor = "#999";
			}

			if(document.getElementById("addArea").value == ""){
				document.getElementById("addArea").style.borderColor = "red";
			}else{
				document.getElementById("addArea").style.borderColor = "#999";
			}

			if(document.getElementById("addCity").value == ""){
				document.getElementById("addCity").style.borderColor = "red";
			}else{
				document.getElementById("addCity").style.borderColor = "#999";
			}

			if(document.getElementById("addPincode").value == ""){
				document.getElementById("addPincode").style.borderColor = "red";
			}else{
				document.getElementById("addPincode").style.borderColor = "#999";
			}

			if(document.getElementById("addPassword").value.length !=4 ){
				document.getElementById("addPassword").style.borderColor = "red";
			}else{
				document.getElementById("addPassword").style.borderColor = "#999";
			}

			return null;
	}

	//resetting
	document.getElementById("addShopName").style.borderColor = "#999";
	document.getElementById("addOwnerName").style.borderColor = "#999";
	document.getElementById("addContact1").style.borderColor = "#999";
	document.getElementById("addArea").style.borderColor = "#999";
	document.getElementById("addCity").style.borderColor = "#999";
	document.getElementById("addPincode").style.borderColor = "#999";
	document.getElementById("addPassword").style.borderColor = "#999";

	// storing data in variables to generate json
	var in_shopName = document.getElementById("addShopName").value;
	var in_ownerName = document.getElementById("addOwnerName").value;
	var in_contact1 = document.getElementById("addContact1").value;
	var in_contact2 = document.getElementById("addContact2").value;
	var in_email = document.getElementById("addEmail").value;
	var in_address = document.getElementById("addAddress").value;
	var in_area = document.getElementById("addArea").value;
	var in_city = document.getElementById("addCity").value;
	var in_pincode = document.getElementById("addPincode").value;
	var in_map = document.getElementById("addMap").value;
	var in_coverArea = document.getElementById("addCoverArea").value;
	var in_password = document.getElementById("addPassword").value;
	
	var checkboxes = document.querySelectorAll('input[name="checkbox_category"]:checked'), values = [];
	var userCatalogueArray = [];

	Array.prototype.forEach.call(checkboxes, function(element) {
		var userCatalogueArray1 = {
			user_id: "",
			catalogue_id: parseInt(element.value),
			catalogue_name: ""
			
		}

		userCatalogueArray.push(userCatalogueArray1);
	});

	$.ajax({
        async: false,
		url: url_o_findMobile,
		type: "POST",
		contentType:"text/plain",
		dataType: "json",
		data: in_contact1,
		success: function(data){

            if(data == 90){
                alert("Apologies, technical issue. Please try again later!");
			}else{
				if(data == 0){
					var temp_userid = "dummy";
					var command = "add";
				}else{
					var temp_userid = data.outputUser.userDetails.user_id;
					var command = "update";
				}

				$.ajax({
					async: false,
					url: url_otp_send,
					type: "POST",
					contentType:"text/plain",
					dataType: "json",
					data: in_contact1,
					success: function(data1){
						
						if(data1 == 90){
							alert("Apologies, technical issue. Please try again later!");
						}else if(data1 == 1){
							console.log("otp sent");
							var tries = 2;
							var checkOTP = false;
			
							while(tries > 0 && !checkOTP){
								var userOTP = prompt("Enter the OTP sent to mobile number: (Number of tries remaining : " + tries + " )");
								tries--;
								checkOTP = true;

								var userDetails = {
									user_id: temp_userid,
									shop_name: in_shopName,
									owner_name: in_ownerName,
									mobile_primary: in_contact1,
									mobile_secondary: in_contact2,
									mail_id: in_email,
									address_line: in_address,
									area: in_area,
									city: in_city,
									pincode: in_pincode,
									googlemap_link: "",
									covering_area: "poonamallee",
									status: 1
								};

								var outputUser = {
									userDetails: userDetails,
									userCatalogueArray: userCatalogueArray
								}
								
								var outputJSON = {
									command: command,
									otp_number: userOTP,
									password: in_password,
									outputUser: outputUser
								};

								console.log(outputJSON);

								$.ajax({
									async: false,
									url: url_otp_check,
									type: "POST",
									contentType:"application/json; charset=utf-8",
									dataType: "json",
									data: JSON.stringify(outputJSON),
									success: function(result){
										if(result == 1){
											alert("You have successfully registered to Labrage");
											checkOTP = true;
											addupdate_reset();
										}else if(result == 2 && tries > 0){
											alert("Incorrect OTP, Please try again later!");
										}else if(result == 2 && tries == 0){
											alert("Incorrect OTP, Your limit is exhausted. Please try again later!");
											addupdate_reset();
										}else if(result == 0){
											alert("Sorry! We don't remember this contact. Please do check the number & try again.");
											tries = 0;
											addupdate_reset();
										}else if(result == 3){
											alert("OTP expired! Please try again from first.");
											tries = 0;
											addupdate_reset();
										}else{
											alert("Technical Error! Please report to us in User Section.(" + result + ")");
											tries = 0;
											addupdate_reset();
										}
									}
								});
			
							}
			
						}
					}
				});
			}
		}
	});
}