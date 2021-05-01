package draw;

import javafx.scene.paint.*;
import javafx.scene.shape.*;

public class Circular extends Canvas{

	
	public Circular(int levels, boolean colour, boolean very_random) {
		super(levels, colour, very_random);
		drawCircles(levels);
	}
	
	private void drawCircles(int level) {
		double levelWidth = MAX_SQUARE_SIZE/(2*level);
		for (int i = 0; i < level; i++) {
			Circle circle = new Circle(MAX_SQUARE_SIZE/2, MAX_SQUARE_SIZE/2, MAX_SQUARE_SIZE/2-levelWidth*i);
			circle.setFill(setColour());
			circle.setStroke(Color.BLACK);
			group.getChildren().add(circle);
		}
	}
}
