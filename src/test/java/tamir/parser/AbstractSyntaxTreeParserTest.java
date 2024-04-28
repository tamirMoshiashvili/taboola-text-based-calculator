package tamir.parser;

import org.junit.jupiter.api.Test;
import tamir.exception.*;
import tamir.parser.assignment.*;
import tamir.parser.ast.AdditionAstNode;
import tamir.parser.ast.IntegerAstNode;
import tamir.parser.ast.MultiplicationAstNode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static tamir.parser.AbstractSyntaxTreeParser.parseExpressionIntoAbstractSyntaxTree;

class AbstractSyntaxTreeParserTest {

	@Test
	void whenExpressionIsBlank_thenParseExpressionThrowsBlankExpressionException() {
		assertThrows(BlankExpressionException.class, () -> parseExpressionIntoAbstractSyntaxTree(null));
		assertThrows(BlankExpressionException.class, () -> parseExpressionIntoAbstractSyntaxTree(""));
		assertThrows(BlankExpressionException.class, () -> parseExpressionIntoAbstractSyntaxTree(" "));
		assertThrows(BlankExpressionException.class, () -> parseExpressionIntoAbstractSyntaxTree("  "));
		assertThrows(BlankExpressionException.class, () -> parseExpressionIntoAbstractSyntaxTree("\t"));
	}

	@Test
	void whenExpressionContainsLessThanThreeTokens_thenParseExpressionThrowsInvalidNumTokensInAssignmentExpressionException() {
		assertThrows(InvalidNumTokensInAssignmentExpressionException.class,
				() -> parseExpressionIntoAbstractSyntaxTree("x"));
		assertThrows(InvalidNumTokensInAssignmentExpressionException.class,
				() -> parseExpressionIntoAbstractSyntaxTree("x ="));
		assertThrows(InvalidNumTokensInAssignmentExpressionException.class,
				() -> parseExpressionIntoAbstractSyntaxTree("item += "));
		assertThrows(InvalidNumTokensInAssignmentExpressionException.class,
				() -> parseExpressionIntoAbstractSyntaxTree("1 2"));
	}

	@Test
	void whenExpressionIsValidAssignmentExpression_thenParseExpressionReturnsAssignmentRootNode() {
		AssignmentRootNode expectedRootNode = new AssignmentRootNode("x", new IntegerAstNode(1));
		assertEquals(expectedRootNode, parseExpressionIntoAbstractSyntaxTree("x = 1"));
	}

	@Test
	void whenExpressionIsValidAdditionAssignmentExpression_thenParseExpressionReturnsAdditionAssignmentRootNode() {
		AdditionAssignmentRootNode expectedRootNode = new AdditionAssignmentRootNode("x", new IntegerAstNode(1));
		assertEquals(expectedRootNode, parseExpressionIntoAbstractSyntaxTree("x += 1"));
	}

	@Test
	void whenExpressionIsValidSubtractionAssignmentExpression_thenParseExpressionReturnsSubtractionAssignmentRootNode() {
		SubtractionAssignmentRootNode expectedRootNode = new SubtractionAssignmentRootNode("x", new IntegerAstNode(1));
		assertEquals(expectedRootNode, parseExpressionIntoAbstractSyntaxTree("x -= 1"));
	}

	@Test
	void whenExpressionIsValidMultiplicationAssignmentExpression_thenParseExpressionReturnsMultiplicationAssignmentRootNode() {
		MultiplicationAssignmentRootNode expectedRootNode = new MultiplicationAssignmentRootNode("x", new IntegerAstNode(1));
		assertEquals(expectedRootNode, parseExpressionIntoAbstractSyntaxTree("x *= 1"));
	}

	@Test
	void whenExpressionIsValidDivisionAssignmentExpression_thenParseExpressionReturnsDivisionAssignmentRootNode() {
		DivisionAssignmentRootNode expectedRootNode = new DivisionAssignmentRootNode("x", new IntegerAstNode(1));
		assertEquals(expectedRootNode, parseExpressionIntoAbstractSyntaxTree("x /= 1"));
	}

	@Test
	void whenExpressionDoesNotStartsWithVariableToken_thenParseExpressionThrowsMissingAssignedVariableTokenException() {
		assertThrows(MissingAssignedVariableTokenException.class, () -> parseExpressionIntoAbstractSyntaxTree("1 = 2"));
		assertThrows(MissingAssignedVariableTokenException.class, () -> parseExpressionIntoAbstractSyntaxTree("+ = 2"));
		assertThrows(MissingAssignedVariableTokenException.class, () -> parseExpressionIntoAbstractSyntaxTree("* /= +"));
	}

	@Test
	void whenExpressionDoesNotContainAssignmentOperator_thenParseExpressionThrowsInvalidAssignmentOperatorException() {
		assertThrows(InvalidAssignmentOperatorException.class, () -> parseExpressionIntoAbstractSyntaxTree("x + 1"));
		assertThrows(InvalidAssignmentOperatorException.class, () -> parseExpressionIntoAbstractSyntaxTree("x == 2"));
		assertThrows(InvalidAssignmentOperatorException.class, () -> parseExpressionIntoAbstractSyntaxTree("item - 12"));
		assertThrows(InvalidAssignmentOperatorException.class, () -> parseExpressionIntoAbstractSyntaxTree("item item2 1"));
	}

	@Test
	void whenExpressionContainsMoreThanOneAssignment_thenParseExpressionThrowsInvalidAbstractSyntaxTreeStructureException() {
		assertThrows(InvalidAbstractSyntaxTreeStructureException.class,
				() -> parseExpressionIntoAbstractSyntaxTree("x = = 1"));
		assertThrows(InvalidAbstractSyntaxTreeStructureException.class,
				() -> parseExpressionIntoAbstractSyntaxTree("x += 1 = y = 50"));
		assertThrows(InvalidAbstractSyntaxTreeStructureException.class,
				() -> parseExpressionIntoAbstractSyntaxTree("x /= 3 *= 2"));
		assertThrows(InvalidAbstractSyntaxTreeStructureException.class,
				() -> parseExpressionIntoAbstractSyntaxTree("x = y = z = w = h = T"));
	}

	@Test
	void whenOperatorIsMissingAnOperand_thenParseExpressionThrowsInvalidAbstractSyntaxTreeStructureException() {
		assertThrows(InvalidAbstractSyntaxTreeStructureException.class,
				() -> parseExpressionIntoAbstractSyntaxTree("x = 1 +"));
		assertThrows(InvalidAbstractSyntaxTreeStructureException.class,
				() -> parseExpressionIntoAbstractSyntaxTree("x = 1 * 2 / "));
		assertThrows(InvalidAbstractSyntaxTreeStructureException.class,
				() -> parseExpressionIntoAbstractSyntaxTree("item += x y"));
		assertThrows(InvalidAbstractSyntaxTreeStructureException.class,
				() -> parseExpressionIntoAbstractSyntaxTree("x = 1 t 2"));
		assertThrows(InvalidAbstractSyntaxTreeStructureException.class,
				() -> parseExpressionIntoAbstractSyntaxTree("x = a 1 t"));
	}

	@Test
	void whenExpressionContainsOperatorsWithDifferentPrecedence_thenParseExpressionUseHigherPrecedenceFirst() {
		IntegerAstNode left = new IntegerAstNode(1);
		MultiplicationAstNode right = new MultiplicationAstNode(new IntegerAstNode(2), new IntegerAstNode(3));
		AdditionAstNode valueExpression = new AdditionAstNode(left, right);
		AssignmentRootNode expectedRootNode = new AssignmentRootNode("x", valueExpression);

		assertEquals(expectedRootNode, parseExpressionIntoAbstractSyntaxTree("x = 1 + 2 * 3"));
	}
}