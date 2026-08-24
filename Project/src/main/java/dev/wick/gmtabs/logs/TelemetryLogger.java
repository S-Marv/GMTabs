package dev.wick.gmtabs.logs;

public interface TelemetryLogger {

	void logEventAsync(EventType eventType, String... data);

	void shutdown();
}
