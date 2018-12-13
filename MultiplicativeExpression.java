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
    public int focusLevel = 0;


    //Constructors
    public MultiplicativeExpression() {
        _subexpression = new ArrayList<>();
        _numSubExpr = 0;
        me1 = null;
        me2 = null;
        pe = null;
    }

    //public Node getNode() {
    //    HBox hbox = new HBox(8);
    //    hbox.getChildren().addAll(new Label("*"), new Label("Kids to be added"));
    //    return (Node) hbox;
    //}


    private int computeHashOnToString(String str) {
        int result = 0;
        for (char chr : str.toCharArray()){
            result += chr;
        }
        return result;
    }


    private void programOnClickEvent(Node kidNode, ParentheticalExpression kidEx) {
        kidNode.setOnMouseClicked( ( e ) ->
        {
            System.out.println("You are executing the multiplication function");

            int expressionId;
            if (kidEx.parent != null) {
                expressionId = computeHashOnToString(kidEx.convertToString(0));
                System.out.println("id="+expressionId + "| " +"The node you clicked on is " + kidEx.convertToString(0));
            }
            else {
                expressionId = -1;
                System.out.println("id="+expressionId + "| " +"This node has null parent");
            }

            if (AbstractCompoundExpression.focusedNode == null)
                System.out.println("id="+expressionId + "| " +"There is no previous focused node");
            else
                System.out.println("id="+expressionId + "| " +"There was a previous focused node");

            //if (AbstractCompoundExpression.focusedNode != null && AbstractCompoundExpression.focusLevel == focusLevel) //eliminating the previous border
            //if (parent.equals(focusedNode)) System.out.println("ADDITIVE: Parent matches focusedNode.");
            System.out.println("id="+expressionId + "| " +"Kid's parent is the focused node="+ kidEx.parent.equals(AbstractCompoundExpression.focusedNode));
            System.out.println("id="+expressionId + "| " +"Focused node is null="+ (AbstractCompoundExpression.focusedNode==null));
            System.out.println("id="+expressionId + "| " +"AbstractCompoundExpression.focusLevel+1 is the kid focus level="+ (AbstractCompoundExpression.focusLevel + 1==kidEx.focusLevel));
            System.out.println("id="+expressionId + "| the abstract focused level is +" + AbstractCompoundExpression.focusLevel);
            System.out.println("id="+expressionId + "| the kidExFocus is +" + kidEx.focusLevel);

            if ((kidEx.parent.equals(AbstractCompoundExpression.focusedNode) || AbstractCompoundExpression.focusedNode == null) && AbstractCompoundExpression.focusLevel + 1 == kidEx.focusLevel) {
            //if ((kidEx.parent != null && kidEx.parent.equals(focusedNode)) || AbstractCompoundExpression.focusedNode == null && AbstractCompoundExpression.focusLevel == kidEx.focusLevel) {
                //((Region)kidNode).setBorder(Expression.NO_BORDER);
                if (AbstractCompoundExpression.focusedNode != null)
                    ((Region)AbstractCompoundExpression.focusedNode).setBorder(NO_BORDER);
                ((Region) kidNode).setBorder(RED_BORDER);
                kidNode.requestFocus();
                AbstractCompoundExpression.focusedNode = kidNode;
                System.out.println("id="+expressionId + "| " +"The node that took into account an event is a " + kidEx.convertToString(0) );
                System.out.println("id="+expressionId + "| " +"and the kid node you are setting is  " + kidNode );
                System.out.println("id="+expressionId + "| " +"which translated to the focused node  " + AbstractCompoundExpression.focusedNode );
                ++AbstractCompoundExpression.focusLevel;
            }
        } );
    }


    //it composes only if there is more than one element
    private void composeMultipleSubexpressions(String operator) { //we mainly need this in the derivate classes
        for (int i = 1; i < _subexpression.size(); ++i) {
            _subexpression.get(i).parent = node;
            _subexpression.get(i).focusLevel = focusLevel + 1;
            Node kidNode =_subexpression.get(i).getNode();
            programOnClickEvent(kidNode, _subexpression.get(i));
            ((HBox) node).getChildren().addAll(new Label(operator), kidNode);
        }
    }

    public Node getNode() {
        node = new HBox(8);
        //hbox.getChildren().addAll(_subexpression.get(0).getNode());
        String thisNodeStr =  convertToString(0);
        int expressionId = computeHashOnToString(thisNodeStr);
        //System.out.println("id="+expressionId + "| " +"The node you clicked on is " + thisNodeStr + "with focus level" + focusLevel);
        System.out.println("\n MULT: id="+expressionId + "| " +"The node you are parsing is " + thisNodeStr + " with focus level " + focusLevel + " and hbox " + node + " and parent " + parent);

        _subexpression.get(0).parent = node;
        _subexpression.get(0).focusLevel = focusLevel + 1;
        Node firstNode =_subexpression.get(0).getNode();
        //if (_subexpression.get(0).parent.equals(hbox)) System.out.println("MULTIPLICATIVE: THE PARENT OF" + " IS THE FIRST CHILD IS " + hbox);
        programOnClickEvent(firstNode, _subexpression.get(0));
        ((HBox)node).getChildren().addAll(firstNode);
        composeMultipleSubexpressions("*");
        //System.out.println("THIS NODE IS " + node + "and has children:");
        //for (int i = 0; i < _subexpression.size(); i++) {
        //    if (_subexpression.get(i).parent.equals(node)) System.out.println("TRUEEEEEEEEE");
        //    System.out.println("   " + ((ParentheticalExpression)_subexpression.get(i)).node);
        //}
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
