package draw;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.QuadCurve;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Canvas {

	public final Group group;
	public int levels = 10;
	double level;
	public final static int MAX_SQUARE_SIZE = 800;
	public final static String GREETING = "";
	public final static int MARGIN = 0;
	double[] coords;
	double[] lengths;

	Rectangle[] squares;
	public final static int SYMMETRIES = 8;
	public boolean add_colour = true, very_random = true;

	public Canvas(int levels, boolean colour, boolean very_random) {
		this.add_colour = colour;
		this.very_random = very_random;
		this.levels = levels;
		level = MAX_SQUARE_SIZE / (levels * 0.9);
		coords = new double[levels];
		lengths = new double[levels];
		squares = new Rectangle[levels];
		group = new Group();
		drawSquares();
		drawLines();
		drawLabels();
		addPatternsToLevels();
		drawDiagonals();
		colourSquares();
		drawCentralPattern();
	}



	private void drawCentralPattern() {
		double centre = MARGIN + MAX_SQUARE_SIZE / 2;
		double offset = level;
		int sides = 8;
		for (int i = 1; i <= 8; i++) {
			QuadCurve quad = new QuadCurve(centre + Math.sin(2 * i * Math.PI / sides) * offset / 2,
					centre + Math.cos(2 * i * Math.PI / sides) * offset / 2,
					centre + Math.sin(2 * (i + 0.5) * Math.PI / sides) * offset / 4,
					centre + Math.cos(2 * (i + 0.5) * Math.PI / sides) * offset / 4,
					centre + Math.sin(2 * (i + 1) * Math.PI / sides) * offset / 2,
					centre + Math.cos(2 * (i + 1) * Math.PI / sides) * offset / 2);
			quad.setFill(Color.TRANSPARENT);
			quad.setStroke(Color.BLACK);
			group.getChildren().add(quad);
		}
	}

	private void drawSquares() {
		double square_length = MAX_SQUARE_SIZE;
		double x = MARGIN;
		double y = MARGIN;
		for (int i = levels; i > 0; i--) {
			coords[i - 1] = x;
			lengths[i - 1] = square_length;
			double variation = (i == levels ? 0 : (i % 4 == 1) ? 0.5 : 1.0);
			Rectangle rectangle = new Rectangle(x += variation * level / 2, y += variation * level / 2,
					square_length -= (variation * level), square_length);
			rectangle.setFill(i % 2 != 0 ? Color.WHITE : Color.BEIGE);
			rectangle.setStroke(Color.BLACK);
			squares[i - 1] = rectangle;
			group.getChildren().add(rectangle);
		}
	}

	private void colourSquares() {
		for (int i = 0; i < levels; i++) {
			squares[i].setFill(setColour());
		}
	}

	private Color getRandomColour() {
		int red = (int) (Math.random() * 128 + 80);
		int green = (int) (Math.random() * 128 + 100);
		int blue = (int) (Math.random() * 128 + 100);
		return Color.rgb(red, green, blue);
	}

	private void addPatternsToLevels() {
		for (int i = levels - 2; i > 0; i--) {
			if (i % 4 == 3) {
				drawTriangles(i + 1, coords[i], coords[i - 1] - coords[i], lengths[i]);
			} else if (i % 4 == 0) {
				// do nothing (blank section)
			} else if (i % 4 == 1) {
				System.out.println("Drawing Circles: " + i);
				drawCircles(i + 1, coords[i], coords[i - 1] - coords[i], lengths[i]);
			} else {
				drawCubicCurve(i + 1, coords[i], coords[i - 1] - coords[i], lengths[i]);
			}
		}
	}

	private void drawTriangles(int level, double coord, double levelWidth, double levelLength) {
		System.out.println("Drawing Triangles " + level + ", " + coord + ", " + levelWidth + ", " + levelLength);
		Color colour = setColour();
		for (int i = 0; i <= 3; i++) {
			for (int j = 0; j < level; j++) {
				Polygon triangle = new Polygon();
				if (i == 0 || i == 3) {
					triangle.getPoints()
							.addAll(new Double[] { (levelLength / level) * j + coord,
									(i == 3 ? coord + levelLength : coord),
									(levelLength / level) * j + (levelLength / level) / 2 + coord,
									(i == 3 ? coord + levelLength - levelWidth : coord + levelWidth),
									(levelLength / level) * (j + 1) + coord, (i == 3 ? coord + levelLength : coord) });
				} else {
					triangle.getPoints()
							.addAll(new Double[] { (i == 2 ? coord + levelLength : coord),
									(levelLength / level) * j + coord,
									(i == 2 ? coord + levelLength - levelWidth : coord + levelWidth),
									(levelLength / level) * j + (levelLength / level) / 2 + coord,
									(i == 2 ? coord + levelLength : coord), (levelLength / level) * (j + 1) + coord });
				}
				triangle.setStroke(Color.BLACK);
				triangle.setFill(very_random ? setColour() : colour);
				group.getChildren().add(triangle);
			}
		}
	}

	private Color setColour() {
		if (!add_colour) {
			return Color.WHITE;
		} else {
			return getRandomColour();
		}
	}

	private void drawCircles(int level, double coord, double levelWidth, double levelLength) {
		Color colour1 = setColour();
		Color colour2 = setColour();
		Color colour3 = setColour();
		for (int i = 0; i <= 3; i++) {
			for (int j = 0; j < level - 1; j++) {
				if ((level % 8 == 2 && j % 2 == 0) || (level % 8 == 6 && j % 2 == 1)) {
					Circle circle1 = new Circle(
							coord + ((i == 0 || i == 2) ? (j + 1) * (levelLength / level)
									: levelWidth / 2 + (i == 1 ? 0 : levelLength - levelWidth)),
							((i == 1 || i == 3) ? (j + 1) * (levelLength / level)
									: levelWidth / 2 + (i == 0 ? 0 : levelLength - levelWidth)) + coord,
							levelWidth * 0.35);
					Circle circle2 = new Circle(
							coord + ((i == 0 || i == 2) ? (j + 1) * (levelLength / level)
									: levelWidth / 2 + (i == 1 ? 0 : levelLength - levelWidth)),
							((i == 1 || i == 3) ? (j + 1) * (levelLength / level)
									: levelWidth / 2 + (i == 0 ? 0 : levelLength - levelWidth)) + coord,
							levelWidth * 0.65);
					circle1.setFill(very_random ? setColour() : colour1);
					circle2.setFill(very_random ? setColour() : colour2);
					circle1.setStroke(Color.BLACK);
					circle2.setStroke(Color.BLACK);
					group.getChildren().add(circle2);
					group.getChildren().add(circle1);
				} else if (level > 4) {
					Circle circle3 = new Circle(
							coord + ((i == 0 || i == 2) ? (j + 1) * (levelLength / level)
									: levelWidth / 2 + (i == 1 ? 0 : levelLength - levelWidth)),
							((i == 1 || i == 3) ? (j + 1) * (levelLength / level)
									: levelWidth / 2 + (i == 0 ? 0 : levelLength - levelWidth)) + coord,
							levelWidth * 0.5);
					circle3.setFill(very_random ? setColour() : colour3);
					circle3.setStroke(Color.BLACK);
					group.getChildren().add(circle3);
				}
			}
		}
	}

	private void drawCubicCurve(int level, double coord, double levelWidth, double levelLength) {
		for (int i = 0; i <= 3; i++) {
			for (int j = 0; j < level / 2; j++) {
				double offset = (levelLength - 2 * levelWidth) / (level / 2);
				double offset2 = levelWidth;
				if (i == 0 || i == 2) {
					CubicCurve cubicCurve = new CubicCurve();
					cubicCurve.setStartX(j * offset + coord + levelWidth);
					cubicCurve.setStartY(
							i == 0 ? coord + levelWidth : coord + levelWidth + (levelLength - 2 * levelWidth));
					cubicCurve.setControlX1(cubicCurve.getStartX() + offset / 6);
					cubicCurve.setControlY1(i == 0 ? coord + levelWidth - offset2
							: coord + levelWidth + (levelLength - 2 * levelWidth) + offset2);
					cubicCurve.setControlX2(cubicCurve.getControlX1() + offset / 6);
					cubicCurve.setControlY2(i == 0 ? coord + offset2 : coord + levelLength - offset2);
					cubicCurve.setEndX(cubicCurve.getControlX2() + offset / 6);
					cubicCurve.setEndY(i == 0 ? coord : levelLength + coord);
					cubicCurve.setFill(Color.TRANSPARENT);
					cubicCurve.setStroke(Color.BLACK);

					CubicCurve cubicCurve2 = new CubicCurve();
					cubicCurve2.setStartX(cubicCurve.getEndX());
					cubicCurve2.setStartY(cubicCurve.getEndY());
					cubicCurve2.setControlX1(cubicCurve2.getStartX() + offset / 6);
					cubicCurve2.setControlY1(cubicCurve.getControlY2());
					cubicCurve2.setControlX2(cubicCurve2.getControlX1() + offset / 6);
					cubicCurve2.setControlY2(cubicCurve.getControlY1());
					cubicCurve2.setEndX(cubicCurve2.getControlX2() + offset / 6);
					cubicCurve2.setEndY(cubicCurve.getStartY());
					cubicCurve2.setFill(Color.TRANSPARENT);
					cubicCurve2.setStroke(Color.BLACK);

					Shape shape = Shape.union(cubicCurve, cubicCurve2);

					group.getChildren().add(cubicCurve);
					group.getChildren().add(cubicCurve2);
				} else {
					CubicCurve cubicCurve = new CubicCurve();
					cubicCurve.setStartY(j * offset + coord + levelWidth);
					cubicCurve.setStartX(
							i == 1 ? coord + levelWidth : coord + levelWidth + (levelLength - 2 * levelWidth));
					cubicCurve.setControlY1(cubicCurve.getStartY() + offset / 6);
					cubicCurve.setControlX1(i == 1 ? coord + levelWidth - offset2
							: coord + levelWidth + (levelLength - 2 * levelWidth) + offset2);
					cubicCurve.setControlY2(cubicCurve.getControlY1() + offset / 6);
					cubicCurve.setControlX2(i == 1 ? coord + offset2 : coord + levelLength - offset2);
					cubicCurve.setEndY(cubicCurve.getControlY2() + offset / 6);
					cubicCurve.setEndX(i == 1 ? coord : levelLength + coord);
					cubicCurve.setFill(Color.TRANSPARENT);
					cubicCurve.setStroke(Color.BLACK);

					CubicCurve cubicCurve2 = new CubicCurve();
					cubicCurve2.setStartY(cubicCurve.getEndY());
					cubicCurve2.setStartX(cubicCurve.getEndX());
					cubicCurve2.setControlY1(cubicCurve2.getStartY() + offset / 6);
					cubicCurve2.setControlX1(cubicCurve.getControlX2());
					cubicCurve2.setControlY2(cubicCurve2.getControlY1() + offset / 6);
					cubicCurve2.setControlX2(cubicCurve.getControlX1());
					cubicCurve2.setEndY(cubicCurve2.getControlY2() + offset / 6);
					cubicCurve2.setEndX(cubicCurve.getStartX());
					cubicCurve2.setFill(Color.TRANSPARENT);
					cubicCurve2.setStroke(Color.BLACK);

					group.getChildren().add(cubicCurve);
					group.getChildren().add(cubicCurve2);
				}
			}
		}
	}

	private void drawDiagonals() {
		setUpLine(MARGIN, MARGIN, MARGIN + MAX_SQUARE_SIZE, MARGIN + MAX_SQUARE_SIZE);
		setUpLine(MARGIN, MARGIN + MAX_SQUARE_SIZE, MARGIN + MAX_SQUARE_SIZE, MARGIN);
	}

	private void drawLines() {
		setUpLine(MARGIN, MARGIN, MARGIN + MAX_SQUARE_SIZE, MARGIN);
		setUpLine(MARGIN, MARGIN, MARGIN, MARGIN + MAX_SQUARE_SIZE);
		setUpLine(MARGIN + MAX_SQUARE_SIZE, MARGIN, MARGIN + MAX_SQUARE_SIZE, MARGIN + MAX_SQUARE_SIZE);
		setUpLine(MARGIN, MARGIN + MAX_SQUARE_SIZE, MARGIN + MAX_SQUARE_SIZE, MARGIN + MAX_SQUARE_SIZE);
	}

	private void setUpLine(int startX, int startY, int endX, int endY) {
		Line line = new Line();
		line.setStartX(startX);
		line.setStartY(startY);
		line.setEndX(endX);
		line.setEndY(endY);
		group.getChildren().add(line);
	}

	private void drawLabels() {
		setText(GREETING, 0, -100);
	}

	public void setText(String str, int x, int y) {
		Text text = new Text(str);
		text.setFont(Font.font("verdana", 15));
		text.setX(x - text.getFont().getSize() / 2);
		text.setY(y + text.getFont().getSize() / 2);
		group.getChildren().add(text);
	}
}
