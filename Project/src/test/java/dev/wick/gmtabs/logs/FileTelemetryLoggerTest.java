package dev.wick.gmtabs.logs;

import dev.wick.gmtabs.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Paths;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.*;

class FileTelemetryLoggerTest {
	private static final File LOG_FOLDER = TestUtils.makeTestDir("telemetry");
	private static final List<OffsetDateTime> TEST_DATES = makeTestDates();
	private static List<OffsetDateTime> makeTestDates() {
		OffsetDateTime base = OffsetDateTime.of(2026, 8, 5, 5, 30, 0, 0, ZoneOffset.UTC);
		List<OffsetDateTime> list = new ArrayList<>();
		list.add(base);
		for(TemporalUnit unit : List.of(ChronoUnit.SECONDS, ChronoUnit.MINUTES, ChronoUnit.HOURS,
				ChronoUnit.DAYS, ChronoUnit.MONTHS, ChronoUnit.YEARS)){
			list.add(base.plus(1, unit));
		}
		return list;
	}

	@Test
	void logEvent() {

		//TelemetryLogger logger = new FileTelemetryLogger();

	}


	@Test
	void testGetFile(){
		TestTimeProvider timeProvider = new TestTimeProvider();
		String actual = FileTelemetryLogger.getFile(LOG_FOLDER, timeProvider).getPath();
		String expected = Paths.get(LOG_FOLDER.getPath(), "L2026-08-05_01.30.00.000.tsv").toString();
		Assertions.assertEquals(expected, actual);
	}

	@Test
	void testFileNamingClashes(){
		TestTimeProvider timeProvider = new TestTimeProvider();
		LinkedList<String> names = new LinkedList<>();
		while (timeProvider.hasNextDate()){
			names.add(FileTelemetryLogger.getFile(LOG_FOLDER, timeProvider).getName());
		}
		List<NameClash> clashes = new ArrayList<>();
		int index = 0;
		while(names.size()!=1){
			String testString = names.removeFirst();
			int clashIndex = names.indexOf(testString);
			if (clashIndex!=-1) clashes.add(new NameClash(testString, index++, index+clashIndex));
		}
		Assertions.assertTrue(clashes.isEmpty(), "Name clashes found: " + clashes.stream().map(NameClash::toString).toList());
	}


	private record NameClash(String name, int firstIndex, int secondIndex){}

	private static class TestTimeProvider extends TimeProvider{
		private int testIndex = 0;

		@Override
		public OffsetDateTime getTimeStamp() {
			return TEST_DATES.get(testIndex++) ;
		}

		boolean hasNextDate(){
			return testIndex < TEST_DATES.size();
		}
	}
}