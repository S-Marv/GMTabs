package dev.wick.gmtabs.logs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

class FileTelemetryLogger implements TelemetryLogger{
	private final PrintStream log;
	private final TimeProvider timeProvider;
	private final ExecutorService executor;
	private final EventCounter counter;

	FileTelemetryLogger(File targetDir, TimeProvider customTimeProvider){
		timeProvider = customTimeProvider==null? new TimeProvider() : customTimeProvider;
		try{
			//noinspection ResultOfMethodCallIgnored
			targetDir.mkdir();
			log = new PrintStream(getFile(targetDir, timeProvider));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
		executor = Executors.newSingleThreadExecutor();
		counter = new EventCounter();
	}

	void logEvent(EventType event, String... data) {
		counter.recordEvent(event);
		StringBuilder sb = new StringBuilder(timeProvider.getTimeStamp().toString());
		sb.append(event.getLogText());
		for (String datapoint : data){
			sb.append("\t").append(datapoint);
		}
		log.println(sb);
	}

	static File getFile(File targetDir, TimeProvider timeProvider){
		long time = Date.from(timeProvider.getTimeStamp().toInstant()).getTime();
		String filename = String.format("L%tY-%<tm-%<td_%<tH.%<tM.%<tS.%<tL.tsv", time);
		return Paths.get(targetDir.getPath(), filename).toFile();
	}

	@Override
	public void logEventAsync(EventType eventType, String... data) {
		executor.execute(()-> logEvent(eventType, data));
	}

	@Override
	public void shutdown() {
		executor.execute(()->{
			logEvent(EventType.CLOSE);
			log.println("------\nSession Summary");
			//log.printf("Tabs at end of session: %d\n", tabCount);
			for(EventType eventType : EventType.values()) {
				if(eventType.isExcludedFromCounter()) continue;
				log.printf("%s: \t%d%n", eventType.getLogText(), counter.getCount(eventType));
			}
			log.close();
		});
		executor.shutdown();
	}


	private static class EventCounter{
		private final Map<EventType, AtomicInteger> map = new HashMap<>();

		private EventCounter(){
			for(EventType type : EventType.values()) {
				if(!type.isExcludedFromCounter()) map.put(type, new AtomicInteger());
			}
		}

		void recordEvent(EventType event){
			if(event.isExcludedFromCounter()) return;
			map.get(event).incrementAndGet();
		}

		int getCount(EventType eventType){
			if(eventType.isExcludedFromCounter()) return -1;
			return map.get(eventType).get();
		}
	}
}
