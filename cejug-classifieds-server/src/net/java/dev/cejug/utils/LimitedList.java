package net.java.dev.cejug.utils;

import java.util.ArrayList;

public final class LimitedList extends ArrayList<Long> {
	private static final long serialVersionUID = 1L;
	public static final int DEFAULT_LIMIT = 10;
	private final int LIMIT;

	public LimitedList(int limit) {
		LIMIT = limit;
	}

	public LimitedList() {
		this(DEFAULT_LIMIT);
	}

	@Override
	public synchronized boolean add(Long e) {
		if (size() == LIMIT) {
			super.remove(0);
		}
		return super.add(e);
	}

	public synchronized double getAverage() {
		long count = 0L;
		for (Long value : this) {
			count += value;
		}
		return Double.longBitsToDouble(count) / size();
	}
}
