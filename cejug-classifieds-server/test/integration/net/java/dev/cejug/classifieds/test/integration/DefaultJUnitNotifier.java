package net.java.dev.cejug.classifieds.test.integration;

import junit.framework.Assert;

import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;

public class DefaultJUnitNotifier extends RunNotifier {
	@Override
	public void fireTestFailure(Failure failure) {
		Assert.fail(failure.getMessage());
	}
}
