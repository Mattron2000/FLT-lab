import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.stream.Stream;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import ast.NodePrg;
import parser.Parser;
import parser.SyntacticException;
import scanner.Scanner;

class TestParser {
	private final String RootFolderOfJavaProject = System.getProperty("user.dir") + "/src/test/java/data/testParser/";

	@TestFactory
	public Stream<DynamicTest> generateDynamicTests() {
		String fileDynamicTestFilePath = RootFolderOfJavaProject +
				"dynamic_tests.txt";
		ArrayList<DynamicTest> dynamicTests = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new FileReader(fileDynamicTestFilePath))) {
			for (String line = br.readLine(); line != null; line = br.readLine()) {
				String[] values = line.split(",,");

				assertEquals(3, values.length);

				String fileName = values[0].trim();
				String testName = values[1].trim();
				String expectedExceptionMessage = values[2].trim();

				dynamicTests.add(dynamicTest(testName, () -> {
					Scanner s = new Scanner(RootFolderOfJavaProject + fileName);
					Parser p = new Parser(s);
					NodePrg nodePrg = null;

					if (!expectedExceptionMessage.equals("null")) {
						Throwable e = assertThrows(SyntacticException.class, () -> p.parse());
						assertEquals(expectedExceptionMessage, e.getMessage());
						return;
					} else
						nodePrg = p.parse();

					System.out.println(nodePrg.toString());
				}));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return dynamicTests.stream();
	}

	// @Test
	// public void testSoloDclPrint1() throws SyntacticException, LexicalException,
	// IOException {
	// Scanner s = new Scanner(RootFolderOfJavaProject + "testSoloDichPrint1.txt");
	// Parser p = new Parser(s);

	// NodePrg nodePrg = p.parse();

	// System.out.println(nodePrg.toString());
	// }

	// @Test
	// public void testSoloDclPrint2() throws SyntacticException, LexicalException,
	// IOException {
	// Scanner s = new Scanner(RootFolderOfJavaProject + "testSoloDichPrint2.txt");
	// Parser p = new Parser(s);

	// NodePrg nodePrg = p.parse();

	// System.out.println(nodePrg.toString());
	// }

	// @Test
	// public void testCorretto1() throws SyntacticException, LexicalException,
	// IOException {
	// Scanner s = new Scanner(RootFolderOfJavaProject + "testParserCorretto1.txt");
	// Parser p = new Parser(s);

	// NodePrg nodePrg = p.parse();

	// System.out.println(nodePrg.toString());
	// }

	// @Test
	// public void testCorretto2() throws SyntacticException, LexicalException,
	// IOException {
	// Scanner s = new Scanner(RootFolderOfJavaProject + "testParserCorretto2.txt");
	// Parser p = new Parser(s);

	// NodePrg nodePrg = p.parse();

	// System.out.println(nodePrg.toString());
	// }
}
