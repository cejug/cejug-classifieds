package net.java.dev.cejug.classifieds.test.unit;

import junit.framework.Assert;

import net.java.dev.cejug.classifieds.server.config.ConfigLoader;
import net.java.dev.cejug.classifieds.server.generated.config.ClassifiedsServerConfig;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.fail;

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

  private static final ConfigLoader loader = ConfigLoader.getInstance();

  @Test
  public void testConfigLoading() {
    try {
      // Marshall listener should replace missed information by the
      // service defaults.

      ClassifiedsServerConfig config;
      config = loader.load();
      Assert.assertNotNull(config.getInjection().getServiceImplementation());
    } catch (Exception e) {
      // TODO Auto-generated catch block
      fail(e.getMessage());
    }
  }
}
