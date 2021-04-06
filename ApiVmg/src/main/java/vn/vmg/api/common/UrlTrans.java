package vn.vmg.api.common;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Map;

public class UrlTrans {
	public static int DEFAULT_TIME_OUT = 60000;
	// trustAllCerts()
	static {
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			@Override
			public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
				// Not implemented
			}

			@Override
			public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
				// Not implemented
			}
		} };

		try {
			SSLContext sc = SSLContext.getInstance("TLS");

			sc.init(null, trustAllCerts, new java.security.SecureRandom());

			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	public UrlTrans() {
	}

	/**
	 * ==============================================================
	 * ========================POST METHOD===========================
	 * ==============================================================
	 */
	public static String postUrl(String urlReq, String body) throws Exception {
		return postUrl(urlReq, body, DEFAULT_TIME_OUT);
	}

	public static String postUrl(String urlReq, String body, int timeOut) throws Exception {
		if (urlReq.startsWith("http://")) {
			return sendHttpUrl(urlReq, HttpMethod.POST,null, body, timeOut);
		}
		return sendHttps(urlReq, HttpMethod.POST,null, body, timeOut);
	}
	
	public static String postUrl(String urlReq, Map<String,String> headPost, String body) throws Exception {
		return postUrl(urlReq,headPost, body, DEFAULT_TIME_OUT);
	}
	
	public static String postUrl(String urlReq, Map<String,String> headPost, String body, int timeOut) throws Exception {
		if (urlReq.startsWith("http://")) {
			return sendHttpUrl(urlReq, HttpMethod.POST,headPost, body, timeOut);
		}
		return sendHttps(urlReq, HttpMethod.POST,headPost, body, timeOut);
	}
	
	/**
	 * ==============================================================
	 * ========================GET METHOD===========================
	 * ==============================================================
	 */
	
	public static String getUrl(String urlReq) throws Exception {
		return getUrl(urlReq, DEFAULT_TIME_OUT);
	}

	public static String getUrl(String urlReq, int timeOut) throws Exception {
		if (urlReq.startsWith("http://")) {
			return sendHttpUrl(urlReq, HttpMethod.GET,null, null, timeOut);
		}
		return sendHttps(urlReq, HttpMethod.GET,null, null, timeOut);
	}
	
	public static String getUrl(String urlReq, Map<String,String> headPost) throws Exception {
		return getUrl(urlReq,headPost, DEFAULT_TIME_OUT);
	}
	
	public static String getUrl(String urlReq, Map<String,String> headPost, int timeOut) throws Exception {
		if (urlReq.startsWith("http://")) {
			return sendHttpUrl(urlReq, HttpMethod.GET,headPost, null, timeOut);
		}
		return sendHttps(urlReq, HttpMethod.GET,headPost, null, timeOut);
	}
	
	/**
	 * ==============================================================
	 * ========================GLOBAL METHOD REQUEST HTTP or HTTPs===
	 * ==============================================================
	 */
	public static String sendHttps(String urlReq, HttpMethod method, Map<String,String> headPost, String body, int timeOut) throws Exception {
		HttpsURLConnection conn = null;
		OutputStream os = null;
		InputStreamReader in = null;
		BufferedReader br = null;
		StringBuilder resStr = new StringBuilder();

		try {
			URL url = new URL(urlReq);
			conn = (HttpsURLConnection) url.openConnection();
			
			//trust all HTTPs
			conn.setHostnameVerifier(new HostnameVerifier() {
				@Override
				public boolean verify(String arg0, SSLSession arg1) {
					return true;
				}
			});
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setConnectTimeout(timeOut);
			conn.setReadTimeout(timeOut);
			conn.setRequestMethod(method.toString());
			if (method == HttpMethod.POST) {
				conn.setRequestProperty("Content-type", "application/json");
			}
			if(headPost !=null && !headPost.isEmpty()) {
				for(String key: (new ArrayList<>(headPost.keySet()))) {
					conn.setRequestProperty(key,headPost.get(key));
				}
			}

			if (body != null) {
				os = conn.getOutputStream();
				os.write(body.getBytes());
				os.flush();
			}
			
			if (conn.getResponseCode() != HttpURLConnection.HTTP_ACCEPTED
					&& conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new Exception(
						"Failed : HTTPS error code : " + conn.getResponseCode() + " " + conn.getResponseMessage());
			}

			in = new InputStreamReader(conn.getInputStream());
			br = new BufferedReader(in);

			String output;
			while ((output = br.readLine()) != null) {
				resStr.append(output);
			}

			return resStr.toString();
		} finally {
			closeBufferedReader(br);
			closeInputStreamReader(in);
			closeOutputStream(os);
			closeHttpsURLConnection(conn);
		}
	}

	public static String sendHttpUrl(String urlReq, HttpMethod method, Map<String,String> headPost, String body, int timeOut) throws Exception {
		HttpURLConnection conn = null;
		OutputStream os = null;
		InputStreamReader in = null;
		BufferedReader br = null;
		StringBuilder resStr = new StringBuilder();

		try {
			URL url = new URL(urlReq);
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setConnectTimeout(timeOut);
			conn.setReadTimeout(timeOut);
			conn.setRequestMethod(method.toString());
			if (method == HttpMethod.POST) {
				conn.setRequestProperty("Content-type", "application/json");
			}
			
			if(headPost !=null && !headPost.isEmpty()) {
				for(String key: (new ArrayList<>(headPost.keySet()))) {
					conn.setRequestProperty(key,headPost.get(key));
				}
			}

			if (body != null) {
				os = conn.getOutputStream();
				os.write(body.getBytes());
				os.flush();
			}

			if (conn.getResponseCode() != HttpURLConnection.HTTP_ACCEPTED
					&& conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			in = new InputStreamReader(conn.getInputStream());
			br = new BufferedReader(in);

			String output;
			while ((output = br.readLine()) != null) {
				resStr.append(output);
			}

			return resStr.toString();
		} finally {
			closeBufferedReader(br);
			closeInputStreamReader(in);
			closeOutputStream(os);
			closeHttpURLConnection(conn);
		}

	}

	private static void closeHttpsURLConnection(HttpsURLConnection con) {
		try {
			if (con != null)
				con.disconnect();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private static void closeHttpURLConnection(HttpURLConnection con) {
		try {
			if (con != null)
				con.disconnect();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private static void closeOutputStream(OutputStream in) {
		try {
			if (in != null)
				in.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private static void closeInputStreamReader(InputStreamReader in) {
		try {
			if (in != null)
				in.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private static void closeBufferedReader(BufferedReader in) {
		try {
			if (in != null)
				in.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static void main(String[] args) throws Exception {
		System.out.println("==> " + UrlTrans.postUrl("https://127.0.0.1:8088/asdasd", "", 60000));
	}
	
	
	public static enum HttpMethod{
		POST,GET
	}

}
