package tamir.parser.ast.assignment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tamir.calculator.CalculatorContext;
import tamir.parser.ast.IntegerAstNode;
import tamir.parser.ast.VariableAstNode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AssignmentAstNodeTest {

	private IntegerAstNode one;
	private VariableAstNode variableX;
	private CalculatorContext context;

	@BeforeEach
	void setup() {
		one = new IntegerAstNode(1);
		variableX = new VariableAstNode("x");
		context = new CalculatorContext();
		context.put("x", 3);
	}

	@Test
	void whenAssigningNewVariable_thenVariableIsBeingAddedToTheContext() {
		CalculatorContext context = new CalculatorContext();
		assertTrue(context.getVariableToValue().isEmpty());
		new AssignmentAstNode("x", one).interpret(context);
		assertEquals(one.interpret(context), variableX.interpret(context));
	}

	@Test
	void whenAssigningExistingVariable_thenVariableValueIsBeingUpdatedInTheContext() {
		assertTrue(context.getVariableToValue().containsKey("x"));
		new AssignmentAstNode("x", one).interpret(context);
		assertEquals(one.interpret(context), variableX.interpret(context));
	}

	@Test
	void whenAssigningVariable_thenInterpretDoesNotChangeOtherVariablesInTheContext() {
		int xValueBeforeOperation = variableX.interpret(context);
		new AssignmentAstNode("new", one).interpret(context);
		assertEquals(xValueBeforeOperation, variableX.interpret(context));
	}
}