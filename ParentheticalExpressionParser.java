public class ParentheticalExpressionParser implements ExpressionParser {
    @Override
    public Expression parse(String str, boolean withJavaFXControls) throws ExpressionParseException {
        //X := (E) | L
        ParentheticalExpression expression = new ParentheticalExpression();

        SimpleExpressionParser ep = new SimpleExpressionParser();
        if(str.length() >= 3) {
            expression.e = ep.parseExpression(str.substring(1, str.length() - 1)); //beginIndex - the beginning index, inclusive ; endIndex - the ending index, exclusive.

            if (str.charAt(0) == '('
                    &&  str.charAt(str.length() - 1) == ')'
                    && expression.e != null) {
                return expression;
            }
        }

        LiteralExpressionParser lp = new LiteralExpressionParser();
        expression.le = (LiteralExpression) lp.parse(str, false);

        if (expression.le != null) {
            return expression;
        }

        return null;
    }
}
