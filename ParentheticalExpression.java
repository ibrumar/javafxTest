import java.util.ArrayList;
import java.util.List;
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
    public Node node;
    public int focusLevel = 0;

    //Constructors
    public ParentheticalExpression() {
        _subexpression = new ArrayList<>();
        _numSubExpr = 0;
        e = null;
        le = null;
    }


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


    private void programOnClickEvent(Node kidNode, AdditiveExpression kidEx) {

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

    public Node getNode() {
        node = new HBox(8);
        String thisNodeStr =  convertToString(0);
        int expressionId = computeHashOnToString(thisNodeStr);
        System.out.println("\n PARE id="+expressionId + "| " +"The node you are parsing is " + thisNodeStr + " with focus level " + focusLevel + " and hbox " + node + " and parent " + parent);
        if (e != null) {
            ((AbstractCompoundExpression) e).parent = node;
            ((AbstractCompoundExpression) e).focusLevel = focusLevel + 1;
            ((HBox) node).getChildren().addAll(new Label("("));
            //Node kidNode = e.getNode();
            //((HBox)node).getChildren().addAll(kidNode);
            ((HBox) node).getChildren().addAll(new Label(")"));

            //if (e instanceof AdditiveExpression)
            //    programOnClickEvent(kidNode, (AdditiveExpression) e);
            //else
            //    programOnClickEvent(kidNode, (MultiplicativeExpression) e);
        } else { //in case just a literal is being contained
            ((HBox)node).getChildren().addAll(le.getNode());
            le.parent = node;
        }
        //((HBox)node).getChildren().addAll(_subexpression.get(0).getNode()); //there should always be at least one node e|le
        //hbox.getChildren().addAll(e.getNode());
        //if (e != null)
        return node;
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
