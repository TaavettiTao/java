/**
 * Copyright Shanghai COS Software Co., Ltd.
 * All right reserved
 * TestRSASignature.java
 * Created on 2016
 */

package com.rsa.test;

import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Map;

import javax.crypto.Cipher;
import javax.security.cert.CertificateException;

import org.apache.commons.codec.binary.Base64;

import com.cosw.commons.crypto.RSAUtil;
import com.cosw.commons.crypto.RSAUtil.AlgorithmEnum;
import com.cosw.commons.util.BytesUtil;
import com.rsa.test.util.RSASignature;
import com.rsa.test.util.StringUtil;


/**
 * @FileName: TestRSASignature.java
 * @Description:
 * @Company: Shanghai COS Software
 * @Copyright: Copyright (c)2016
 * @author yangb
 * @version 1.0
 * @Create: 2016-4-11 下午04:35:52
 * 
 * @Modification History
 * @Date Author Version Description
 * @----------------------------------------------------
 * @2016-4-11 yangb 1.0 create
 * 
 * RSA:公私钥模一样，指数不同
 */
public class RSAUtilTest {

	public static void main(String[] args) throws Exception {
		/*System.out.println("==================");
		long begin = System.currentTimeMillis();

		genKeyPairTest();

		// testPublicKey();

		// loadPrivateKeyTest();

		System.out.println("===end===" + (System.currentTimeMillis() - begin));*/
		
		/*//base64编码的公钥
		String key="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAx+yMLnBhVreFOITTNMosXs7tLVUvEqqvLJPaE+FhyouIUU5CZQnKGUV6zUhlUO9BAh22h2udExUgrKzvElrIcmUsIXyqXzTajOAnpBgikYY8J7tQretvk3Uj11Z0JaBz3pt6EXqcYxLQMu/cY0neX8BNYy1B81zrpjntsHbpBMhw1uI+BhjVQ9iXWxpL3bMeSW9H6R+/lztRvbGcnbVHrPKlkDxwwRBfmbAim5mrEovHS8hlwhynVPxWMZ8mlNU3TtWBe9AG7HQftFXeg1gKCv8Nd04EbFbm7NFv4OgPYDiM7pztH5vSuO+/sQZPkieb7xDK+aDrZZaeDIN8vU5ZBQIDAQAB";
		System.out.println(Base64.isBase64(key));
		
		//转换成RSAPublicKey
		RSAPublicKey publicKey=(RSAPublicKey) getPublicKey(key);
		//16进制模
		System.out.println(publicKey.getModulus().toString(16));
	    //16进制指数
		System.out.println(publicKey.getPublicExponent().toString(16));*/
		
		//获取base64编码公钥串
	/*	System.out.println(getKeyString(publicKey));*/
		
		genKeyPairTest();
		
		//SHA1WithRSA签名算法
		//java.security.Signature signature = java.security.Signature.getInstance("SHA1WithRSA");
		String modulus="8B6D8CAE9C9494FC1AE3C90E5869111447FA19F66F62D904787C973862D08B0056CA891ECA0CA7CF5D38407BA7AF8FC9A83624CDC46B9A47B0FEBAD0FC730D80C7C1CA1088731D758C6D26A3A2DF7BD7634EEA107B6D752609A16C9C671758A853135214425C58B0DD25779DFB070B08817F0828C72BEF11A5D7F998D0F81D49";
		String exponent="10001";
		String data="{\"txncode\":\"charge\",\"cardno\":\"2253123456781234\"}";
		String hexData=StringUtil.byteArrayToHexString(data.getBytes());
		String signData="8501C57883A26FFDA679403816E31BC3B455C5E04BDE47711BAF50A926151BEB0BE895E2563C1BE205D5E2054A1321FFF5A09D39677A4EE7A856AB1FD74F0A38641A868C5C7A781F89D2496D978DDF6B63E44110C52D58E5D1106271A75083A4E948D19B7BB9BE0040D85536FDAC1649015C2049586590CF878DFE110507E87A";
		
		boolean flag=RSASignature.verify(StringUtil.hexStringToByteArray(hexData), 
				StringUtil.hexStringToByteArray(signData), modulus, exponent, 16);
		System.out.println("验签结果："+flag);
		}

	public static void genKeyPairTest() throws Exception {
		Map<String, Object> keyMap = RSAUtil.genKeyPair(1024);// 1152,1024
		byte[] privateKeyBytes = RSAUtil.getPrivateKey(keyMap);
		byte[] publicKeyBytes = RSAUtil.getPublicKey(keyMap);

		PrivateKey privateKey = RSAUtil.loadPrivateKey(privateKeyBytes);
		System.out.println("私钥 ：" + privateKey);
	    RSAPrivateKey rsaPrivateKey=(RSAPrivateKey)privateKey;
	    System.out.println("私钥的模："+rsaPrivateKey.getModulus().toString(16));
	    System.out.println("私钥的指数："+rsaPrivateKey.getPrivateExponent().toString(16));
	    
		PublicKey publicKey = RSAUtil.loadPublicKey(publicKeyBytes);
		RSAPublicKey rsaPublicKey=(RSAPublicKey)publicKey;
		System.out.println("公钥 ：" + publicKey);
		System.out.println("公钥的模："+rsaPublicKey.getModulus().toString(16));
		System.out.println("公钥的指数："+rsaPublicKey.getPublicExponent().toString(16));

		/*printRSAKey((RSAKey) privateKey);
		printRSAKey((RSAKey) publicKey);*/
	}
	
	/**
	 * 获取公钥
	 * @param key
	 *        Base64编码字符串
	 * @return
	 * @throws Exception
	 */
	  public static PublicKey getPublicKey(String key) throws Exception {
          byte[] keyBytes=null;
          keyBytes = Base64.decodeBase64(key);
         // keyBytes =(new Base64Decoder()).decodeBuffer(key);
          X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
          KeyFactory keyFactory = KeyFactory.getInstance("RSA");
          PublicKey publicKey = keyFactory.generatePublic(keySpec);
          return publicKey;
    }
	  

	public static PrivateKey loadPrivateKeyTest() throws Exception {
		String modulus = "9456009f52ca4af09f5173f9c48fa7c3648037b30423bd769ef5e770cb7561e4581b93e01dfbfd5bde53c06d0479f73e53e3787667bce5aab26ad14ceb35c046b2f4c283a78a66ee8c4ac410a14ea6a6501371f09a45f69ce12869979c8042707f347c49e70a1318df74d7b3f173e0fbf666fd461dc26e0e6ac62b179a258efb8ee3d15feadd6d8f281602828431b96d";
		String privateExponent = "181238b9a20ab7cc825e0a6eeea9cd3494fb858e644e94714a6c672953aaf1bec7597a4eb52eee375945c80528e3cdee6c588e74d072c3e6465bb529414610b4a857b5b4fd9c2505b3b59e7293948fe90b254f08a516b44f16856b7406f9ef51f4bc958f2ae5ae06fba64f4e67c8251a246567003524b0cabbab7ed0d81dc7b684e5a45336b8a6c053d9041bf4f04e21";

		PrivateKey privateKey = RSAUtil.loadPrivateKey(modulus,
				privateExponent, 16);

		String data = "562E3502E629D0D3108A4B74A7D15B8E42FBF993E2D2A4CD85A940C0578CA5564C0FEF2D3D2B56E41075CF767510B3F3AD75D9980A968B0143F7F2D0297015E59703EDD2037B0195C462C23C792FEE12E0517CE5C9DCFB246A36E66D56130676FFDFFD6E92C5F9B2E0B4F49F9906FE8BD9198EBF7F4BFDF391AF8701948E654ABE638B23B89DFF1CCCD67D7CFC16DBDE";
		data = "2D48BE0D229C334E0E99892038F1FF9ED3D9B0798F81EC69A8EE17E75CF9E91D6939434093AAAD3854B52D9720F7605B078FE4EE810513CD931FF9988092C4139D68609E673473253C8C381D8B35ABD5B8559B794B6EEFD4C1F87F6BB0BCF2028758823B4F50573F64C83181011E4F761786916AFE87F25EC7C2FFAC1C59CF159078970230B95F637191EDB576D60147";
		byte[] da = BytesUtil.hexToBytes(data);

		Cipher cipher = Cipher.getInstance("RSA/ECB/pkcs1padding");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		byte[] r = cipher.doFinal(da);
		String resp = BytesUtil.bytesToHex(r);

		// String resp = privateKeyEncryptTest(privateKey, data);

		System.err.println("===resp===" + resp);

		return privateKey;
	}

	public static String publicKeyEncryptTest(PublicKey publicKey, String data)
			throws Exception {

		byte[] dd = BytesUtil.hexToBytes(data);
		byte[] resp = RSAUtil.publicKeyCrypt(publicKey,
				AlgorithmEnum.RSA_ECB_PKCS1, true, dd);

		return BytesUtil.bytesToHex(resp);
	}

	public static String privateKeyEncryptTest(PrivateKey privateKey,
			String data) throws Exception {

		byte[] dd = BytesUtil.hexToBytes(data);
		byte[] resp = RSAUtil.privateKeyCrypt(privateKey,
				AlgorithmEnum.RSA_ECB_PKCS1, false, dd);

		return BytesUtil.bytesToHex(resp);
	}

	public static void testPublicKey() throws Exception {
		String publicModulus = "CB5DDEC9E75CA2FFD06055C392734276D8B96257F363D94308447B3D7A12FA09B41DD89F970140AD849D71963257A161384AF7AD12DAD11B8FF2D2CE29EAB8BD659CBFF7AF320ADB64FE1E87BEB4016BDAFA1787EF62F3B2A414CEF1F6109B7CE99777D4C079114281BD351E51381AC40AB1A26CFAAD52871EA09E3A41DBFA2A7A22D778BB608597C9430C072755EE4D";
		String publicExponent = "010001";

		publicModulus = "B60F8E636C2F292511EBC21C4EAE2F0131DEEC92567505F637C80506850B748A52AB1F8FE0756BCC158EA94F1CADFAE584E29313E8C019009C4B0F0C2E91C9715A12F6E00AADE9CB8F80F98E4F4100BA4AB950E1689F81E14BBBBD8A69795A0CBAF4A969BC0262B7F764BDFD9991CA1324480D0AB8F62CDDA962EAFAE404C664695DAF9D92F7A995694AF0D8A7650A0D";
		publicExponent = "010001";

		publicModulus = "9456009f52ca4af09f5173f9c48fa7c3648037b30423bd769ef5e770cb7561e4581b93e01dfbfd5bde53c06d0479f73e53e3787667bce5aab26ad14ceb35c046b2f4c283a78a66ee8c4ac410a14ea6a6501371f09a45f69ce12869979c8042707f347c49e70a1318df74d7b3f173e0fbf666fd461dc26e0e6ac62b179a258efb8ee3d15feadd6d8f281602828431b96d";
		publicExponent = "010001";

		String data = "404142434445464748494A4B4C4D4E4F";

		// PublicKey pKey =
		// geneneratePublicKey(BytesUtil.hexToBytes(publicKey));
		PublicKey publicKey = RSAUtil.loadPublicKey(publicModulus,
				publicExponent, 16);
		// pKey = RSAUtil.loadPublicKey(BytesUtil.hexToBytes(publicKey));

		// 打印密钥信息
		printRSAKey((RSAKey) publicKey);

		String result = null;
		// result = publicKeyEncryptTest(publicKey, data);

		Cipher cipher = Cipher.getInstance("RSA/ECB/pkcs1padding");

		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		byte[] b = cipher.doFinal(BytesUtil.hexToBytes(data));
		result = BytesUtil.bytesToHex(b);

		System.out.println("===publicKeyEncryptTest result:" + result);

		// byte[] b = RSAUtil.publicKeyEncrypt(BytesUtil.hexToBytes(data),
		// BytesUtil.hexToBytes(publicKey));

		// byte[] b = RSAUtil.rsaCrypt(BytesUtil.hexToBytes(data), pKey,
		// AlgorithmEnum.RSA_NONE_PKCS1, true);
		// b = RSAUtil.pulibcKey(BytesUtil.hexToBytes(data), pKey,
		// AlgorithmEnum.RSA_NONE_PKCS1, true);

		// System.out.println("公钥加密:"+BytesUtil.bytesToHex(b));

		// b = RSAUtil.sign(data, privateKeyBytes);
		// System.out.println("公钥签名:"+BytesUtil.bytesToHex(b));

		// printPublicKeyInfo(pKey);
	}

	// 传入参数是DER编码的私钥内容,里面包涵模和指数
	public PrivateKey generatePrivateKey(byte[] key)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		KeySpec keySpec = new PKCS8EncodedKeySpec(key);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		return keyFactory.generatePrivate(keySpec);
	}

	// 传入参数是DER编码公钥内容,里面包涵模和指数
	public static PublicKey geneneratePublicKey(byte[] key)
			throws InvalidKeySpecException, NoSuchAlgorithmException,
			CertificateException {
		KeySpec keySpec = new X509EncodedKeySpec(key);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		return keyFactory.generatePublic(keySpec);

		// X509Certificate x509Certificate = X509Certificate.getInstance(key);
		// return x509Certificate.getPublicKey();
	}

	/**
	 * 打印RSAKey信息
	 * 
	 * @param rsaKey
	 * @return void
	 */
	public static void printRSAKey(RSAKey rsaKey) {
		System.out.println("RSAKey=" + rsaKey);
		System.out.println("Modulus.bitLength="
				+ rsaKey.getModulus().bitLength());
		System.out.println("Modulus(10)=" + rsaKey.getModulus().toString());
		System.out.println("Modulus(16)=" + rsaKey.getModulus().toString(16));

		if (rsaKey instanceof RSAPrivateKey) {
			RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) rsaKey;
			System.out.println("PrivateExponent.bitLength="
					+ rsaPrivateKey.getPrivateExponent().bitLength());
			System.out.println("PrivateExponent(10)="
					+ rsaPrivateKey.getPrivateExponent().toString());
			System.out.println("PrivateExponent(16)="
					+ rsaPrivateKey.getPrivateExponent().toString(16));
		}
		if (rsaKey instanceof RSAPublicKey) {
			RSAPublicKey rdaPublicKey = (RSAPublicKey) rsaKey;
			System.out.println("PublicExponent.bitLength="
					+ rdaPublicKey.getPublicExponent().bitLength());

			System.out.println("PublicExponent(10)="
					+ rdaPublicKey.getPublicExponent().toString());
			System.out.println("PublicExponent(16)="
					+ rdaPublicKey.getPublicExponent().toString(16));
		}
		System.out.println("RSAKey(base64)=" + getKeyString((Key) rsaKey));
	}

	// 打印私钥信息
	@Deprecated
	public static void printPrivateKeyInfo(PrivateKey privateKey) {
		RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) privateKey;

		System.out.println("RSAPrivateKey=" + rsaPrivateKey);
		System.out.println("Modulus.bitLength="
				+ rsaPrivateKey.getModulus().bitLength());
		System.out.println("Modulus(10)="
				+ rsaPrivateKey.getModulus().toString());
		System.out.println("Modulus(16)="
				+ rsaPrivateKey.getModulus().toString(16));

		System.out.println("PrivateExponent.bitLength="
				+ rsaPrivateKey.getPrivateExponent().bitLength());
		System.out.println("PrivateExponent(10)="
				+ rsaPrivateKey.getPrivateExponent().toString());
		System.out.println("PrivateExponent(16)="
				+ rsaPrivateKey.getPrivateExponent().toString(16));

		System.out.println("RSAKey(base64)="
				+ getKeyString((Key) rsaPrivateKey));
	}

	// 打印公钥信息
	@Deprecated
	public static void printPublicKeyInfo(PublicKey publicKey) {
		RSAPublicKey rsaPublicKey = (RSAPublicKey) publicKey;

		System.out.println("RSAPublicKey:" + rsaPublicKey);
		System.out.println("Modulus.bitLength="
				+ rsaPublicKey.getModulus().bitLength());
		System.out.println("Modulus=(10)"
				+ rsaPublicKey.getModulus().toString());
		System.out.println("Modulus=(16)"
				+ rsaPublicKey.getModulus().toString(16));

		System.out.println("PublicExponent.bitLength="
				+ rsaPublicKey.getPublicExponent().bitLength());
		System.out.println("PublicExponent(10)="
				+ rsaPublicKey.getPublicExponent().toString());
		System.out.println("PublicExponent(16)="
				+ rsaPublicKey.getPublicExponent().toString(16));

		System.out
				.println("RSAKey(base64)=" + getKeyString((Key) rsaPublicKey));
	}

	/**
	 * 得到密钥字符串（经过base64编码）
	 * 
	 * @return
	 */
	public static String getKeyString(Key key) {
		byte[] keyBytes = key.getEncoded();
		//come from :sun.misc.BASE64Encoder
		/*String s = (new BASE64Encoder()).encode(keyBytes);*/
		//return s;
		return "";
	}
}
