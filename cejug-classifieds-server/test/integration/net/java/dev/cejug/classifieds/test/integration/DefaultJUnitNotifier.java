package net.java.dev.cejug.classifieds.test.integration;

import java.util.ArrayList;
import java.util.List;

import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;

public class DefaultJUnitNotifier extends RunNotifier {
	private int threadCounter = 0;
	List<Failure> failures = new ArrayList<Failure>();

	public List<Failure> getFailures() {
		return failures;
	}

	public void incrementThreadsCounter() {
		threadCounter++;
	}

	public int getThreadCounter() {
		return threadCounter;
	}

	@Override
	public void fireTestFailure(Failure failure) {
		failures.add(failure);
	}

	@Override
	public void fireTestFinished(Description description) {
		threadCounter--;
		System.out.println(description.getDisplayName() + " finished.");
	}
}
