
import javax.crypto.*;
import java.security.*;
import java.security.spec.*;
import javax.crypto.spec.*;
import ViolinStrings.*;

import java.io.*;
import java.util.*;

public class SecurityTest {
	
	//53,59,6A,1C,21,DD,70,7B,46,0F,B5,81,90,81,5F,83  -> MD5
	
	
	public void run1() {
		try {
			String SEARCHPW = "53596A1C21DD707B460FB58190815F83";
			MessageDigest digest = MessageDigest.getInstance("MD5");
			/*
			
			List abc = new ArrayList();
		
			char start = 'A';
			for (int i = 0; i < 26; i++)
				abc.add(new Character((char)(start+i)));
		
		
			List rl = new Permutation(abc.toArray(), 3).getPermutations();
			System.out.println(rl.size());
		
			StringBuffer sb = new StringBuffer();
			StringBuffer rs = new StringBuffer();
			
			Iterator it = rl.iterator();
			byte[] pw = null;
			byte[] result = null;
			
			while(it.hasNext()) {
				sb.setLength(0);
				rs.setLength(0);
				Object[] ia = (Object[])it.next();
				for (int i = 0; i < ia.length; i++) {
					sb.append(ia[i]);
				}
				pw = sb.toString().getBytes();
				digest.update(pw);
				result = digest.digest();
				
				rs.setLength(0);
				for (int i = 0; i < result.length; i++) {
					rs.append(Strings.rightJustify(Integer.toHexString(result[i]).toUpperCase(), 2, '0'));
				}	
				if (rs.toString().equals(SEARCHPW)) {
					System.out.println(sb.toString());
					return;
				}
				
			}

			
			*/
			
			byte[] msg = "A756288".getBytes();
			digest.update(msg);
			
			byte[] result = digest.digest();
			
			for (int i = 0; i < result.length; i++) {
				System.out.print(Strings.rightJustify(Integer.toHexString(result[i]).toUpperCase(), 2, '0'));
			}
			
			
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	/*
	public void run2() {
		byte b = (byte)Integer.parseInt("9C", 16);
		byte c = 0x0000007F;
		System.out.println(b);
		System.out.println(c);
	}

	public void encode() {
		
		try {		
		   KeyGenerator keyGenerator = KeyGenerator.getInstance("Blowfish");
		   keyGenerator.init(128);
		   SecretKey key = keyGenerator.generateKey();
		   
		   Cipher cipher = Cipher.getInstance("Blowfish/CBC/PKCS5Padding");
		   cipher.init(Cipher.ENCRYPT_MODE, key);
			
			FileInputStream fis = new FileInputStream("teletext.html");
			CipherOutputStream cos = new CipherOutputStream(new FileOutputStream("teletext.html.encoded"), cipher);
			
			byte[] iv = new byte[cipher.getBlockSize()];
			new SecureRandom().nextBytes(iv);			
			cos.write(iv, 0, iv.length);
			
			byte[] buffer = new byte[1024];
			int len;			
			while((len = fis.read(buffer)) != -1) {	
				cos.write(buffer, 0, len);
			}
			cos.close();
			fis.close();


			/
			PrintWriter pw = new PrintWriter(new FileWriter("key.dat"));
			byte[] keybytes = key.getEncoded();
			
			for (int i = 0; i < keybytes.length; i++)
				pw.println(Strings.rightJustify(Integer.toHexString(keybytes[i]).toUpperCase(), 2, '0'));		
			pw.close();
			/
		
			SecretKeyFactory keyFact = SecretKeyFactory.getInstance("Blowfish");
			SecretKeySpec keySpec = (SecretKeySpec)keyFact.getKeySpec(key, SecretKeySpec.class);
			byte[] key2 = keySpec.getEncoded();
			
			PrintWriter pw = new PrintWriter(new FileWriter("key.dat"));
			
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < key2.length; i++) {
				sb.append(Strings.rightJustify(Integer.toHexString(key2[i]).toUpperCase(), 2, '0'));
			}
			pw.println(sb.toString());
			pw.close();
	
		} catch (Exception e) {
			System.err.println(e);
		}			
	}

	public void decode() {
		
		try {	
			BufferedReader br = new BufferedReader(new FileReader("key.dat"));
			String line = br.readLine();
			br.close();
			
			int ix = 0;
			byte[] keyBytes = new byte[line.length() / 2];
			for (int i = 0; i < line.length(); i += 2) {
				keyBytes[ix++] = (byte)Integer.parseInt(line.substring(i, i+2), 16);
			}
			
			SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "Blowfish");
			SecretKeyFactory keyFact = SecretKeyFactory.getInstance("Blowfish");
			
			SecretKey key = keyFact.generateSecret(keySpec);
			
			Cipher cipher = Cipher.getInstance("Blowfish/CBC/PKCS5Padding");
		

			cipher.init(Cipher.DECRYPT_MODE, key);
			
			CipherInputStream cis = new CipherInputStream(new FileInputStream("teletext.html.encoded"), cipher);
			FileOutputStream fos = new FileOutputStream("teletext.html.decoded");
			
			
			
			byte[] buffer = new byte[1024];
			int len;
			len = cis.read(buffer, 0, cipher.getBlockSize());	
			while((len = cis.read(buffer)) != -1) {			
				fos.write(buffer, 0, len);
			}
			cis.close();
			fos.close();
			
		} catch (Exception e) {
			System.err.println(e);
		}
			
	}
	

	public void run() {

		

		try {		
		   KeyGenerator blowGenerator = KeyGenerator.getInstance("Blowfish");
		   blowGenerator.init(128);
		
		   SecretKey blowKey = blowGenerator.generateKey();
		   Cipher blowfish = Cipher.getInstance("Blowfish");
		   blowfish.init(Cipher.ENCRYPT_MODE, blowKey);
			
			System.out.println(blowKey.getAlgorithm());
			System.out.println(blowKey.getFormat());
			
			byte[] keybytes = blowKey.getEncoded();
			
			for (int i = 0; i < keybytes.length; i++) {
				System.out.print(Strings.rightJustify(Integer.toHexString(keybytes[i]).toUpperCase(), 2, '0') + ',');
			}
			

   	} catch (Exception e) {
			System.err.println(e);
		}
	}
	*/
	public static void main(String[] args) {
		Security.addProvider(new au.net.aba.crypto.provider.ABAProvider());
		//Security.addProvider(new hoyle.security.improv.Improv());
		
		//Security.addProvider(new cryptix.provider.Cryptix());
		
		new SecurityTest().run1();
		/*
		new SecurityTest().encode();
		new SecurityTest().decode();
		*/
	}
}