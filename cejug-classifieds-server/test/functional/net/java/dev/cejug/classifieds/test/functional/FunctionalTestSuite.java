package net.java.dev.cejug.classifieds.test.functional;

import net.java.dev.cejug.classifieds.test.functional.admin.AdminTestSuite;
import net.java.dev.cejug.classifieds.test.functional.business.BusinessTestSuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({AdminTestSuite.class, BusinessTestSuite.class})
public class FunctionalTestSuite {
}
