import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

import java.util.ArrayList;
import java.util.List;

public class MultiplicativeExpression extends SimpleCompoundExpression {
    //Variables
    private CompoundExpression _parent;
    protected List<ParentheticalExpression> _subexpression;
    private int _numSubExpr;
    public MultiplicativeExpression me1;
    public MultiplicativeExpression me2;
    public ParentheticalExpression pe;
    public Node node;


    //Constructors
    public MultiplicativeExpression() {
        _subexpression = new ArrayList<>();
        _numSubExpr = 0;
        me1 = null;
        me2 = null;
        pe = null;
    }

    private void requestNodeFocus(Node node) {
        node.setOnMouseClicked( ( e ) ->
        {
            System.out.println("Node clicked m");
            System.out.println("Node clicked from MULTIPLICATIVE.");
            if (parent == null) {
                System.out.println("MULTIPLICATIVE: Parent is null.");
                System.out.println("ADDITIVE: NODE IS " + node);
            }
            //if (parent.equals(focusedNode)) System.out.println("ADDITIVE: Parent matches focusedNode.");
            if (parent != null && parent.equals(focusedNode)) {
                System.out.println("parent??");
                ((Region)_parent.getNode()).setBorder(Expression.NO_BORDER);
                //((Region) node).setBorder(Expression.RED_BORDER);
            }
            node.requestFocus();
            ((Region) node).setBorder(RED_BORDER);
            if (AbstractCompoundExpression.focusedNode != null)
                ((Region) AbstractCompoundExpression.focusedNode).setBorder(NO_BORDER);
            AbstractCompoundExpression.focusedNode = node;
        } );
    }


    //it composes only if there is more than one element
    private void composeMultipleSubexpressions(String operator) { //we mainly need this in the derivate classes
        for (int i = 1; i < _subexpression.size(); ++i) {
            Node kidNode =_subexpression.get(i).getNode();
            _subexpression.get(i).parent = node;
            requestNodeFocus(kidNode);
            ((HBox) node).getChildren().addAll(new Label(operator), kidNode);
        }
    }

    public Node getNode() {
        node = new HBox(8);
        //hbox.getChildren().addAll(_subexpression.get(0).getNode());
        Node firstNode =_subexpression.get(0).getNode();
        _subexpression.get(0).parent = node;
        //if (_subexpression.get(0).parent.equals(hbox)) System.out.println("MULTIPLICATIVE: THE PARENT OF" + " IS THE FIRST CHILD IS " + hbox);
        requestNodeFocus(firstNode);
        ((HBox)node).getChildren().addAll(firstNode);
        composeMultipleSubexpressions("*");
        System.out.println("THIS NODE IS " + node + "and has children:");
        for (int i = 0; i < _subexpression.size(); i++) {
            if (_subexpression.get(i).parent.equals(node)) System.out.println("TRUEEEEEEEEE");
            System.out.println("   " + ((ParentheticalExpression)_subexpression.get(i)).node);
        }
        return (Node) node;
    }

    public void addSubexpression(Expression subexpression) {
        _subexpression.add((ParentheticalExpression) subexpression);
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
    }

    /**
     * Creates and returns a deep copy of the expression.
     * The entire tree rooted at the target node is copied, i.e.,
     * the copied Expression is as deep as possible.
     * @return the deep copy
     */
    @Override
    public Expression deepCopy() {
        final MultiplicativeExpression copy = new MultiplicativeExpression();
        //List<Expression> copyChildren = new ArrayList<>();

        for(Expression subexpr : _subexpression) {
            copy._subexpression.add((ParentheticalExpression) subexpr.deepCopy());
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
        for (ParentheticalExpression p : _subexpression) {
            p.flatten();
        }
    }

    /**
     * flattens the tree from the current node recursively
     * @return ArrayList with the new children of the current node
     */
    private ArrayList<ParentheticalExpression> flattenRec() {
        ArrayList<ParentheticalExpression> ar = new ArrayList<>();
        if (pe != null) { //if the ParentheticalExpression child is not null, then we have only a ParentheticalExpression child
            ar.add(pe);
        } else { //M = M * M
            ar.addAll(me1.flattenRec());
            ar.addAll(me2.flattenRec());
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
            sb.append("*\n");
            indentLevel++;
        }

        for (ParentheticalExpression me : _subexpression) {
            sb.append(me.convertToString(indentLevel));
        }

        return sb.toString();
    }

    /**
     * removes the given subexpression from the List _subexpression
     * @param subexpr the subexpression to be removed
     */
    public void removeSubexpression(MultiplicativeExpression subexpr) {
        _subexpression.remove(subexpr);
    }
}
