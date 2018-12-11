/**
 * Starter code to implement an ExpressionParser. Your code should parse a context-free grammar
 * for mathematical expressions for addition, multiplication, and parentheses over single-letter
 * and integer operands. Suggested (though not required) grammar:
 * E := AdditiveExpression | X
 * AdditiveExpression := AdditiveExpression+MultiplicativeExpression | MultiplicativeExpression
 * MultiplicativeExpression := MultiplicativeExpression*MultiplicativeExpression | X
 * X := (E) | LiteralExpression
 * LiteralExpression := [0-9]+ | [a-z]
 */

public class SimpleExpressionParser implements ExpressionParser {
	/**
	 * Attempts to create an expression tree -- flattened as much as possible -- from the specified String.
	 * Throws a ExpressionParseException if the specified string cannot be parsed.
	 * @param str the string to parse into an expression tree
	 * @param withJavaFXControls you can just ignore this variable for R1
	 * @return the Expression object representing the parsed expression tree
	 */
	public Expression parse (String str, boolean withJavaFXControls) throws ExpressionParseException {
		// Remove spaces -- this simplifies the parsing logic
		str = str.replaceAll(" ", "");
		Expression expression = parseExpression(str);
		if (expression == null) {
			// If we couldn't parse the string, then raise an error
			throw new ExpressionParseException("Cannot parse expression: " + str);
		}

		// Flatten the expression before returning
		expression.flatten();
		return expression;
	}

	/**
	 *
	 * @param str
	 * @return
	 */
	protected Expression parseExpression (String str) throws ExpressionParseException {
		Expression expression;

		// TODO implement me
		// E is a SimpleCompoundExpression
		AdditiveExpressionParser ap = new AdditiveExpressionParser();
		//System.out.println("SimpleExpressionParser AdditiveParser");
		expression = ap.parse(str, false);
		if (expression != null) { //E -> A
			//System.out.println("if statement");
			//ae.setParent((CompoundExpression) expression); //todo not sure if it is safe to do this casting
			return expression;
		}

		ParentheticalExpressionParser pp = new ParentheticalExpressionParser();
		expression = pp.parse(str, false);
		if (expression != null) { // E -> X
			//pe.setParent((CompoundExpression) expression); //todo not sure if it is safe to do this casting
			return expression;
		}

		return null;
	}
}
