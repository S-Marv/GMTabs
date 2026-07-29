package dev.wick.gmtabs;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class HelloApplicationTest {

	@ParameterizedTest
	@CsvSource({"1 ,1", "1.0.1,1.0.1", " 2.20.4,2.20.4"})
	void testGetVersion(String input, String expected){
		Assertions.assertEquals(expected, input);
	}

	@ParameterizedTest
	@CsvSource({"evil.exe", "1.0.t"})
	void testGetVersion_wrong(String input){
		Assertions.assertThrows(RuntimeException.class, ()-> HelloApplication.getVersion(input));

	}

}