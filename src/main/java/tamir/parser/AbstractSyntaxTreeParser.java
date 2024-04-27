package tamir.parser;

import tamir.exception.InvalidAbstractSyntaxTreeStructureException;
import tamir.token.expression.Token;

import java.util.List;
import java.util.Stack;

public class AbstractSyntaxTreeParser {

	public static AbstractSyntaxTreeNode parseTokensIntoAbstractSyntaxTree(List<Token> tokens) {
		AbstractSyntaxTreeBuilder astBuilder = new AbstractSyntaxTreeBuilder();
		tokens.forEach(astBuilder::addToken);

		return astBuilder.getAbstractSyntaxTreeRootNode();
	}

	private static class AbstractSyntaxTreeBuilder {

		private final Stack<Token> operators;
		private final Stack<AbstractSyntaxTreeNode> expressions;

		AbstractSyntaxTreeBuilder() {
			this.operators = new Stack<>();
			this.expressions = new Stack<>();
		}

		void addToken(Token token) {
			if (token.isVariable()) expressions.push(new VariableAstNode(token));
			else if (token.isOperand()) expressions.push(new IntegerAstNode(token));
			else if (token.isUnaryOperator()) expressions.push(new UnaryOperatorAstNode(token));
			else if (token.isBinaryOperator()) {
				while (!operators.isEmpty() && isOperatorTokenOfLowerPrecedenceThanTopOperator(token)) {
					expressions.push(popBinaryOperatorAstNode());
				}
				operators.push(token);
			} else throw new InvalidAbstractSyntaxTreeStructureException(operators, expressions, token);
		}

		AbstractSyntaxTreeNode getAbstractSyntaxTreeRootNode() {
			if (expressions.size() != 1)
				throw new InvalidAbstractSyntaxTreeStructureException(operators, expressions);
			return expressions.pop();
		}

		private boolean isOperatorTokenOfLowerPrecedenceThanTopOperator(Token token) {
			return operators.peek().getPrecedence() >= token.getPrecedence();
		}

		private BinaryOperatorAstNode popBinaryOperatorAstNode() {
			Token topOperator = operators.pop();
			AbstractSyntaxTreeNode rightExpression = expressions.pop();
			AbstractSyntaxTreeNode leftExpression = expressions.pop();

			return new BinaryOperatorAstNode(leftExpression, topOperator, rightExpression);
		}
	}
}
