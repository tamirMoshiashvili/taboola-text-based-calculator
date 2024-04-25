package tamir.calculator;

import tamir.token.Token;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static tamir.token.Tokenizer.parseTokens;

public class TextBasedCalculator {

	public static Map<String, Integer> runTextBasedCalculator(Scanner scanner) {
		System.out.println("Enter expressions line by line, enter blank line for exit");
		Map<String, Integer> variableToValues = new HashMap<>();
		String line;
		while (!(line = scanner.nextLine()).isBlank()) {
			List<Token> tokens = parseTokens(line);
		}

		return variableToValues;
	}
}
