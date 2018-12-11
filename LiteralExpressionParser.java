import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LiteralExpressionParser implements ExpressionParser {
    @Override
    public Expression parse(String str, boolean withJavaFXControls) throws ExpressionParseException {
        LiteralExpression expression = new LiteralExpression(str);

        if (isVariable(str) || isNumber(str)) {
            return expression;
        }

        return null;
    }

    private boolean isVariable(String s) {
        return itMatches(s, "[0-9]+");
    }

    private boolean isNumber(String s) {
        return itMatches(s, "[a-z]");
    }

    private boolean itMatches(String s, String pat) {
        Pattern pattern = Pattern.compile(pat);
        Matcher matcher = pattern.matcher(s);
        // it returns true if s is a letter/number and it does not contain different elements than letter/number, respectivelly.
        return matcher.find() && matcher.group().length() == s.length();
    }
}
