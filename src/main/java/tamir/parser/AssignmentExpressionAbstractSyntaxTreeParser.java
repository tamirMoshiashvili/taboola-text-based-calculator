package tamir.parser;

import tamir.exception.*;
import tamir.parser.ast.AbstractSyntaxTreeNode;
import tamir.parser.ast.IntegerAstNode;
import tamir.parser.ast.VariableAstNode;
import tamir.parser.ast.assignment.*;
import tamir.parser.ast.operator.binary.AdditionAstNode;
import tamir.parser.ast.operator.binary.DivisionAstNode;
import tamir.parser.ast.operator.binary.MultiplicationAstNode;
import tamir.parser.ast.operator.binary.SubtractionAstNode;
import tamir.parser.ast.operator.unary.PostDecrementAstNode;
import tamir.parser.ast.operator.unary.PostIncrementAstNode;
import tamir.parser.ast.operator.unary.PreDecrementAstNode;
import tamir.parser.ast.operator.unary.PreIncrementAstNode;
import tamir.parser.operator.AssignmentOperator;
import tamir.parser.operator.BinaryOperator;

import java.util.List;
import java.util.Stack;

import static java.lang.Integer.parseInt;
import static tamir.parser.ExpressionTokenizer.*;
import static tamir.parser.operator.BinaryOperator.isBinaryOperator;

public class AssignmentExpressionAbstractSyntaxTreeParser {

	private static final int VARIABLE_TOKEN_INDEX = 0;
	private static final int ASSIGNMENT_TOKEN_INDEX = 1;
	private static final long VARIABLE_AND_ASSIGNMENT_TOKENS_SIZE = 2;
	private static final int MIN_NUM_TOKENS_OF_ASSIGNMENT_EXPRESSION = 3;

	public static AssignmentRootNode parseAssignmentExpressionIntoAbstractSyntaxTree(String expression) {
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

	private static AssignmentRootNode parseAbstractSyntaxTree(List<String> tokens) {
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
		private final Stack<AbstractSyntaxTreeNode<Integer>> expressions;

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
				BinaryOperator binaryOperator = BinaryOperator.fromToken(token);
				createExpressionsFromBinaryOperatorsWithHigherPrecedence(binaryOperator);
				binaryOperators.push(binaryOperator);
			} else throw new InvalidAbstractSyntaxTreeStructureException(binaryOperators, expressions, token);
		}

		AssignmentRootNode getAbstractSyntaxTreeAssignmentRootNode() {
			AbstractSyntaxTreeNode<Integer> valueExpression = getValueExpression();

			switch (assignmentOperator) {
				case ASSIGNMENT:
					return new AssignmentRootNode(assignedVariableName, valueExpression);
				case ADDITION_ASSIGNMENT:
					return new AdditionAssignmentRootNode(assignedVariableName, valueExpression);
				case SUBTRACTION_ASSIGNMENT:
					return new SubtractionAssignmentRootNode(assignedVariableName, valueExpression);
				case MULTIPLICATION_ASSIGNMENT:
					return new MultiplicationAssignmentRootNode(assignedVariableName, valueExpression);
				case DIVISION_ASSIGNMENT:
					return new DivisionAssignmentRootNode(assignedVariableName, valueExpression);
				default:
					throw new UnsupportedAssignmentOperatorForAstException(assignmentOperator);
			}
		}

		private void createExpressionsFromBinaryOperatorsWithHigherPrecedence(BinaryOperator binaryOperator) {
			while (!binaryOperators.isEmpty() && isOperatorOfLowerOrEqualPrecedenceThanTopOperator(binaryOperator)) {
				expressions.push(popBinaryOperatorAstNode());
			}
		}

		private boolean isOperatorOfLowerOrEqualPrecedenceThanTopOperator(BinaryOperator binaryOperator) {
			return binaryOperator.getPrecedence() <= binaryOperators.peek().getPrecedence();
		}

		private AbstractSyntaxTreeNode<Integer> getValueExpression() {
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

		private AbstractSyntaxTreeNode<Integer> popBinaryOperatorAstNode() {
			validateBinaryOperatorHasTwoExpressionsToPop();
			AbstractSyntaxTreeNode<Integer> rightExpression = expressions.pop();
			AbstractSyntaxTreeNode<Integer> leftExpression = expressions.pop();

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

		private void validateBinaryOperatorHasTwoExpressionsToPop() {
			if (expressions.size() < 2)
				throw new InvalidAbstractSyntaxTreeStructureException(binaryOperators, expressions);
		}
	}
}
