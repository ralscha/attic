
import java.io.*;
import java.util.*;
import common.util.*;
import common.io.*;
import java.text.*;
import javax.crypto.*;
import java.security.*;
import java.security.spec.*;
import javax.crypto.spec.*;
import ViolinStrings.*;

public class Decrypt {

	String backupZipFileName, backupZipPath, decryptedFileName;

	public Decrypt() {
		backupZipFileName = AppProperties.getStringProperty("backup.zip.file");
		backupZipPath = AppProperties.getStringProperty("backup.zip.path");
		
		decryptedFileName = AppProperties.getStringProperty("backup.decrypted.file");
	}

	public void doIt() {
		try {

			File zipFile = new File(backupZipPath + backupZipFileName);

			CipherInputStream cis =
  				new CipherInputStream(new FileInputStream(zipFile), getCipher());

			FileOutputStream fos = new FileOutputStream(decryptedFileName);
			copy(cis, fos);
			cis.close();
			fos.close();

		} catch (IOException ioe) {
			System.err.println(ioe);
		}
	}

	public static void copy(InputStream in, OutputStream out) throws IOException {

		// do not allow other threads to read from the
		// input or write to the output while copying is
		// taking place

		synchronized (in) {
			synchronized (out) {
				BufferedInputStream bin = new BufferedInputStream(in);
				BufferedOutputStream bout = new BufferedOutputStream(out);

				while (true) {
					int datum = bin.read();
					if (datum == -1)
						break;
					bout.write(datum);
				}

				bout.flush();

			}
		}
	}


	private Cipher getCipher() {

		try {
			String keyDatFile = AppProperties.getStringProperty("encrypt.key.file", "key.dat");
			File keyFile = new File(keyDatFile);
			byte[] keyBytes = new byte[(int) keyFile.length()];

			FileInputStream fis = new FileInputStream(keyDatFile);
			System.out.println(fis.read(keyBytes));
			fis.close();


			SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "Blowfish");
			SecretKeyFactory keyFact = SecretKeyFactory.getInstance("Blowfish");

			SecretKey key = keyFact.generateSecret(keySpec);

			Cipher cipher = Cipher.getInstance("Blowfish/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, key);

			return cipher;

		} catch (Exception e) {
			System.err.println(e);
			return null;
		}


	}

	public static void main(String[] args) {
		Security.addProvider(new au.net.aba.crypto.provider.ABAProvider());
		new Decrypt().doIt();
	}
}