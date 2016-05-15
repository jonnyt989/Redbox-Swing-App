package RedBoxSwing;

import java.util.EventListener;

public interface CancelListener extends EventListener {

	public void cancelEventOccurred(CancelEvent e);
}
