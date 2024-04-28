package tamir.parser.assignment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tamir.calculator.CalculatorContext;
import tamir.exception.UnknownVariableException;
import tamir.parser.ast.IntegerAstNode;
import tamir.parser.ast.VariableAstNode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DivisionAssignmentAstNodeTest {

	private IntegerAstNode integer;
	private VariableAstNode variableX;
	private CalculatorContext context;

	@BeforeEach
	void setup() {
		integer = new IntegerAstNode(2);
		variableX = new VariableAstNode("x");
		context = new CalculatorContext();
		context.put("x", 4);
	}

	@Test
	void whenDivisionAssigningExistingVariable_thenVariableIsBeingUpdatedInTheContext() {
		new DivisionAssignmentAstNode("x", integer).execute(context);
		assertEquals(2, variableX.interpret(context));
	}

	@Test
	void whenDivisionAssigningOfUnknownVariable_thenInterpretThrowsUnknownVariableException() {
		assertThrows(UnknownVariableException.class,
				() -> new DivisionAssignmentAstNode("unknown", integer).execute(context));
	}
}