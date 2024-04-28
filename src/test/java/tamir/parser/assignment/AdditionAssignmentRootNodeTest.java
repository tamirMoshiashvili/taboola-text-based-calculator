package tamir.parser.assignment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tamir.calculator.CalculatorContext;
import tamir.exception.UnknownVariableException;
import tamir.parser.ast.IntegerAstNode;
import tamir.parser.ast.VariableAstNode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AdditionAssignmentRootNodeTest {

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
	void whenAdditionAssigningExistingVariable_thenVariableIsBeingUpdatedInTheContext() {
		new AdditionAssignmentRootNode("x", integer).execute(context);
		assertEquals(4, variableX.interpret(context));
	}

	@Test
	void whenAdditionAssigningOfUnknownVariable_thenInterpretThrowsUnknownVariableException() {
		assertThrows(UnknownVariableException.class,
				() -> new AdditionAssignmentRootNode("unknown", integer).execute(context));
	}
}