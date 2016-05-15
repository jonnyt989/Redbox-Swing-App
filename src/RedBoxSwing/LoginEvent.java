package RedBoxSwing;

import java.util.EventObject;

public class LoginEvent extends EventObject {

	private String email;

	
	public LoginEvent(Object source, String email) {
		super(source);

		this.email = email;	
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}