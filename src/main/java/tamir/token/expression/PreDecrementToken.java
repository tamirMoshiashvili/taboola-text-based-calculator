package tamir.token.expression;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class PreDecrementToken implements UnaryOperatorToken {

	private final String variableName;
}
