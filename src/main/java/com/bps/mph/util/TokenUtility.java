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
			String url = Util.encodeUrl(Util.SANDBOX_TOKEN_URL);
			String payload = getPayload(authorizationCode);
			HttpPost httpPost = new HttpPost(url);
			StringEntity entity = new StringEntity(payload, Charset.forName("UTF-8"));
			httpPost.setEntity(entity);
			httpPost.setHeader("Content-Type", "application/json");
			try (CloseableHttpResponse httpResponse = client.execute(httpPost)) {
				if (Util.isSuccess(httpResponse.getStatusLine().getStatusCode())) {
					String str = EntityUtils.toString(httpResponse.getEntity());
					Gson gson = new Gson();
					tokenResponse = gson.fromJson(str, TokenResponse.class);
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

		Gson gson = new Gson();
		String payload = gson.toJson(tokenRequest);
		return payload;
	}
}
