/*
 * @(#) $CVSHeader:  $
 *
 * Copyright (C) 2008 by Netcetera AG.
 * All rights reserved.
 *
 * The copyright to the computer program(s) herein is the property of
 * Netcetera AG, Switzerland.  The program(s) may be used and/or copied
 * only with the written permission of Netcetera AG or in accordance
 * with the terms and conditions stipulated in the agreement/contract
 * under which the program(s) have been supplied.
 *
 * @(#) $Id: codetemplates.xml,v 1.5 2004/06/29 12:49:49 hagger Exp $
 */
package net.java.dev.cejug.classifieds.test.unit.utils;

import net.java.dev.cejug.utils.RandomStringGenerator;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * TODO: to comment.
 * 
 * @author $Author: felipegaucho $
 * @version $Rev: 504 $ ($Date: 2008-08-24 11:22:52 +0200 (So, 24 Aug 2008) $)
 */
public class RandomStringGeneratorTest {

  private RandomStringGenerator rsg = new RandomStringGenerator();

  @Test
  public void testGeneratedLengthIsGivenLength() {
    String aleaJactaEst = rsg.generateString(10);
    assertEquals(10, aleaJactaEst.length());
    aleaJactaEst = rsg.generateString(273);
    assertEquals(273, aleaJactaEst.length());
    aleaJactaEst = rsg.generateString(2);
    assertEquals(2, aleaJactaEst.length());
  }

  @Test
  public void testGeneratedStringsAreDifferent() {
    RandomStringGenerator rsg = new RandomStringGenerator();
    String first = rsg.generateString(10);
    String second = rsg.generateString(10);
    assertFalse("Two identical strings generated consecutively", first.equals(second));
  }
}
