package net.java.dev.cejug.classifieds.test.integration.business;

import generated.Rss;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses( { LoadAtomIntegrationTest.class,
		LoadRssIntegrationTest.class, PublishIntegrationTest.class,
		ReportSpamIntegrationTest.class })
public class BusinessTestSuite {
	public static void main(String[] args) {
		System.out.println(Rss.class.getPackage().getName());
	}
}