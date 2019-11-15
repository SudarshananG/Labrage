package com.deadshot.labrage_v3.InputModel;

import com.deadshot.labrage_v3.OutputModel.OutputUser;

public class OtpCheck {
	
	private String command;
	private String otp_number;
	private String password;
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	private OutputUser outputUser;

	public OutputUser getOutputUser() {
		return outputUser;
	}

	public void setOutputUser(OutputUser outputUser) {
		this.outputUser = outputUser;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}
	
	public String getOtp_number() {
		return otp_number;
	}
	
	public void setOtp_number(String otp_number) {
		this.otp_number = otp_number;
	}
	
}
