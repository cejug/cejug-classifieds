package net.java.dev.cejug.classifieds.test.unit;

import net.java.dev.cejug.classifieds.server.config.ConfigWrapper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ConfigLoaderTest {

	@Before
	public void setUp() throws Exception {
		// include or activate a new advertisement (submit via service or direct
		// into database)
	}

	@After
	public void tearDown() throws Exception {
		// remove or inactive the test advertisement
	}

	@Test
	public void testConfigLoading() {
		try {
			ConfigWrapper wrapper = 
				ConfigWrapper.getInstance();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
