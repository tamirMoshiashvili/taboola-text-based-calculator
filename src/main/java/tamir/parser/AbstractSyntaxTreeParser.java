package tamir.parser;

import tamir.exception.*;
import tamir.parser.ast.*;
import tamir.parser.operator.AssignmentOperator;
import tamir.parser.operator.BinaryOperator;

import java.util.List;
import java.util.Stack;

import static java.lang.Integer.parseInt;
import static tamir.parser.ExpressionTokenizer.*;
import static tamir.parser.operator.BinaryOperator.isBinaryOperator;

public class AbstractSyntaxTreeParser {

	private static final int VARIABLE_TOKEN_INDEX = 0;
	private static final int ASSIGNMENT_TOKEN_INDEX = 1;
	private static final long VARIABLE_AND_ASSIGNMENT_TOKENS_SIZE = 2;
	private static final int MIN_NUM_TOKENS_OF_ASSIGNMENT_EXPRESSION = 3;

	public static AssignmentAstNode parseExpressionIntoAbstractSyntaxTree(String expression) {
		validateExpressionIsNotBlank(expression);
		List<String> tokens = splitIntoTokens(expression);
		validateNumberOfTokens(tokens);

		return parseAbstractSyntaxTree(tokens);
	}

	private static void validateExpressionIsNotBlank(String expression) {
		if (expression == null || expression.isBlank())
			throw new BlankExpressionException();
	}

	private static void validateNumberOfTokens(List<String> tokens) {
		if (tokens.size() < MIN_NUM_TOKENS_OF_ASSIGNMENT_EXPRESSION)
			throw new InvalidNumTokensInAssignmentExpressionException(tokens);
	}

	private static AssignmentAstNode parseAbstractSyntaxTree(List<String> tokens) {
		AbstractSyntaxTreeBuilder astBuilder = new AbstractSyntaxTreeBuilder();
		astBuilder.addAssignedVariable(tokens.get(VARIABLE_TOKEN_INDEX));
		astBuilder.addAssignment(tokens.get(ASSIGNMENT_TOKEN_INDEX));
		tokens.stream().skip(VARIABLE_AND_ASSIGNMENT_TOKENS_SIZE)
				.forEach(astBuilder::addToken);

		return astBuilder.getAbstractSyntaxTreeAssignmentRootNode();
	}

	private static class AbstractSyntaxTreeBuilder {

		private String assignedVariableName;
		private AssignmentOperator assignmentOperator;
		private final Stack<BinaryOperator> binaryOperators;
		private final Stack<AbstractSyntaxTreeNode> expressions;

		AbstractSyntaxTreeBuilder() {
			this.binaryOperators = new Stack<>();
			this.expressions = new Stack<>();
		}

		void addAssignedVariable(String token) {
			if (!isVariable(token))
				throw new MissingAssignedVariableTokenException(token);
			assignedVariableName = token;
		}

		void addAssignment(String token) {
			assignmentOperator = AssignmentOperator.fromToken(token);
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
				BinaryOperator binaryOperator = BinaryOperator.getFromToken(token);
				createExpressionsFromBinaryOperatorsWithEqualOrHigherPrecedence(binaryOperator);
				binaryOperators.push(binaryOperator);
			} else throw new InvalidAbstractSyntaxTreeStructureException(binaryOperators, expressions, token);
		}

		AssignmentAstNode getAbstractSyntaxTreeAssignmentRootNode() {
			AbstractSyntaxTreeNode valueExpression = getValueExpression();

			switch (assignmentOperator) {
				case ASSIGNMENT:
					return new AssignmentAstNode(assignedVariableName, valueExpression);
				case ADDITION_ASSIGNMENT:
					return new AdditionAssignmentAstNode(assignedVariableName, valueExpression);
				case SUBTRACTION_ASSIGNMENT:
					return new SubtractionAssignmentAstNode(assignedVariableName, valueExpression);
				case MULTIPLICATION_ASSIGNMENT:
					return new MultiplicationAssignmentAstNode(assignedVariableName, valueExpression);
				case DIVISION_ASSIGNMENT:
					return new DivisionAssignmentAstNode(assignedVariableName, valueExpression);
				default:
					throw new UnsupportedAssignmentOperatorForAstException(assignmentOperator);
			}
		}

		private void createExpressionsFromBinaryOperatorsWithEqualOrHigherPrecedence(BinaryOperator binaryOperator) {
			while (!binaryOperators.isEmpty() && isOperatorOfLowerPrecedenceThanTopOperator(binaryOperator)) {
				expressions.push(popBinaryOperatorAstNode());
			}
		}

		private boolean isOperatorOfLowerPrecedenceThanTopOperator(BinaryOperator binaryOperator) {
			return binaryOperator.getPrecedence() < binaryOperators.peek().getPrecedence();
		}

		private AbstractSyntaxTreeNode getValueExpression() {
			createExpressions();
			validateFinalValueExpressionIsSingleNode();
			return expressions.pop();
		}

		private void createExpressions() {
			while (!binaryOperators.isEmpty()) {
				expressions.push(popBinaryOperatorAstNode());
			}
		}

		private void validateFinalValueExpressionIsSingleNode() {
			if (expressions.size() != 1)
				throw new InvalidAbstractSyntaxTreeStructureException(binaryOperators, expressions);
		}

		private AbstractSyntaxTreeNode popBinaryOperatorAstNode() {
			AbstractSyntaxTreeNode rightExpression = expressions.pop();
			AbstractSyntaxTreeNode leftExpression = expressions.pop();

			BinaryOperator topBinaryOperator = binaryOperators.pop();
			switch (topBinaryOperator) {
				case ADDITION:
					return new AdditionAstNode(leftExpression, rightExpression);
				case SUBTRACTION:
					return new SubtractionAstNode(leftExpression, rightExpression);
				case MULTIPLICATION:
					return new MultiplicationAstNode(leftExpression, rightExpression);
				case DIVISION:
					return new DivisionAstNode(leftExpression, rightExpression);
				default:
					throw new UnsupportedBinaryOperatorForAstException(topBinaryOperator);
			}
		}
	}
}
