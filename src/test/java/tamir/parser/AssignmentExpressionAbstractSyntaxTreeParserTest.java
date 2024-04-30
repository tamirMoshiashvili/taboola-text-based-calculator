package tamir.parser;

import org.junit.jupiter.api.Test;
import tamir.exception.*;
import tamir.parser.assignment.*;
import tamir.parser.ast.AdditionAstNode;
import tamir.parser.ast.IntegerAstNode;
import tamir.parser.ast.MultiplicationAstNode;
import tamir.parser.ast.SubtractionAstNode;

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
		AssignmentRootNode expectedRootNode = new AssignmentRootNode("x", new IntegerAstNode(1));
		assertEquals(expectedRootNode, parseAssignmentExpressionIntoAbstractSyntaxTree("x = 1"));
	}

	@Test
	void whenExpressionIsValidAdditionAssignmentExpression_thenParseExpressionReturnsAdditionAssignmentRootNode() {
		AdditionAssignmentRootNode expectedRootNode = new AdditionAssignmentRootNode("x", new IntegerAstNode(1));
		assertEquals(expectedRootNode, parseAssignmentExpressionIntoAbstractSyntaxTree("x += 1"));
	}

	@Test
	void whenExpressionIsValidSubtractionAssignmentExpression_thenParseExpressionReturnsSubtractionAssignmentRootNode() {
		SubtractionAssignmentRootNode expectedRootNode = new SubtractionAssignmentRootNode("x", new IntegerAstNode(1));
		assertEquals(expectedRootNode, parseAssignmentExpressionIntoAbstractSyntaxTree("x -= 1"));
	}

	@Test
	void whenExpressionIsValidMultiplicationAssignmentExpression_thenParseExpressionReturnsMultiplicationAssignmentRootNode() {
		MultiplicationAssignmentRootNode expectedRootNode = new MultiplicationAssignmentRootNode("x", new IntegerAstNode(1));
		assertEquals(expectedRootNode, parseAssignmentExpressionIntoAbstractSyntaxTree("x *= 1"));
	}

	@Test
	void whenExpressionIsValidDivisionAssignmentExpression_thenParseExpressionReturnsDivisionAssignmentRootNode() {
		DivisionAssignmentRootNode expectedRootNode = new DivisionAssignmentRootNode("x", new IntegerAstNode(1));
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
		AssignmentRootNode expectedRootNode = new AssignmentRootNode("x", valueExpression);

		assertEquals(expectedRootNode, parseAssignmentExpressionIntoAbstractSyntaxTree("x = 1 + 2 * 3"));
	}

	@Test
	void whenExpressionContainsOperatorsWithSamePrecedence_thenParseExpressionFromLeftToRight() {
		SubtractionAstNode left = new SubtractionAstNode(new IntegerAstNode(1), new IntegerAstNode(2));
		IntegerAstNode right = new IntegerAstNode(3);
		SubtractionAstNode valueExpression = new SubtractionAstNode(left, right);
		AssignmentRootNode expectedRootNode = new AssignmentRootNode("x", valueExpression);

		assertEquals(expectedRootNode, parseAssignmentExpressionIntoAbstractSyntaxTree("x = 1 - 2 - 3"));
	}
}