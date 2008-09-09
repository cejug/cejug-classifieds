package net.java.dev.cejug.classifieds.test.integration.business;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.codeplex.rss2schema.Rss;

@RunWith(Suite.class)
@Suite.SuiteClasses( { LoadAtomIntegrationTest.class,
		LoadRssIntegrationTest.class, PublishIntegrationTest.class,
		ReportSpamIntegrationTest.class })
public class BusinessTestSuite {
  public static void main(String[] args) {
    System.out.println(Rss.class.getPackage().getName());
  }
}