package net.java.dev.cejug.classifieds.test.functional;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses( { LoadAtomFunctionalTest.class,
		LoadRssFunctionalTest.class, PublishFunctionalTest.class,
		ReportSpamFunctionalTest.class, AdminFunctionalTest.class,
		MaintainCategoriesFunctionalTest.class })
public class FunctionalTestSuite {
}