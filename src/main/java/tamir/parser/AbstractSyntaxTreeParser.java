package tamir.parser;

import tamir.exception.BlankExpressionException;
import tamir.exception.InvalidAbstractSyntaxTreeStructureException;
import tamir.exception.UnsupportedBinaryOperatorForAstException;
import tamir.token.Operator;

import java.util.List;
import java.util.Stack;

import static java.lang.Integer.parseInt;
import static tamir.token.ExpressionTokenizer.*;
import static tamir.token.Operator.isBinaryOperator;

public class AbstractSyntaxTreeParser {

	public static AbstractSyntaxTreeNode parseExpressionIntoAbstractSyntaxTree(String expression) {
		validateExpressionIsNotBlank(expression);
		List<String> tokens = splitIntoTokens(expression);
		AbstractSyntaxTreeBuilder astBuilder = new AbstractSyntaxTreeBuilder();
		tokens.forEach(astBuilder::addToken);

		return astBuilder.getAbstractSyntaxTreeRootNode();
	}

	private static void validateExpressionIsNotBlank(String expression) {
		if (expression == null || expression.isBlank())
			throw new BlankExpressionException();
	}

	private static class AbstractSyntaxTreeBuilder {

		private final Stack<Operator> operators;
		private final Stack<AbstractSyntaxTreeNode> expressions;

		AbstractSyntaxTreeBuilder() {
			this.operators = new Stack<>();
			this.expressions = new Stack<>();
		}

		void addToken(String token) {
			if (isVariable(token)) expressions.push(new VariableAstNode(token));
			else if (isInteger(token)) expressions.push(new IntegerAstNode(parseInt(token)));
			else if (isPreIncrement(token))
				expressions.push(new PreIncrementAstNode(getVariableNameOfPreUnaryOperatorToken(token)));
			else if (isPostIncrement(token))
				expressions.push(new PostIncrementAstNode(getVariableNameOfPostUnaryOperatorToken(token)));
			else if (isPreDecrement(token))
				expressions.push(new PreDecrementAstNode(getVariableNameOfPreUnaryOperatorToken(token)));
			else if (isPostDecrement(token))
				expressions.push(new PostDecrementAstNode(getVariableNameOfPostUnaryOperatorToken(token)));
			else if (isBinaryOperator(token)) {
				Operator operator = Operator.getFromToken(token);
				popBinaryOperatorsWithHigherPrecedence(operator);
				operators.push(operator);
			} else throw new InvalidAbstractSyntaxTreeStructureException(operators, expressions, token);
		}

		AbstractSyntaxTreeNode getAbstractSyntaxTreeRootNode() {
			if (expressions.size() != 1)
				throw new InvalidAbstractSyntaxTreeStructureException(operators, expressions);
			return expressions.pop();
		}

		private void popBinaryOperatorsWithHigherPrecedence(Operator operator) {
			while (!operators.isEmpty() && isOperatorOfLowerPrecedenceThanTopOperator(operator)) {
				expressions.push(popBinaryOperatorAstNode());
			}
		}

		private boolean isOperatorOfLowerPrecedenceThanTopOperator(Operator operator) {
			return operators.peek().getPrecedence() >= operator.getPrecedence();
		}

		private AbstractSyntaxTreeNode popBinaryOperatorAstNode() {
			AbstractSyntaxTreeNode rightExpression = expressions.pop();
			AbstractSyntaxTreeNode leftExpression = expressions.pop();

			Operator topOperator = operators.pop();
			switch (topOperator) {
				case ADDITION:
					return new AdditionAstNode(leftExpression, rightExpression);
				case SUBTRACTION:
					return new SubtractionAstNode(leftExpression, rightExpression);
				case MULTIPLICATION:
					return new MultiplicationAstNode(leftExpression, rightExpression);
				case DIVISION:
					return new DivisionAstNode(leftExpression, rightExpression);
				case ASSIGNMENT:
					return new AssignmentAstNode(leftExpression, rightExpression);
				case ADDITION_ASSIGNMENT:
					return new AdditionAssignmentAstNode(leftExpression, rightExpression);
				case SUBTRACTION_ASSIGNMENT:
					return new SubtractionAssignmentAstNode(leftExpression, rightExpression);
				case MULTIPLICATION_ASSIGNMENT:
					return new MultiplicationAssignmentAstNode(leftExpression, rightExpression);
				case DIVISION_ASSIGNMENT:
					return new DivisionAssignmentAstNode(leftExpression, rightExpression);
				default:
					throw new UnsupportedBinaryOperatorForAstException(topOperator);
			}
		}
	}
}
