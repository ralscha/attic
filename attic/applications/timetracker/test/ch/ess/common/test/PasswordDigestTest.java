package ch.ess.common.test;

import junit.framework.TestCase;

import org.apache.commons.codec.digest.DigestUtils;

import ch.ess.common.util.PasswordDigest;

/**
 * @author sr
 */
public class PasswordDigestTest extends TestCase {

  public void testPasswordDigest() {
    String digest = DigestUtils.shaHex("fredo");
    assertTrue(PasswordDigest.validatePassword(digest, "fredo"));
    assertFalse(PasswordDigest.validatePassword(digest, "fredo2"));
  }

}
