package com.bps.mph.util;

import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.bps.mph.api.TokenRequest;
import com.bps.mph.api.TokenResponse;
import com.google.gson.Gson;

public class TokenUtility {
	public TokenResponse fetchAccessToken(String authorizationCode) {
		TokenResponse tokenResponse = null;
		try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
			String url = Util.SANDBOX_TOKEN_URL + "?client_id=" + Util.CLIENT_ID;
			System.out.println("Bhanu Token URL: " + url);
			String payload = getPayload(authorizationCode);
			System.out.println("Bhanu Token Payload: " + payload);
			HttpPost httpPost = new HttpPost(url);
			StringEntity entity = new StringEntity(payload, Charset.forName("UTF-8"));
			httpPost.setEntity(entity);
			httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
			try (CloseableHttpResponse httpResponse = client.execute(httpPost)) {
				if (Util.isSuccess(httpResponse.getStatusLine().getStatusCode())) {
					String str = EntityUtils.toString(httpResponse.getEntity());
					System.out.println("Bhanu token response: " + str);
					Gson gson = new Gson();
					tokenResponse = gson.fromJson(str, TokenResponse.class);
					System.out.println("Bhanu token response accessToken: " + tokenResponse.getAccess_token());
				} else {
					String str = EntityUtils.toString(httpResponse.getEntity());
					System.out.println("Bhanu token fetch failed: " +httpResponse.getStatusLine().getStatusCode() 
							+ " " + httpResponse.getStatusLine().getReasonPhrase()
							+ " > " + str);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return tokenResponse;
	}

	private String getPayload(String code) {
		String redirectURI = Util.REDIRECT_URL;
		String clientId = Util.CLIENT_ID;
		String secret = Util.CLIENT_SECRET;
		TokenRequest tokenRequest = new TokenRequest();
		tokenRequest.setCode(code);
		tokenRequest.setRedirect_uri(redirectURI);
		tokenRequest.setClient_id(clientId);
		tokenRequest.setClient_secret(secret);

		/*
		 * Gson gson = new Gson(); String payload = gson.toJson(tokenRequest);
		 */
		return tokenRequest.toString();
	}
}
