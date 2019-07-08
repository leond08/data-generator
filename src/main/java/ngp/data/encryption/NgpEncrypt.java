package ngp.data.encryption;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.engines.RijndaelEngine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.paddings.ZeroBytePadding;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NgpEncrypt {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(NgpEncrypt.class);
	private String secureKey;
	private String vectorKey;
	private int blockSize;
	
	public NgpEncrypt(AppParams params) {
		this.secureKey = params.getSecureKey();
		this.vectorKey = params.getVectorKey();
		this.blockSize = params.getBlockSize();
	}
	
	public String md5(String input) {
		
		String md5 = null;
		
		if(null == input) {
			return null;
		}
		
		try {
			//Create MessageDigest object for MD5
			MessageDigest digest = MessageDigest.getInstance("MD5");
		
			//Update input string in message digest
			digest.update(input.getBytes(), 0, input.length());
		
			//Converts message digest value in base 16 (hex) 
			md5 = new BigInteger(1, digest.digest()).toString(16);

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return md5;
	}
	
	public String aes(String query) {
		
		try {
			
	        PaddedBufferedBlockCipher cipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(new RijndaelEngine(blockSize)), new ZeroBytePadding());
	        CipherParameters ivAndKey = new ParametersWithIV(new KeyParameter(secureKey.getBytes()), vectorKey.getBytes());
	        cipher.init(true, ivAndKey);
	        
	        return Base64.encodeBase64String(cipherData(cipher, query.getBytes()));
	        
	    } catch (InvalidCipherTextException e) {
	        throw new RuntimeException(e);
	    }
	}
	
	private static byte[] cipherData(PaddedBufferedBlockCipher cipher, byte[] data) throws InvalidCipherTextException {
		
	    int minSize = cipher.getOutputSize(data.length);
	    byte[] outBuf = new byte[minSize];
	    int length1 = cipher.processBytes(data, 0, data.length, outBuf, 0);
	    int length2 = cipher.doFinal(outBuf, length1);
	    int actualLength = length1 + length2;
	    byte[] cipherArray = new byte[actualLength];
	    
	    for (int x = 0; x < actualLength; x++) {
	        cipherArray[x] = outBuf[x];
	    }
	    
	    return cipherArray;
	}


}
