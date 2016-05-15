package RedBoxSwing;

import java.util.EventObject;

public class RentalEvent extends EventObject {

	private int movieId;
	
	public RentalEvent(Object source, int movie) {
		super(source);
		
		this.movieId = movie;
	}

	public int getMovie() {
		return movieId;
	}
}