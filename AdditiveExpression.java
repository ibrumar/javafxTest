import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

//Production Rules
//Terminals = [0-9], [a-z]

//CADY COMMENT 

public class AdditiveExpression extends SimpleCompoundExpression {//implements CompoundExpression {
    //Variables
    private CompoundExpression _parent;
    protected List<MultiplicativeExpression> _subexpression; //or is it an ArrayList
    private int _numSubExpr;
    public AdditiveExpression ae;
    public MultiplicativeExpression me;
    public Node node;
    public int count = 0;
    public int focusLevel = 0;


    //private void requestNodeFocus(Node node) { // TODO: 12/11/18 add a field of type NOde named parent in each type of expression in prder to remember for each expression who is its parent
    //    node.setOnMouseClicked( ( e ) ->
    //    {
    //        System.out.println(count++ + " ---------------------------------------- \n Node clicked from ADDITIVE.");
    //        if (parent == null) {
    //            System.out.println("ADDITIVE: Parent is null.");
    //            System.out.println("ADDITIVE: NODE IS " + node);


    //        }
    //        //if (parent.equals(focusedNode)) System.out.println("ADDITIVE: Parent matches focusedNode.");
    //        if (parent != null && parent.equals(focusedNode)) {
    //            System.out.println("parent??");
    //            ((Region)_parent.getNode()).setBorder(Expression.NO_BORDER);
    //            //((Region) node).setBorder(Expression.RED_BORDER);
    //        }
    //        //node.requestFocus();
    //        ((Region) node).setBorder(RED_BORDER);
    //        if (AbstractCompoundExpression.focusedNode != null) // if there is a focused node
    //            ((Region)AbstractCompoundExpression.focusedNode).setBorder(NO_BORDER); // we remove its border
    //        AbstractCompoundExpression.focusedNode = node;
    //    } );
    //}

    ////it composes only if there is more than one element
    //private void composeMultipleSubexpressions(String operator) { //we mainly need this in the derivate classes
    //    for (int i = 1; i < _subexpression.size(); ++i) {
    //        Node kidNode =_subexpression.get(i).getNode();
    //        _subexpression.get(i).parent = node;
    //        requestNodeFocus(kidNode);
    //        ((HBox) node).getChildren().addAll(new Label(operator), kidNode);
    //    }
    //}

    //public Node getNode() {
    //    node = new HBox(8);
    //    Node firstNode =_subexpression.get(0).getNode(); // we call the get node on the first kid because we don't want do add a "+" if there are no more than 1 kid.
    //    _subexpression.get(0).parent = node;
    //    //if (_subexpression.get(0).parent.equals(hbox)) System.out.println("ADDITIVE: THE PARENT OF THE FIRST CHILD IS " + hbox);
    //    requestNodeFocus(firstNode);
    //    ((HBox) node).getChildren().addAll(firstNode); //reuse this code from here and MultiplicativeExpression

    //    //hbox.getChildren().addAll(new Label("Kid")); //reuse this code from here and MultiplicativeExpression
    //    composeMultipleSubexpressions( "+");
    //    // when vbox is clicked focus on it
    //    System.out.println("THIS NODE IS " + node + "and has children:");
    //    for (int i = 0; i < _subexpression.size(); i++) {
    //        if (_subexpression.get(i).parent.equals(node)) System.out.println("TRUEEEEEEEEE");
    //        System.out.println("   " + ((MultiplicativeExpression)_subexpression.get(i)).node);
    //    }
    //    return (Node) node;
    //}

    private int computeHashOnToString(String str) {
       int result = 0;
       for (char chr : str.toCharArray()){
           result += chr;
       }
       return result;
    }

    private void programOnClickEvent(Node kidNode, MultiplicativeExpression kidEx) {

//        System.out.println("You are programming the " + kidEx.convertToString(0) + " \n expression ");
        kidNode.setOnMouseClicked( ( e ) ->
        {
            System.out.println("You are executing the addition function");

            int expressionId;
            if (kidEx.parent != null) {
                expressionId = computeHashOnToString(kidEx.convertToString(0));
                System.out.println("id="+expressionId + "| " +"The node you clicked on is " + kidEx.convertToString(0));
            }
            else {
                expressionId = -1;
                System.out.println("id="+expressionId + "| " +"This node has null parent");
            }

            //if (AbstractCompoundExpression.focusedNode != null && AbstractCompoundExpression.focusLevel == focusLevel) //eliminating the previous border


            if (AbstractCompoundExpression.focusedNode == null)
                System.out.println("id="+expressionId + "| " +"There is no previous focused node");
            else
                System.out.println("id="+expressionId + "| " +"There was a previous focused node");

            System.out.println("id="+expressionId + "| " +"Kid's parent is the focused node="+ kidEx.parent.equals(AbstractCompoundExpression.focusedNode));
            System.out.println("id="+expressionId + "| " +"Focused node is null="+ (AbstractCompoundExpression.focusedNode==null));
            System.out.println("id="+expressionId + "| " +"AbstractCompoundExpression.focusLevel is the abstract focus level="+ (AbstractCompoundExpression.focusLevel==kidEx.focusLevel));
            System.out.println("id="+expressionId + "| the abstract focused level is " + AbstractCompoundExpression.focusLevel);
            System.out.println("id="+expressionId + "| the kidExFocus is +" + kidEx.focusLevel);


            //if (parent.equals(focusedNode)) System.out.println("ADDITIVE: Parent matches focusedNode.");
            if ((kidEx.parent.equals(AbstractCompoundExpression.focusedNode) || AbstractCompoundExpression.focusedNode == null) && AbstractCompoundExpression.focusLevel + 1 == kidEx.focusLevel) {
                if (AbstractCompoundExpression.focusedNode != null)
                    ((Region)AbstractCompoundExpression.focusedNode).setBorder(NO_BORDER);
            //if ((kidEx.parent != null && kidEx.parent.equals(focusedNode)) || AbstractCompoundExpression.focusedNode == null) {
                //((Region)kidNode).setBorder(Expression.NO_BORDER);
                ((Region) kidNode).setBorder(RED_BORDER);
                kidNode.requestFocus();
                AbstractCompoundExpression.focusedNode = kidNode;
                System.out.println("id="+expressionId + "| " +"The node that took into account an event " + kidEx.convertToString(0) );
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
        System.out.println("\n SUMA id="+expressionId + "| " +"The node you are parsing is " + thisNodeStr + " with focus level " + focusLevel + " and hbox " + node + " and parent " + parent);
        _subexpression.get(0).parent = node;
        _subexpression.get(0).focusLevel = focusLevel + 1;
        Node firstNode = _subexpression.get(0).getNode();
        //if (_subexpression.get(0).parent.equals(hbox)) System.out.println("MULTIPLICATIVE: THE PARENT OF" + " IS THE FIRST CHILD IS " + hbox);
        programOnClickEvent(firstNode, _subexpression.get(0));
        ((HBox) node).getChildren().addAll(firstNode);
        composeMultipleSubexpressions("+");
        return node;
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
