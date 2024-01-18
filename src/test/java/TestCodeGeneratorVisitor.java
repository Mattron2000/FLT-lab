import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.stream.Stream;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

class CodeGenerationVisitor {
	private final String RootFolderOfJavaProject = System.getProperty("user.dir") + "/src/test/java/data/testCodeGenerationVisitor/";

	@TestFactory
	public Stream<DynamicTest> generateDynamicTests() {
		String fileDynamicTestFilePath = RootFolderOfJavaProject + "dynamic_tests.txt";
		ArrayList<DynamicTest> dynamicTests = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new FileReader(fileDynamicTestFilePath))) {
			for (String line = br.readLine(); line != null; line = br.readLine()) {
				String[] values = line.split(",,");

				assertEquals(3, values.length);

				String fileName = values[0].trim();
				String testName = values[1].trim();
				String expectedExceptionMessage = values[2].trim();

				dynamicTests.add(dynamicTest(testName, () -> {
					// TODO fare i test qui
				}));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return dynamicTests.stream();
	}
}
