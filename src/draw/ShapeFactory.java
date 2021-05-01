package draw;

public class ShapeFactory {

	String shapeDescription;
	
	public Canvas createShape(int shape, int levels, boolean colour, boolean very_random) {
		switch (shape) {
		
			case 0:
				shapeDescription = "Circle";
				return new Circular(levels, colour, very_random);
			case 1:
				shapeDescription = "Triangle";
				System.out.println("Creating triangles");
				return new Triangular(levels, colour, very_random);
			case 2:
				shapeDescription = "Square";
				return new Square(levels, colour, very_random);
			case 3:
				shapeDescription = "Pentagon";
				return new Pentagonal(levels, colour, very_random);
			case 4:
				shapeDescription = "Hexagon";
				return new Hexagonal(levels, colour, very_random);
			case 5: 
				shapeDescription = "Heptagon";
				return new Heptagonal(levels, colour, very_random);
			case 6: 
				shapeDescription = "Octagon";
				return new Octagonal(levels, colour, very_random);
			case 7:
				shapeDescription = "Squiggly";
				return new Squiggly(levels, colour, very_random);
			default:
				shapeDescription = "Default";
				return new Square(levels, colour, very_random);
		}
	}
}
