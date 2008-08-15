package net.java.dev.cejug.classifieds.test.integration.admin;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses( { CategoryMaintenanceFunctionalTest.class,
		DomainMaintenanceFunctionalTest.class, CheckMonitorFunctionalTest.class })
public class AdminTestSuite {
}