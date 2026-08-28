package dev.wick.gmtabs.logs;

import dev.wick.gmtabs.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.FieldSource;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class ErrorLoggerTest {

	private static final ErrorTestCase[] TEST_EXCEPTIONS = new ErrorTestCase[]{
			new ErrorTestCase(new RuntimeException("Runtime test"), "runtimeError.txt"),
			new ErrorTestCase(new IOException("IO Test"), "io.txt"),
			new ErrorTestCase(new RuntimeException(new FileNotFoundException("inner exception")), "inner.txt", "inner2.txt")
	};


	private final Lock lock = new ReentrantLock();

	@ParameterizedTest
	@FieldSource("TEST_EXCEPTIONS")
	void testLogException(ErrorTestCase testException) throws IOException {
		lock.lock();
		String output;
		try {
			Path target = Path.of(TestUtils.makeTestDir("errors").getPath(), "errors.txt");
			ErrorLogger logger = new ErrorLogger(target.toFile(), "Test", TIME_PROVIDER);
			logger.logException(testException.e);
			logger.shutdown();
			output = Files.readString(target);
		} finally {
			lock.unlock();
		}
		for(String expectedTextFile : testException.expectedTextFiles()){
			InputStream fileStream = Objects.requireNonNull(ErrorLoggerTest.class.getResourceAsStream(expectedTextFile));
			String expectedText = new String(fileStream.readAllBytes()).strip();
			Assertions.assertTrue(output.contains(expectedText),
					String.format("Output does not contain expected text from %s \nExpected: %s \nOutput: %s", expectedTextFile, expectedText, output));
		}
	}

	private static final ErrorTestTimeProvider TIME_PROVIDER = new ErrorTestTimeProvider();
	private static class ErrorTestTimeProvider extends TimeProvider{
		@Override
		public OffsetDateTime getTimeStamp() {
			return OffsetDateTime.of(2000, 1, 1, 1, 1, 1, 0, ZoneOffset.UTC);
		}
	}

	private record ErrorTestCase(Exception e, String... expectedTextFiles){}
}