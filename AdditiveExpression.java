import javafx.scene.layout.HBox;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

//Production Rules
//Terminals = [0-9], [a-z]

public class AdditiveExpression extends SimpleCompoundExpression {//implements CompoundExpression {
    //Variables
    private CompoundExpression _parent;
    protected List<MultiplicativeExpression> _subexpression; //or is it an ArrayList
    private int _numSubExpr;
    public AdditiveExpression ae;
    public MultiplicativeExpression me;
    public Node node;
    public int count = 0;

    /**
     *
     * @param node
     */
    private void requestNodeFocus(Node node) {
        node.setOnMouseClicked( ( e ) ->
        {
            node.requestFocus();

            ((Region) node).setBorder(RED_BORDER);

            AbstractCompoundExpression.deepCopy = AbstractCompoundExpression.focusedNode;

            if (AbstractCompoundExpression.focusedNode != null) { // if there is a focused node
                ((Region) AbstractCompoundExpression.focusedNode).setBorder(NO_BORDER);
            }// we remove its border

            AbstractCompoundExpression.focusedNode = node;
        } );
    }

    /**
     *
     * @param operator
     */
    //it composes only if there is more than one element
    private void composeMultipleSubexpressions(String operator) { //we mainly need this in the derivate classes
        for (int i = 1; i < _subexpression.size(); ++i) {
            Node kidNode =_subexpression.get(i).getNode();
            _subexpression.get(i).parent = node;
            requestNodeFocus(kidNode);
            ((HBox) node).getChildren().addAll(new Label(operator), kidNode);
        }
    }

    /**
     *
     * @return
     */
    public Node getNode() {
        node = new HBox(8);
        Node firstNode =_subexpression.get(0).getNode(); // we call the get node on the first kid because we don't want do add a "+" if there are no more than 1 kid.
        _subexpression.get(0).parent = node;
        //if (_subexpression.get(0).parent.equals(hbox)) System.out.println("ADDITIVE: THE PARENT OF THE FIRST CHILD IS " + hbox);
        requestNodeFocus(firstNode);
        ((HBox) node).getChildren().addAll(firstNode); //reuse this code from here and MultiplicativeExpression

        //hbox.getChildren().addAll(new Label("Kid")); //reuse this code from here and MultiplicativeExpression
        composeMultipleSubexpressions( "+");
        // when vbox is clicked focus on it
        for (int i = 0; i < _subexpression.size(); i++) {
            if (_subexpression.get(i).parent.equals(node)) System.out.println("TRUEEEEEEEEE");
            System.out.println("   " + ((MultiplicativeExpression)_subexpression.get(i)).node);
        }
        return (Node) node;
    }

    //Constructors
    public AdditiveExpression() {
        _subexpression = new ArrayList<>();
        _numSubExpr = 0;
        ae = null;
        me = null;
    }

    //Methods
    @Override
    public void addSubexpression(Expression subexpression) {
        _subexpression.add((MultiplicativeExpression) subexpression);
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

        for(Expression subexpr : _subexpression) {
            _parent.addSubexpression(subexpr);
        }
    }

    /**
     * Creates and returns a deep copy of the expression.
     * The entire tree rooted at the target node is copied, i.e.,
     * the copied Expression is as deep as possible.
     * @return the deep copy
     */
    @Override
    public Expression deepCopy() {
        final AdditiveExpression copy = new AdditiveExpression();
        //List<Expression> copyChildren = new ArrayList<>();

        for(Expression subexpr : _subexpression) {
            copy._subexpression.add((MultiplicativeExpression) subexpr.deepCopy());
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
        _subexpression = this.flattenRec();
        for (MultiplicativeExpression m : _subexpression) {
            m.flatten();
        }
    }

    /**
     * flattens the tree from the current node recursively
     * @return ArrayList with the new children of the current node
     */
    private ArrayList<MultiplicativeExpression> flattenRec() {
        ArrayList<MultiplicativeExpression> ar = new ArrayList<>();
        if (ae == null) { //if the AdditiveExpression child is null, then we have only a MultiplicativeExpression child
            ar.add(me);
        } else { //A = A + M
            ar.addAll(ae.flattenRec());
            ar.add(me);
        }
        return ar;
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

        if (_subexpression.size() > 1) {
            Expression.indent(sb, indentLevel);
            sb.append("+\n");
            indentLevel++;
        }

        for (MultiplicativeExpression me : _subexpression) {
            sb.append(me.convertToString(indentLevel));
        }

        return sb.toString();
    }

    /**
     * removes the given subexpression from the List _subexpression
     * @param subexpr the subexpression to be removed
     */
    public void removeSubexpression(AdditiveExpression subexpr) {
        _subexpression.remove(subexpr);
    }
}
