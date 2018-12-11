import java.util.ArrayList;

public class AdditiveExpressionParser implements ExpressionParser {
    // A := A+M | M
    @Override
    public Expression parse(String str, boolean withJavaFXControls) throws ExpressionParseException {
        AdditiveExpression expression = new AdditiveExpression();

        AdditiveExpressionParser ap = new AdditiveExpressionParser();
        MultiplicativeExpressionParser mp = new MultiplicativeExpressionParser();


        for (int i = 1; i < str.length() - 1; i++) {
            if (str.charAt(i) == '+') {
                expression.ae = (AdditiveExpression) ap.parse(str.substring(0, i), false); // TODO: 04/12/2018 rememnber to do the same in the other parsers: AdditiveExpression expression; for the returning data type and also the same I did in this line

                expression.me = (MultiplicativeExpression) mp.parse(str.substring(i + 1), false);

                if (expression.ae != null && expression.me != null) {
                    return expression;
                }
            }

        }

         expression.me = (MultiplicativeExpression) mp.parse(str, false);

        if (expression.me != null) {
            return expression;
        }

        return null;
    }
}
