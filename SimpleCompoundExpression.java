import javafx.scene.control.Label;
import javafx.scene.Node;

public class SimpleCompoundExpression extends AbstractCompoundExpression {
    private CompoundExpression _parent;

    public Node getNode() {
        Node node = new Label();
        return node;
    }


    @Override
    public CompoundExpression getParent() {
        return null;
    }

    @Override
    public void setParent(CompoundExpression parent) {
        this._parent = parent;
    }

    @Override
    public void flatten() {

    }

    @Override
    public String convertToString(int indentLevel) {
        return null;
    }

    @Override
    public void addSubexpression(Expression subexpression) {

    }
}
