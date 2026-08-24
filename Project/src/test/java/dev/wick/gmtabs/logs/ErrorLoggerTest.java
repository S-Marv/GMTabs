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
			new ErrorTestCase("runtimeError.txt",new RuntimeException("Runtime test")),
			new ErrorTestCase("io.txt", new IOException("IO Test")),
			new ErrorTestCase("inner.txt", new RuntimeException(new FileNotFoundException("inner exception")))
	};


	private final Lock lock = new ReentrantLock();

	@ParameterizedTest
	@FieldSource("TEST_EXCEPTIONS")
	void testLogException(ErrorTestCase testException) throws IOException {
		lock.lock();
		String output;
		String expected = new String(Objects.requireNonNull(ErrorLoggerTest.class.getResourceAsStream(testException.expectedTextFile)).readAllBytes());
		try {
			Path target = Path.of(TestUtils.makeTestDir("errors").getPath(), "errors.txt");
			ErrorLogger logger = new ErrorLogger(target.toFile(), "Test", TIME_PROVIDER);
			logger.logException(testException.e);
			logger.shutdown();
			output = Files.readString(target);
		} finally {
			lock.unlock();
		}
		Assertions.assertEquals(expected, output);
	}

	private static final ErrorTestTimeProvider TIME_PROVIDER = new ErrorTestTimeProvider();
	private static class ErrorTestTimeProvider extends TimeProvider{
		@Override
		public OffsetDateTime getTimeStamp() {
			return OffsetDateTime.of(2000, 1, 1, 1, 1, 1, 0, ZoneOffset.UTC);
		}
	}

	private record ErrorTestCase(String expectedTextFile, Exception e){}
}