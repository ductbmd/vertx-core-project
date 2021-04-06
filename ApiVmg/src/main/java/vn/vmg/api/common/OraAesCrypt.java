package vn.vmg.api.common;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class OraAesCrypt {
	private static OraAesCrypt instance;

	private final byte[] encryptionkeyBytes = new byte[] { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
			0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };
	private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
	private final IvParameterSpec ivParameterSpec;
	private final SecretKeySpec secretKeySpec;
	private final Cipher cipher;

	public static synchronized OraAesCrypt getIns() {
		if (instance == null) {
			try {
				instance = new OraAesCrypt(SerConfig.app.oraAesKey);
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		return instance;
	}

	private OraAesCrypt(String privateKey) throws UnsupportedEncodingException, NoSuchAlgorithmException,
			InvalidKeySpecException, NoSuchPaddingException {
		ivParameterSpec = new IvParameterSpec(this.encryptionkeyBytes);
		secretKeySpec = new SecretKeySpec(this.buildKey(privateKey), "AES");
		cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
	}

	public String encrypt(String toBeEncrypt) throws NoSuchPaddingException, NoSuchAlgorithmException,
			InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

		cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
		byte[] encrypted = cipher.doFinal(toBeEncrypt.getBytes());
		return bytesToHex(encrypted);
	}

	public String decrypt(String encrypted) throws InvalidAlgorithmParameterException, InvalidKeyException,
			BadPaddingException, IllegalBlockSizeException {

		cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
		byte[] decryptedBytes = cipher.doFinal(hexStringToByteArray(encrypted));
		return new String(decryptedBytes);
	}

	private byte[] buildKey(String key) throws UnsupportedEncodingException {
		int keySize = key.length() > 26 ? 32 : 16;

		byte[] keyBytes = new byte[keySize];
		byte[] b = key.getBytes("UTF-8");
		int len = b.length;
		if (len > keyBytes.length)
			len = keyBytes.length;
		System.arraycopy(b, 0, keyBytes, 0, len);
		return keyBytes;
	}

	private String bytesToHex(byte[] bytes) {
		char[] hexChars = new char[bytes.length * 2];
		for (int j = 0; j < bytes.length; j++) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = HEX_ARRAY[v >>> 4];
			hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
		}
		return new String(hexChars);
	}

	private byte[] hexStringToByteArray(String s) {
		int len = s.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
		}
		return data;
	}
	
	public static void main(String[] args) throws IOException, InvalidKeyException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException {
		SerConfig.init();
		System.out.println("==> "+OraAesCrypt.getIns().decrypt("EEC2C9A17B9A2FC5A41CC6AFF8A4E450"));
	}

}
