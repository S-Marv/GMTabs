package dev.wick.gmtabs.logs;

import java.io.*;

public class ErrorLogger {
	private final TimeProvider timeProvider;


	private final PrintStream printStream;

	ErrorLogger(File file, String version, TimeProvider timeProvider) {
		this.timeProvider = timeProvider;
		try {
			printStream = new PrintStream(file);
			printStream.printf("GMTabs Version: %s at %s%n", version, timeProvider.getTimeStamp());
		} catch (IOException e) {
//			if(e instanceof AccessDeniedException accessDeniedException) throw accessDeniedException;
			throw new RuntimeException(e);
		}
	}

	public final void logException(Exception e){
		printStream.println(timeProvider.getTimeStamp());
		Throwable throwable = e;
		while (throwable!=null){
			throwable.printStackTrace(printStream);
			throwable = throwable.getCause();
		}
	}

	public final void shutdown(){
		printStream.close();
	}
}
