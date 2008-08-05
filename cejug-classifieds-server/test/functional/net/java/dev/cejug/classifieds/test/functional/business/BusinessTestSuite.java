package net.java.dev.cejug.classifieds.test.functional.business;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses( { LoadAtomFunctionalTest.class,
		LoadRssFunctionalTest.class, PublishFunctionalTest.class,
		ReportSpamFunctionalTest.class })
public class BusinessTestSuite {
}