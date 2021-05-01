package draw;

import javafx.scene.paint.*;
	
public interface iCanvas {

	public int levels = 10;
	public final static int MAX_SQUARE_SIZE = 800;
	public final static String GREETING = "";
	public final static int MARGIN = 0;
	public final static int SYMMETRIES = 8;
	public boolean add_colour = true, very_random = true;

	public Color getRandomColour();
	
	public Color setColour();

	public void drawLabels();
	
	public void setText(String str, int x, int y);
}

	

