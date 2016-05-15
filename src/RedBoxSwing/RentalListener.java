package RedBoxSwing;

import java.util.EventListener;

public interface RentalListener extends EventListener{
	
	public void rentalEventOccurred(RentalEvent ev);

}