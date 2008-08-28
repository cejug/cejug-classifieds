package net.java.dev.cejug.classifieds.test.integration;

import java.util.List;

import net.java.dev.cejug.classifieds.test.integration.admin.AdminTestSuite;
import net.java.dev.cejug.classifieds.test.integration.business.BusinessTestSuite;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.notification.Failure;
import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;

public class ConcurrentTestSuite {
	private DefaultJUnitNotifier notifier = new DefaultJUnitNotifier();
	private int concurrencyFactor = 10;

	@Test
	public void admin() {
		Suite.SuiteClasses annos = AdminTestSuite.class
				.getAnnotation(Suite.SuiteClasses.class);
		List<Failure> failures = runAll(annos);
		if (failures.size() > 0) {
			Assert.fail(failures.get(0).getTrace());
		}
	}

	@Test
	public void business() {
		Suite.SuiteClasses annos = BusinessTestSuite.class
				.getAnnotation(Suite.SuiteClasses.class);
		List<Failure> failures = runAll(annos);
		if (failures.size() > 0) {
			Assert.fail(failures.get(0).getTrace());
		}
	}

	private synchronized List<Failure> runAll(Suite.SuiteClasses annos) {
		try {
			Class<?>[] testSuite = annos.value();
			Thread[] processes = new Thread[testSuite.length
					* concurrencyFactor];

			ThreadGroup adminTestSuiteGroup = new ThreadGroup("AdminTestSuite");
			for (int i = 0; i < testSuite.length; i++) {
				for (int j = 0; j < concurrencyFactor; j++) {
					processes[i * concurrencyFactor + j] = new Thread(
							adminTestSuiteGroup, new JUnitRunnable(
									testSuite[i], notifier));
					notifier.incrementThreadsCounter();
				}
			}

			for (Thread t : processes) {
				t.start();
			}

			// TODO: include a decent timer here instead of this blatant
			// counter.
			while (notifier.getThreadCounter() > 0) {
				Thread.yield();
			}

			return notifier.getFailures();
		} catch (InitializationError e) {
			return null;
		}
	}
}
