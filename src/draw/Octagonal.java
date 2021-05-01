package draw;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

public class Octagonal extends Canvas{

	public int levels = 10;
	double level;
	public final static int MAX_SQUARE_SIZE = 800;
	public final static String GREETING = "";
	public final static int MARGIN = 0;
	double[] coords;
	double[] lengths;

	public final static int SYMMETRIES = 8;
	public boolean add_colour = true, very_random = true;
	
	public Octagonal(int levels, boolean colour, boolean very_random) {
		super(levels, colour, very_random);
		this.add_colour = colour;
		this.very_random = very_random;
		this.levels = levels;
		level = MAX_SQUARE_SIZE / (levels*0.9);
		coords = new double[levels];
		lengths = new double[levels];
		squares = new Rectangle[levels];
		drawOctagons(levels);
	}		
	
	private void drawOctagons(int level) {
		double centre = MARGIN + MAX_SQUARE_SIZE / 2;
		double offset = MAX_SQUARE_SIZE/(2*levels);
		int sides = 8;
		for (int i = level; i > 0; i--) {
			Color colour = setColour();
			Polygon octagon = new Polygon();
			octagon.getPoints().addAll(new Double[] { 
					centre + Math.sin((1*2+1) * Math.PI / sides) * i*offset, centre + Math.cos((1*2+1) * Math.PI / sides) * i*offset,
					centre + Math.sin((2*2+1) * Math.PI / sides) * i*offset, centre + Math.cos((2*2+1) * Math.PI / sides) * i*offset,
					centre + Math.sin((3*2+1) * Math.PI / sides) * i*offset, centre + Math.cos((3*2+1) * Math.PI / sides) * i*offset,
					centre + Math.sin((4*2+1) * Math.PI / sides) * i*offset, centre + Math.cos((4*2+1) * Math.PI / sides) * i*offset,
					centre + Math.sin((5*2+1) * Math.PI / sides) * i*offset, centre + Math.cos((5*2+1) * Math.PI / sides) * i*offset,
					centre + Math.sin((6*2+1) * Math.PI / sides) * i*offset, centre + Math.cos((6*2+1) * Math.PI / sides) * i*offset,
					centre + Math.sin((7*2+1) * Math.PI / sides) * i*offset, centre + Math.cos((7*2+1) * Math.PI / sides) * i*offset,
					centre + Math.sin((8*2+1) * Math.PI / sides) * i*offset, centre + Math.cos((8*2+1) * Math.PI / sides) * i*offset
			 });
			octagon.setStroke(Color.BLACK);
			octagon.setFill(colour);
			group.getChildren().add(octagon);
		}
	}
}
