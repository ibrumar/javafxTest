import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.Node;

import java.util.List;

public abstract class AbstractCompoundExpression implements CompoundExpression {
    //Variables
    public List<Expression> _subexpression;
    private CompoundExpression _expr;
    private String _field; //String that equals either "A", "M", or "P" (cannot be LiteralExpression)
    public static Node focusedNode = null;
    public Node parent;
    public Border border;

    //Constructors
    public AbstractCompoundExpression(CompoundExpression expr, List<Expression> subexpression) {
        _expr = expr;
        _subexpression = subexpression;

        //Cannot be a LiteralExpression, can only be either Additive, Multiplicative, or Parenthetical
        if(expr instanceof AdditiveExpression)
            _field = "A";
        else if(expr instanceof MultiplicativeExpression)
            _field = "M";
        else
            _field = "P";
    }

    public AbstractCompoundExpression() { //Default constructor

    }


    //Methods
    public Expression deepCopy() {
        //TODO implement or make abstract
        AbstractCompoundExpression copy;

        if(_field == "A")
            copy = new AdditiveExpression();
        else if(_field == "M")
            copy = new MultiplicativeExpression();
        else
            copy = new ParentheticalExpression();

        for(Expression subexpr : _subexpression) {
            copy._subexpression.add(subexpr.deepCopy());
        }

        return copy;
    }



    public String convertToString(int indentLevel) {
        StringBuffer sb = new StringBuffer();

        for(Expression subexpr : _subexpression) {
            subexpr.convertToString(indentLevel + 1);
        }

        return sb.toString();
    }
}
