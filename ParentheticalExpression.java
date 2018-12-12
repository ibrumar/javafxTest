import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

public class ParentheticalExpression extends AbstractCompoundExpression {
    //Variables
    private CompoundExpression _parent;
    protected List<Expression> _subexpression;
    private int _numSubExpr;
    public Expression e;
    public LiteralExpression le;

    //Constructors
    public ParentheticalExpression() {
        _subexpression = new ArrayList<>();
        _numSubExpr = 0;
        e = null;
        le = null;
    }

    public Node getNode() {
        HBox hbox = new HBox(8);
        if (e != null)
            hbox.getChildren().addAll(new Label("("));
        hbox.getChildren().addAll(_subexpression.get(0).getNode()); //there should always be at least one node e|le
        if (e != null)
            hbox.getChildren().addAll(new Label(")"));
        return hbox;
    }


    @Override
    public void addSubexpression(Expression subexpression) {
        _subexpression.add(subexpression);
        _numSubExpr++;
        _subexpression.get(_subexpression.size() - 1).setParent(this);
    }

    /**
     * Returns the expression's parent.
     * @return the expression's parent
     */
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

       // _parent.addSubexpression(this);

        /*//not sure if this is needed
        if(_subexpression.size() > 0) {
            for (Expression subexpr : _subexpression) {
                _parent.addSubexpression(subexpr);
            }
        }*/
    }

    /**
     * Creates and returns a deep copy of the expression.
     * The entire tree rooted at the target node is copied, i.e.,
     * the copied Expression is as deep as possible.
     * @return the deep copy
     */
    @Override
    public Expression deepCopy() {
        final ParentheticalExpression copy = new ParentheticalExpression();

        for(Expression subexpr : _subexpression) {
            copy._subexpression.add(subexpr.deepCopy()); //not sure if i need .deepCopy()
        }

        return copy;
    }

    /**
     * Recursively flattens the expression as much as possible
     * throughout the entire tree. Specifically, in every multiplicative
     * or additive expression x whose first or last
     * child c is of the same type as x, the children of c will be added to x, and
     * c itself will be removed. This method modifies the expression itself.
     */
    @Override
    public void flatten() {
        if (e != null) {
            e.flatten();
            _subexpression.add(e);
        } else
            _subexpression.add(le);
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

        if (e != null) {
            Expression.indent(sb, indentLevel);
            sb.append("()\n");
            sb.append(e.convertToString(indentLevel + 1));
        } else {
            sb.append(le.convertToString(indentLevel));
        }

        return sb.toString();
    }

    /**
     * removes the given subexpression from the List _subexpression
     * @param subexpr the subexpression to be removed
     */
    public void removeSubexpression(ParentheticalExpression subexpr) {
        _subexpression.remove(subexpr);
    }
}
