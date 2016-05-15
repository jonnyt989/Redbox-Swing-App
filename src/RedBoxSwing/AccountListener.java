package RedBoxSwing;

import java.util.EventListener;

public interface AccountListener extends EventListener{
	
	public void accountEventOccurred(AccountEvent ev);

}