package com.bps.mph.util;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class Util {
	public static final String SANDBOX_TOKEN_URL = "https://r-hi2.cigna.com/mga/sps/oauth/oauth20/token";
	public static final String SANDBOX_AUTHORIZATION_URL = "https://r-hi2.cigna.com/mga/sps/oauth/oauth20/authorize";
	public static final String CLIENT_ID = "7aa3a815-0100-48bb-8c17-e991925ac621";
	public static final String CLIENT_SECRET = "e0e0e5d7-27e9-490b-ba8e-c425c127a5bb";
	public static final String REDIRECT_URL = "http://mphauthflow-env.eba-pwfbf2qx.ap-south-1.elasticbeanstalk.com/callback";
	public static final String CONSUMER_SANDBOX_ENDPOINT = "https://p-hi2.digitaledge.cigna.com/ConsumerAccess/v1-devportal";
	public static final String PROVIDER_SANDBOX_ENDPOINT = "https://p-hi2.digitaledge.cigna.com/ProviderDirectory/v1-devportal";

	public static boolean isSuccess(int statusCode) {
		if (statusCode >= 200 && statusCode <= 299) {
			return true;
		}
		return false;
	}

	public static int getRandom(int min, int max) {
		Random r = new Random();
		int result = r.nextInt(max - min) + min;
		return result;
	}

	public static boolean isValidURL(String url) {
		boolean isValid = false;
		if (url != null) {
			try {
				URI.create(url);
				isValid = true;
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
		}
		return isValid;
	}

	public static String encodeUrl(String url) {
		String encodedUrl = null;
		try {
			encodedUrl = URLEncoder.encode(url, StandardCharsets.UTF_8.toString());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return encodedUrl;
	}
	
	public static void main(String[] args) {
		String url = "https://r-hi2.cigna.com/mga/sps/oauth/oauth20/authorize?nonce=nonce_05aef024-0c08-401f-a3fc-7fcf4897168d&response_type=code&state=state_05aef024-0c08-401f-a3fc-7fcf4897168d&client_id=7aa3a815-0100-48bb-8c17-e991925ac621&scope=patient/*.read openid fhirUser&redirect_uri=http://mphauthflow-env.eba-pwfbf2qx.ap-south-1.elasticbeanstalk.com/callback";
		if(isValidURL(encodeUrl(url))) {
			System.out.println("Url is valid");
		} else {
			System.err.println("Url is invalid");
		}
	}
}
