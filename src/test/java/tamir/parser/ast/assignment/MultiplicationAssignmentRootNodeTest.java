package tamir.parser.ast.assignment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tamir.calculator.CalculatorContext;
import tamir.exception.UnknownVariableException;
import tamir.parser.ast.IntegerAstNode;
import tamir.parser.ast.VariableAstNode;

import static org.junit.jupiter.api.Assertions.*;

class MultiplicationAssignmentRootNodeTest {

	private IntegerAstNode five;
	private VariableAstNode variableX;
	private CalculatorContext context;

	@BeforeEach
	void setup() {
		five = new IntegerAstNode(5);
		variableX = new VariableAstNode("x");
		context = new CalculatorContext();
		context.put("x", 3);
	}

	@Test
	void whenMultiplicationAssigningExistingVariable_thenVariableValueIsBeingUpdatedInTheContext() {
		new MultiplicationAssignmentRootNode("x", five).interpret(context);
		assertEquals(15, variableX.interpret(context));
	}

	@Test
	void whenMultiplicationAssigningOfUnknownVariable_thenInterpretThrowsUnknownVariableException() {
		assertThrows(UnknownVariableException.class,
				() -> new MultiplicationAssignmentRootNode("unknown", five).interpret(context));
	}

	@Test
	void whenMultiplicationAssigningByOne_thenVariableValueDoesNotChange() {
		int xValueBeforeChange = variableX.interpret(context);
		new MultiplicationAssignmentRootNode("x", new IntegerAstNode(1)).interpret(context);
		assertEquals(xValueBeforeChange, variableX.interpret(context));
	}

	@Test
	void whenMultiplicationAssigningByZero_thenVariableIsSetToZero() {
		new MultiplicationAssignmentRootNode("x", new IntegerAstNode(0)).interpret(context);
		assertEquals(0, variableX.interpret(context));
	}

	@Test
	void whenMultiplicationAssigningPositiveVariableByNegativeInteger_thenVariableValueIsNegative() {
		assertTrue(variableX.interpret(context) > 0);
		IntegerAstNode negativeInteger = new IntegerAstNode(-1);
		new MultiplicationAssignmentRootNode("x", negativeInteger).interpret(context);
		assertTrue(variableX.interpret(context) < 0);
	}

	@Test
	void whenMultiplicationAssigningNegativeVariableByNegativeInteger_thenVariableValueIsPositive() {
		context.put("y", -1);
		IntegerAstNode negativeInteger = new IntegerAstNode(-1);
		new MultiplicationAssignmentRootNode("y", negativeInteger).interpret(context);
		assertTrue(variableX.interpret(context) > 0);
	}
}