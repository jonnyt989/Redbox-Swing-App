package RedBoxSwing;

import java.util.EventObject;

public class AccountEvent extends EventObject {

	private int returnId;
	
	public AccountEvent(Object source, int returnId) {
		super(source);
		
		this.returnId = returnId;
	}

	public int getReturnId() {
		return returnId;
	}

	public void setReturnId(int returnId) {
		this.returnId = returnId;
	}
}

