package ch.ess.cal;

import java.io.ByteArrayInputStream;
import java.security.Principal;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

/**
 * This class demonstrates how to decode the DN attribues inside a X509 certificate
 * using the standard Java Cryptography Extension (JCE)libraries.
 */
public class CertificateDecoder {
  public static final String prefix = "BEGIN CERTIFICATE-----";
  public static final String suffix = "-----END CERTIFICATE-----";

  /**
   * Decodes a PEM encoded certificate
   * @param PEMCert - X509 Certificate to decode
   * @return subject DN string
   */
  public static String decodeCertificate(String PEMCert) {
    try {
      // Strip prefix and suffix strings if present
      int idx = PEMCert.indexOf(prefix);
      if (idx > -1) {
        PEMCert = PEMCert.substring(idx + prefix.length());
      }
      idx = PEMCert.indexOf(suffix);
      if (idx > -1) {
        PEMCert = PEMCert.substring(0, idx);
      }

      CertificateFactory cf = CertificateFactory.getInstance("X.509");
      java.io.InputStream in = null;
      in = new ByteArrayInputStream(Base64.decode(PEMCert.getBytes()));
      java.security.cert.Certificate cert = cf.generateCertificate(in);
      if (cert instanceof X509Certificate) {
        X509Certificate x509cert = (X509Certificate)cert;

        // Get subject
        Principal principal = x509cert.getSubjectDN();
        String subjectDn = principal.getName();

        // Get issuer
        //principal = x509cert.getIssuerDN();
        //String issuerDn = principal.getName();
        return subjectDn;
      }

      return "Unable to get certificate subject name";

    } catch (CertificateException e) {
      e.printStackTrace();
      return "Error, certificate exception encountered";
    }
  }

}