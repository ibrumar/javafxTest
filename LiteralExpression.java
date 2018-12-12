import java.util.ArrayList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;


public class LiteralExpression implements Expression { //hello !!
    //Variables
    private String _str;
    private CompoundExpression _parent;
    private int _numSubExpr;
    private int _indentationLevel;
    public Node parent;

    //Constructors
    public LiteralExpression(String str) {
        _str = str;
        _numSubExpr = 0;
        _indentationLevel = 0;
    }


    public Node getNode() {
        Node node = new Label(_str);
        //requestNodeFocus(node);
        return node;
    }

    /**
     * Returns the expression's parent.
     * @return the expression's parent
     */
    //Methods
    @Override
    public CompoundExpression getParent() {
        return _parent;
    }

    /**
     * Sets the parent be the specified expression.
     * @param parent the CompoundExpression that should be the parent of the target object
     */
    @Override
    public void setParent(CompoundExpression parent) {
        _parent = parent;
    }

    /**
     * Creates and returns a deep copy of the expression.
     * The entire tree rooted at the target node is copied, i.e.,
     * the copied Expression is as deep as possible.
     * @return the deep copy
     */
    @Override
    public Expression deepCopy() {
        //TODO make sure this is correct
        final LiteralExpression copy = new LiteralExpression(new String(_str));

        return copy;
    }

    /**
     * Recursively flattens the expression as much as possible
     * throughout the entire tree. Specifically, in every multiplicative
     * or additive expression x whose first or last
     * child c is of the same type as x, the children of c will be added to x, and
     * c itself will be removed. This method modifies the expression itself.
     * This method should not be used because you cannot flatten a LiteralExpression.
     */
    @Override
    public void flatten() {
        System.out.println("flatten literal");
    }

    /**
     * Creates a String representation by recursively printing out (using indentation) the
     * tree represented by this expression, starting at the specified indentation level.
     * @param indentLevel the indentation level (number of tabs from the left margin) at which to start
     * @return a String representation of the expression tree.
     */
    @Override
    public String convertToString(int indentLevel) {
        StringBuffer sb = new StringBuffer();

        Expression.indent(sb, indentLevel);
        sb.append(_str);
        sb.append("\n");

        return sb.toString();
    }
}
