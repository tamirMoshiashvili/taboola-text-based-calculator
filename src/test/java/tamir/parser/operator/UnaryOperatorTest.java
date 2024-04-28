package tamir.parser.operator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tamir.parser.operator.UnaryOperator.getUnaryOperatorLength;

class UnaryOperatorTest {

	@Test
	void testUnaryOperatorLengthEqualsTwo() {
		assertEquals(2, getUnaryOperatorLength());
	}
}