package net.java.dev.cejug.classifieds.test.integration;

import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

/**
 * A JUnit class that can be launch in concurrent mode.
 * 
 * @author $Author$
 * @version $Rev$ ($Date$)
 * 
 */
public class JUnitRunnable extends BlockJUnit4ClassRunner implements Runnable {
	private transient final RunNotifier notifier;

	public JUnitRunnable(Class<?> testCase, RunNotifier notifier)
			throws InitializationError {
		super(testCase);
		this.notifier = notifier;
	}

	public void run() {
		run(notifier);
	}
}
