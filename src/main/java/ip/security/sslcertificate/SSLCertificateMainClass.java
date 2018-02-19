package ip.security.sslcertificate;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class SSLCertificateMainClass {
	
	public static void main(String[] args) {
		
		String url = "https://staging.api.investfit.com.au/api/v0.1/report/ifc-344/interactive";
		RestTemplate restTemplate = getRestTemplate();
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("username", "Agent@csc.com");
		httpHeaders.add("profileId", "12345");
		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String, Object>>(
				httpHeaders);
		try {
			ResponseEntity<?> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
			System.out.println("Response HTTP Code : " + response.getStatusCode());
			System.out.println("String output: " + response.getBody());
		} catch (HttpStatusCodeException e) {
			e.printStackTrace();
		} catch (RestClientException e) {
			e.printStackTrace();
		} 
	}
	
	public static RestTemplate getRestTemplate(){
		TrustStrategy acceptingTrustStrategy = new TrustStrategy() {
			@Override
			public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				return true;
			}
		};
		RestTemplate restTemplate = null;
	    SSLContext sslContext;
		try {
			sslContext = SSLContexts.custom()
			                .loadTrustMaterial(null, acceptingTrustStrategy)
			                .build();
			SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);

		    CloseableHttpClient httpClient = HttpClients.custom()
		                    .setSSLSocketFactory(csf)
		                    .build();

		    HttpComponentsClientHttpRequestFactory requestFactory =
		                    new HttpComponentsClientHttpRequestFactory();
		    requestFactory.setConnectTimeout(5 * 60 * 1000);
			requestFactory.setReadTimeout(5 * 60 * 1000);
		    requestFactory.setHttpClient(httpClient);
		    restTemplate = new RestTemplate(requestFactory);
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		}
		return restTemplate;
	}
	
}
