package RedBoxSwing;

import java.util.EventListener;

public interface CheckoutListener extends EventListener {

	public void checkoutEventOccurred(CheckoutEvent e);
}
