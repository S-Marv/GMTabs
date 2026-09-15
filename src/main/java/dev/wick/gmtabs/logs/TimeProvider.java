package dev.wick.gmtabs.logs;

import java.time.OffsetDateTime;

public class TimeProvider {
	public OffsetDateTime getTimeStamp() {
		return OffsetDateTime.now();
	}
}
