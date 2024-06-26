package tamir.parser;

import org.junit.jupiter.api.Test;
import tamir.exception.*;
import tamir.parser.ast.IntegerAstNode;
import tamir.parser.ast.assignment.*;
import tamir.parser.ast.operator.binary.AdditionAstNode;
import tamir.parser.ast.operator.binary.MultiplicationAstNode;
import tamir.parser.ast.operator.binary.SubtractionAstNode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static tamir.parser.AssignmentExpressionAbstractSyntaxTreeParser.parseAssignmentExpressionIntoAbstractSyntaxTree;

class AssignmentExpressionAbstractSyntaxTreeParserTest {

	@Test
	void whenExpressionIsBlank_thenParseExpressionThrowsBlankExpressionException() {
		assertThrows(BlankExpressionException.class, () -> parseAssignmentExpressionIntoAbstractSyntaxTree(null));
		assertThrows(BlankExpressionException.class, () -> parseAssignmentExpressionIntoAbstractSyntaxTree(""));
		assertThrows(BlankExpressionException.class, () -> parseAssignmentExpressionIntoAbstractSyntaxTree(" "));
		assertThrows(BlankExpressionException.class, () -> parseAssignmentExpressionIntoAbstractSyntaxTree("  "));
		assertThrows(BlankExpressionException.class, () -> parseAssignmentExpressionIntoAbstractSyntaxTree("\t"));
	}

	@Test
	void whenExpressionContainsLessThanThreeTokens_thenParseExpressionThrowsInvalidNumTokensInAssignmentExpressionException() {
		assertThrows(InvalidNumTokensInAssignmentExpressionException.class,
				() -> parseAssignmentExpressionIntoAbstractSyntaxTree("x"));
		assertThrows(InvalidNumTokensInAssignmentExpressionException.class,
				() -> parseAssignmentExpressionIntoAbstractSyntaxTree("x ="));
		assertThrows(InvalidNumTokensInAssignmentExpressionException.class,
				() -> parseAssignmentExpressionIntoAbstractSyntaxTree("item += "));
		assertThrows(InvalidNumTokensInAssignmentExpressionException.class,
				() -> parseAssignmentExpressionIntoAbstractSyntaxTree("1 2"));
	}

	@Test
	void whenExpressionIsValidAssignmentExpression_thenParseExpressionReturnsAssignmentRootNode() {
		AssignmentAstNode expectedRootNode = new AssignmentAstNode("x", new IntegerAstNode(1));
		assertEquals(expectedRootNode, parseAssignmentExpressionIntoAbstractSyntaxTree("x = 1"));
	}

	@Test
	void whenExpressionIsValidAdditionAssignmentExpression_thenParseExpressionReturnsAdditionAssignmentRootNode() {
		AdditionAssignmentAstNode expectedRootNode = new AdditionAssignmentAstNode("x", new IntegerAstNode(1));
		assertEquals(expectedRootNode, parseAssignmentExpressionIntoAbstractSyntaxTree("x += 1"));
	}

	@Test
	void whenExpressionIsValidSubtractionAssignmentExpression_thenParseExpressionReturnsSubtractionAssignmentRootNode() {
		SubtractionAssignmentAstNode expectedRootNode = new SubtractionAssignmentAstNode("x", new IntegerAstNode(1));
		assertEquals(expectedRootNode, parseAssignmentExpressionIntoAbstractSyntaxTree("x -= 1"));
	}

	@Test
	void whenExpressionIsValidMultiplicationAssignmentExpression_thenParseExpressionReturnsMultiplicationAssignmentRootNode() {
		MultiplicationAssignmentAstNode expectedRootNode = new MultiplicationAssignmentAstNode("x", new IntegerAstNode(1));
		assertEquals(expectedRootNode, parseAssignmentExpressionIntoAbstractSyntaxTree("x *= 1"));
	}

	@Test
	void whenExpressionIsValidDivisionAssignmentExpression_thenParseExpressionReturnsDivisionAssignmentRootNode() {
		DivisionAssignmentAstNode expectedRootNode = new DivisionAssignmentAstNode("x", new IntegerAstNode(1));
		assertEquals(expectedRootNode, parseAssignmentExpressionIntoAbstractSyntaxTree("x /= 1"));
	}

	@Test
	void whenExpressionDoesNotStartsWithVariableToken_thenParseExpressionThrowsMissingAssignedVariableTokenException() {
		assertThrows(MissingAssignedVariableTokenException.class, () -> parseAssignmentExpressionIntoAbstractSyntaxTree("1 = 2"));
		assertThrows(MissingAssignedVariableTokenException.class, () -> parseAssignmentExpressionIntoAbstractSyntaxTree("+ = 2"));
		assertThrows(MissingAssignedVariableTokenException.class, () -> parseAssignmentExpressionIntoAbstractSyntaxTree("* /= +"));
	}

	@Test
	void whenExpressionDoesNotContainAssignmentOperator_thenParseExpressionThrowsInvalidAssignmentOperatorException() {
		assertThrows(InvalidAssignmentOperatorException.class, () -> parseAssignmentExpressionIntoAbstractSyntaxTree("x + 1"));
		assertThrows(InvalidAssignmentOperatorException.class, () -> parseAssignmentExpressionIntoAbstractSyntaxTree("x == 2"));
		assertThrows(InvalidAssignmentOperatorException.class, () -> parseAssignmentExpressionIntoAbstractSyntaxTree("item - 12"));
		assertThrows(InvalidAssignmentOperatorException.class, () -> parseAssignmentExpressionIntoAbstractSyntaxTree("item item2 1"));
	}

	@Test
	void whenExpressionContainsMoreThanOneAssignment_thenParseExpressionThrowsInvalidAbstractSyntaxTreeStructureException() {
		assertThrows(InvalidAbstractSyntaxTreeStructureException.class,
				() -> parseAssignmentExpressionIntoAbstractSyntaxTree("x = = 1"));
		assertThrows(InvalidAbstractSyntaxTreeStructureException.class,
				() -> parseAssignmentExpressionIntoAbstractSyntaxTree("x += 1 = y = 50"));
		assertThrows(InvalidAbstractSyntaxTreeStructureException.class,
				() -> parseAssignmentExpressionIntoAbstractSyntaxTree("x /= 3 *= 2"));
		assertThrows(InvalidAbstractSyntaxTreeStructureException.class,
				() -> parseAssignmentExpressionIntoAbstractSyntaxTree("x = y = z = w = h = T"));
	}

	@Test
	void whenOperatorIsMissingAnOperand_thenParseExpressionThrowsInvalidAbstractSyntaxTreeStructureException() {
		assertThrows(InvalidAbstractSyntaxTreeStructureException.class,
				() -> parseAssignmentExpressionIntoAbstractSyntaxTree("x = *"));
		assertThrows(InvalidAbstractSyntaxTreeStructureException.class,
				() -> parseAssignmentExpressionIntoAbstractSyntaxTree("x = 1 +"));
		assertThrows(InvalidAbstractSyntaxTreeStructureException.class,
				() -> parseAssignmentExpressionIntoAbstractSyntaxTree("x = 1 * 2 / "));
	}

	@Test
	void whenValueExpressionContainsMultipleOperandsWithoutOperator_thenParseExpressionThrowsInvalidAbstractSyntaxTreeStructureException() {
		assertThrows(InvalidAbstractSyntaxTreeStructureException.class,
				() -> parseAssignmentExpressionIntoAbstractSyntaxTree("item += x y"));
		assertThrows(InvalidAbstractSyntaxTreeStructureException.class,
				() -> parseAssignmentExpressionIntoAbstractSyntaxTree("x = 1 t 2"));
		assertThrows(InvalidAbstractSyntaxTreeStructureException.class,
				() -> parseAssignmentExpressionIntoAbstractSyntaxTree("x = a 1 t"));
		assertThrows(InvalidAbstractSyntaxTreeStructureException.class,
				() -> parseAssignmentExpressionIntoAbstractSyntaxTree("x = 1 1 1"));
	}

	@Test
	void whenExpressionContainsOperatorsWithDifferentPrecedence_thenParseExpressionUseHigherPrecedenceFirst() {
		IntegerAstNode left = new IntegerAstNode(1);
		MultiplicationAstNode right = new MultiplicationAstNode(new IntegerAstNode(2), new IntegerAstNode(3));
		AdditionAstNode valueExpression = new AdditionAstNode(left, right);
		AssignmentAstNode expectedRootNode = new AssignmentAstNode("x", valueExpression);

		assertEquals(expectedRootNode, parseAssignmentExpressionIntoAbstractSyntaxTree("x = 1 + 2 * 3"));
	}

	@Test
	void whenExpressionContainsOperatorsWithSamePrecedence_thenParseExpressionFromLeftToRight() {
		SubtractionAstNode left = new SubtractionAstNode(new IntegerAstNode(1), new IntegerAstNode(2));
		IntegerAstNode right = new IntegerAstNode(3);
		SubtractionAstNode valueExpression = new SubtractionAstNode(left, right);
		AssignmentAstNode expectedRootNode = new AssignmentAstNode("x", valueExpression);

		assertEquals(expectedRootNode, parseAssignmentExpressionIntoAbstractSyntaxTree("x = 1 - 2 - 3"));
	}
}