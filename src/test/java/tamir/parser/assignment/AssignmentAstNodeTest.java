package tamir.parser.assignment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tamir.calculator.CalculatorContext;
import tamir.parser.ast.IntegerAstNode;
import tamir.parser.ast.VariableAstNode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AssignmentAstNodeTest {

	private IntegerAstNode integer;
	private VariableAstNode variableX;
	private CalculatorContext context;

	@BeforeEach
	void setup() {
		integer = new IntegerAstNode(1);
		variableX = new VariableAstNode("x");
		context = new CalculatorContext();
		context.put("x", 3);
	}

	@Test
	void whenAssigningNewVariable_thenVariableIsBeingAddedToTheContext() {
		CalculatorContext context = new CalculatorContext();
		assertTrue(context.getVariableToValue().isEmpty());
		new AssignmentAstNode("x", integer).execute(context);
		assertEquals(integer.interpret(context), variableX.interpret(context));
	}

	@Test
	void whenAssigningExistingVariable_thenVariableIsBeingUpdatedInTheContext() {
		new AssignmentAstNode("x", integer).execute(context);
		assertEquals(integer.interpret(context), variableX.interpret(context));
	}

	@Test
	void whenAssigningVariable_thenInterpretDoesNotChangeOtherVariablesInTheContext() {
		VariableAstNode newVariable = new VariableAstNode("new");
		new AssignmentAstNode("new", integer).execute(context);
		int value = newVariable.interpret(context);
		new AssignmentAstNode("x", integer).execute(context);
		assertEquals(value, newVariable.interpret(context));
	}
}