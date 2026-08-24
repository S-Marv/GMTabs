package dev.wick.gmtabs.logs;

import java.io.File;

public class Logs {
	private static TelemetryLogger telemetryLogger = new EmptyLogger();
	private static final File LOG_DIRECTORY = new File("./telemetry/");

	public static void initTelemetry(){
		telemetryLogger = new FileTelemetryLogger(LOG_DIRECTORY, null);
	}

	public static TelemetryLogger telemetryLogger() {
		return telemetryLogger;
	}


	private static class EmptyLogger implements TelemetryLogger{
		@Override
		public void logEventAsync(EventType eventType, String... data) {

		}

		@Override
		public void shutdown() {

		}
	}
}
