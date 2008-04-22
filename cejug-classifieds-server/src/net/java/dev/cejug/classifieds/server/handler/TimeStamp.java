package net.java.dev.cejug.classifieds.server.handler;

public final class TimeStamp {
	public static final String KEY = "timestamp";
	public static final String OPERATION_KEY = "operation";
	private long time = System.currentTimeMillis();

	public long getTime() {
		return time;
	}
}
