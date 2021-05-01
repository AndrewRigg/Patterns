package draw;

import javafx.scene.Group;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.text.*;

public class Canvas {

	//public final Group group;
	public int levels = 10;
	double level;
	public final static int MAX_SQUARE_SIZE = 800;
	public final static String GREETING = "";
	public final static int MARGIN = 0;
	double[] coords;
	double[] lengths;
	public Group group;

	Rectangle[] squares;
	public final static int SYMMETRIES = 8;
	public boolean add_colour = true, very_random = true;

	public Canvas(int levels, boolean colour, boolean very_random) {
		group = new Group();
		this.add_colour = colour;
		this.very_random = very_random;
		this.levels = levels;
		level = MAX_SQUARE_SIZE / (levels*0.9);
		coords = new double[levels];
		lengths = new double[levels];
		squares = new Rectangle[levels];
		drawLabels();
	}

	protected Color getRandomColour() {
		int red = (int) (Math.random() * 128 + 80);
		int green = (int) (Math.random() * 128 + 100);
		int blue = (int) (Math.random() * 128 + 100);
		return Color.rgb(red, green, blue);
	}
	
	protected Color setColour() {
		if (!add_colour) {
			return Color.WHITE;
		} else {
			return getRandomColour();
		}
	}

	protected void drawLabels() {
		setText(GREETING, 0, -100);
	}

	protected void setText(String str, int x, int y) {
		Text text = new Text(str);
		text.setFont(Font.font("verdana", 15));
		text.setX(x - text.getFont().getSize() / 2);
		text.setY(y + text.getFont().getSize() / 2);
		//group.getChildren().add(text);
	}
}
