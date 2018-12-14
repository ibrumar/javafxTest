import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javax.swing.*;

public class ExpressionEditor extends Application {
    public static void main (String[] args) {
        launch(args);
    }

    /**
     * Mouse event handler for the entire pane that constitutes the ExpressionEditor
     */
    private static class MouseEventHandler implements EventHandler<MouseEvent> {
        private Pane _pane;
        private CompoundExpression _rootExpression;
        private Expression _deepCopy;
        private double _startSceneX;
        private double _startSceneY;

        MouseEventHandler (Pane pane, CompoundExpression rootExpression) {
            _pane = pane;
            _rootExpression = rootExpression;
            _deepCopy = null;
            _startSceneX = 0;
            _startSceneY = 0;
        }

        public void handle (MouseEvent event) {
            if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
                _startSceneX = event.getSceneX();
                _startSceneY = event.getSceneY();

                //if there is a focused node and the mouse click is not in its range, get rid of border
                if(AbstractCompoundExpression.focusedNode != null) {

                    double ex = AbstractCompoundExpression.focusedNode.sceneToLocal(_startSceneX, _startSceneY).getX();
                    double ey = AbstractCompoundExpression.focusedNode.sceneToLocal(_startSceneX, _startSceneY).getY();

                    if(!(AbstractCompoundExpression.focusedNode.contains(ex, ey))) {
                        ((Region) AbstractCompoundExpression.focusedNode).setBorder(Border.EMPTY);
                        AbstractCompoundExpression.focusedNode = null;
                    }
                }
            } else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
                if (AbstractCompoundExpression.focusedNode != null) {

                    double ex = AbstractCompoundExpression.focusedNode.sceneToLocal(_startSceneX, _startSceneY).getX();
                    double ey = AbstractCompoundExpression.focusedNode.sceneToLocal(_startSceneX, _startSceneY).getY();

                    //Drag
                    if(AbstractCompoundExpression.focusedNode.contains(ex, ey)) {
                        AbstractCompoundExpression.deepCopy.setTranslateX(event.getSceneX() - _startSceneX);
                        AbstractCompoundExpression.deepCopy.setTranslateY(event.getSceneY() - _startSceneY);
                    }
                }
            } else if (event.getEventType() == MouseEvent.MOUSE_RELEASED) {
                if (AbstractCompoundExpression.focusedNode != null) {
                    AbstractCompoundExpression.deepCopy.setLayoutX(event.getX() + AbstractCompoundExpression.deepCopy.getTranslateX());
                    AbstractCompoundExpression.deepCopy.setLayoutY(event.getY() + AbstractCompoundExpression.deepCopy.getTranslateY());
                    AbstractCompoundExpression.deepCopy.setTranslateX(0);
                    AbstractCompoundExpression.deepCopy.setTranslateY(0);

                    //Print out new tree
                    System.out.println(_rootExpression.convertToString(0));
                }
            }
        }
    }

    /**
     * Size of the GUI
     */
    private static final int WINDOW_WIDTH = 500, WINDOW_HEIGHT = 250;

    /**
     * Initial expression shown in the textbox
     */
    private static final String EXAMPLE_EXPRESSION = "2*x+3*y+4*z+(7+6*z)";

    /**
     * Parser used for parsing expressions.
     */
    private final ExpressionParser expressionParser = new SimpleExpressionParser();

    @Override
    public void start (Stage primaryStage) {
        primaryStage.setTitle("Expression Editor");

        // Add the textbox and Parser button
        final Pane queryPane = new HBox();
        final TextField textField = new TextField(EXAMPLE_EXPRESSION);
        final Button button = new Button("Parse");
        queryPane.getChildren().add(textField);

        final Pane expressionPane = new Pane();

        // Add the callback to handle when the Parse button is pressed
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle (MouseEvent e) {
                // Try to parse the expression
                try {
                    // Success! Add the expression's Node to the expressionPane
                    final Expression expression = expressionParser.parse(textField.getText(), true);
                    System.out.println(expression.convertToString(0));
                    expressionPane.getChildren().clear();
                    expressionPane.getChildren().add(expression.getNode());
                    expression.getNode().setLayoutX(WINDOW_WIDTH/4);
                    expression.getNode().setLayoutY(WINDOW_HEIGHT/2);

                    // If the parsed expression is a CompoundExpression, then register some callbacks
                    if (expression instanceof CompoundExpression) {
                        ((Pane) expression.getNode()).setBorder(Expression.NO_BORDER);
                        final MouseEventHandler eventHandler = new MouseEventHandler(expressionPane, (CompoundExpression) expression);
                        expressionPane.setOnMousePressed(eventHandler);
                        expressionPane.setOnMouseDragged(eventHandler);
                        expressionPane.setOnMouseReleased(eventHandler);
                    }
                } catch (ExpressionParseException epe) {
                    // If we can't parse the expression, then mark it in red
                    textField.setStyle("-fx-text-fill: red");
                }
            }
        });

        queryPane.getChildren().add(button);

        // Reset the color to black whenever the user presses a key
        textField.setOnKeyPressed(e -> textField.setStyle("-fx-text-fill: black"));

        final BorderPane root = new BorderPane();
        root.setTop(queryPane);
        root.setCenter(expressionPane);

        primaryStage.setScene(new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT));
        primaryStage.show();
    }
}