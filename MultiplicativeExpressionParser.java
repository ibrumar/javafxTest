public class MultiplicativeExpressionParser implements ExpressionParser {
    @Override
    public Expression parse(String str, boolean withJavaFXControls) throws ExpressionParseException {
        //M := M*M | X

        MultiplicativeExpression expression = new MultiplicativeExpression();
        MultiplicativeExpressionParser mp = new MultiplicativeExpressionParser();



        for (int i = 1; i < str.length() - 1; i++) {
            if (str.charAt(i) == '*') {
                expression.me1 = (MultiplicativeExpression) mp.parse(str.substring(0,i), false);
                expression.me2 = (MultiplicativeExpression) mp.parse(str.substring(i + 1), false);
                if (expression.me1 != null && expression.me2 != null) {
                    return expression;
                }
            }

        }

        ParentheticalExpressionParser pp = new ParentheticalExpressionParser();
        expression.pe = (ParentheticalExpression) pp.parse(str, false);

        if (expression.pe != null) {
            return expression;
        }

        return null;
    }
}
