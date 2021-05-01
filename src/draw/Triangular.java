package draw;

import javafx.scene.paint.*;
import javafx.scene.shape.*;

public class Triangular extends Canvas{

	public int levels = 10;
	double level;
	public final static int MAX_SQUARE_SIZE = 800;
	public final static String GREETING = "";
	public final static int MARGIN = 0;
	double[] coords;
	double[] lengths;

	public final static int SYMMETRIES = 8;
	public boolean add_colour = true, very_random = true;
	
	public Triangular(int levels, boolean colour, boolean very_random) {
		super(levels, colour, very_random);
		this.add_colour = colour;
		this.very_random = very_random;
		this.levels = levels;
		level = MAX_SQUARE_SIZE / (levels*0.9);
		coords = new double[levels];
		lengths = new double[levels];
		squares = new Rectangle[levels];
		drawTriangles(levels);
	}		

	private void drawTriangles(int level) {
		double x = MARGIN;
		double y = MARGIN;
		double factor = (Math.sqrt(3.0)/2.0);
		double levelWidth = MAX_SQUARE_SIZE/(2*levels);
		for (int j = 0; j < level; j++) {
			Color colour = setColour();
			Polygon triangle = new Polygon();
			triangle.getPoints().addAll(new Double[] { 
					MAX_SQUARE_SIZE/2.0, (MAX_SQUARE_SIZE - factor*MAX_SQUARE_SIZE) + 1.5*j*levelWidth,
					j*levelWidth/factor, MAX_SQUARE_SIZE-j*levelWidth*factor,
					MAX_SQUARE_SIZE-(j*levelWidth/factor), MAX_SQUARE_SIZE-j*levelWidth*factor
			 });
			triangle.setStroke(Color.BLACK);
			triangle.setFill(colour);
			group.getChildren().add(triangle);
		}
	}

}
