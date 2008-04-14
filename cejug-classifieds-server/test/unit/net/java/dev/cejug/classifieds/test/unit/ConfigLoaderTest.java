package net.java.dev.cejug.classifieds.test.unit;

import junit.framework.Assert;
import net.java.dev.cejug.classifieds.server.config.ConfigLoader;
import net.java.dev.cejug.classifieds.server.generated.config.ClassifiedsServerConfig;

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
			// Marshall listener should replace missed information by the
			// service defaults.
			ConfigLoader loader = ConfigLoader.getInstance();
			ClassifiedsServerConfig config = loader.load();
			Assert.assertNotNull(config.getInjection()
					.getServiceImplementation());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
