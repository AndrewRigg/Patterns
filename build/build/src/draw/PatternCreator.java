package draw;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

public class PatternCreator extends Application {
    public static void main(String[] args) {
        launch(args);
    }
       
    public boolean add_colour = true;
    public boolean very_random = false;
    Button btn;
	public Button colour = new Button(add_colour ? "Black and White" : "Colour");
	public Button print = new Button("Print");
	public Button randomise = new Button("Randomer");
	StackPane root = new StackPane();
	StackPane pattern = new StackPane();
	VBox vbox;
    
    public void start(Stage primaryStage) {
    
    	Spinner<Integer> spinner = new Spinner<Integer>(1, 50, 14);
    	
        class IncrementHandler implements EventHandler<MouseEvent> {

            private Spinner<Integer> spinner;
            private boolean increment;
            private long startTimestamp;
            

            private static final long DELAY = 1000l * 1000L * 650L; // 0.75 sec
            private static final long NORMAL_DELAY = 1000l * 1000L * 100L; // 0.1 sec

            private final AnimationTimer timer = new AnimationTimer() {
            	private int currentFrame = 0;
            	private int previousFrame = 0; 
            	
            	@Override
            	public void handle(long now)
            	{
            		if (now - startTimestamp <= NORMAL_DELAY) {
            			if (currentFrame == previousFrame || currentFrame % 10 == 0) {
	            			if (increment)
	        	            {
	        	                spinner.increment();
	        	            }
	        	            else
	        	            {
	        	                spinner.decrement();
	        	            }
            			}
            			++currentFrame;
            		}
            		else if (now - startTimestamp >= DELAY)
            	        {
            	        if (currentFrame == previousFrame || currentFrame % 6 == 0)
            	        {
            	            if (increment)
            	            {
            	                spinner.increment();
            	            }
            	            else
            	            {
            	                spinner.decrement();
            	            }
            	        }
            	    }
            	    ++currentFrame;
            	}
            };

            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.PRIMARY) {
                    Spinner source = (Spinner) event.getSource();
                    Node node = event.getPickResult().getIntersectedNode();

                    Boolean increment = null;
                    // find which kind of button was pressed and if one was pressed
                    while (increment == null && node != source) {
                        if (node.getStyleClass().contains("increment-arrow-button")) {
                            increment = Boolean.TRUE;
                        } else if (node.getStyleClass().contains("decrement-arrow-button")) {
                            increment = Boolean.FALSE;
                        } else {
                            node = node.getParent();
                        }
                    }
                    if (increment != null) {
                        event.consume();
                        source.requestFocus();
                        spinner = source;
                        this.increment = increment;
                        startTimestamp = System.nanoTime();
                        timer.handle(startTimestamp + DELAY);
                        timer.start();
                    }
                }
            }
            
            public void stop() {
                timer.stop();
                spinner = null;
            }
        }

        IncrementHandler handler = new IncrementHandler();
        spinner.addEventFilter(MouseEvent.MOUSE_PRESSED, handler);
        spinner.addEventFilter(MouseEvent.MOUSE_RELEASED, evt -> {
            if (evt.getButton() == MouseButton.PRIMARY) {
                handler.stop();
            }
        });
        spinner.addEventFilter(MouseEvent.MOUSE_RELEASED, evt -> {
            Node node = evt.getPickResult().getIntersectedNode();
            if (node.getStyleClass().contains("increment-arrow-button") ||
                node.getStyleClass().contains("decrement-arrow-button")) {
                    if (evt.getButton() == MouseButton.PRIMARY) {
                        handler.stop();
                    }
            }
        });
        spinner.setMaxWidth(60);
        spinner.setEditable(true);
        

        primaryStage.setTitle("Patterns");
        //primaryStage.getIcons().add(new Image("resources/Patterns.ico"));
        btn = new Button("Generate Pattern");
        Label levelDescription = new Label("Size: ");
        Font font = Font.font("", FontWeight.NORMAL, FontPosture.REGULAR, 16);
        levelDescription.setFont(font);
        vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        
        HBox hbox = new HBox(5);
        hbox.setPadding(new Insets(50, 10, 10, 100));
        hbox.getChildren().addAll(levelDescription, spinner, btn, colour, print, randomise);
        vbox.getChildren().addAll(hbox);
        
        btn.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
            	createCanvas(spinner);
            }
        });
        btn.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
            	btn.fire();
            }
        }
        );
    	colour.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				add_colour = !add_colour;
				System.out.println("Clicked COLOUR!");
				colour.setText(add_colour ? "Black and White" : "Colour");
				randomise.setDisable(!add_colour ? true : false);
			}
		});
		print.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				System.out.println("Clicked PRINT!");
				print(pattern);
			}
		});
		randomise.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				very_random = !very_random;
				System.out.println("Clicked randomise!");
				randomise.setText(very_random ? "Normal" : "Random");
			}
		});
		randomise.setDisable(!add_colour ? true : false);
		root.getChildren().add(vbox);
        primaryStage.setScene(new Scene(root, 1000, 1000));
        primaryStage.show();
    }
    public void createCanvas(Spinner<Integer> spinner) {
    	pattern.getChildren().clear();
    	root.getChildren().clear();
    	Canvas canvas = new Canvas(spinner.getValue(), add_colour, very_random);
    	pattern.getChildren().add(canvas.group);
    	root.getChildren().add(pattern);
    	root.getChildren().add(vbox);
    }
    
	public void print(Node node) {
		PrinterJob job = PrinterJob.createPrinterJob();
		if (job != null && job.showPrintDialog(root.getScene().getWindow())) {
			final Printer printer = job.getPrinter();
			final Paper paper = job.getJobSettings().getPageLayout().getPaper();
			System.out.println("Colour settings: " + printer.getPrinterAttributes().getDefaultPrintColor());
			final PageLayout pageLayout = printer.createPageLayout(paper, PageOrientation.LANDSCAPE, Printer.MarginType.DEFAULT);
			double scaleX = 1.0;
			System.out.println("printable width: "+ pageLayout.getPrintableWidth());
			if (pageLayout.getPrintableWidth() < node.getBoundsInParent().getWidth()) {
				scaleX = pageLayout.getPrintableWidth() / (node.getBoundsInParent().getWidth());
			}
			double scaleY = 1.0;
			if (pageLayout.getPrintableHeight() < node.getBoundsInParent().getHeight()) {
				scaleY = pageLayout.getPrintableHeight() / (node.getBoundsInParent().getHeight());
			}
			double scaleXY = 1.1*Double.min(scaleX, scaleY);
			Scale scale = new Scale(scaleXY, scaleXY);
			node.getTransforms().add(scale);
			
			boolean success = job.printPage(node);
			node.getTransforms().remove(scale);
			if (success) {
				job.endJob();
			}
		}
	}
}