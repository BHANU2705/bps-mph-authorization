package com.bps.mph.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.bps.mph.api.TokenResponse;
import com.bps.mph.util.TokenUtility;
import com.bps.mph.util.Util;

@WebServlet("/data")
public class DataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String type = request.getParameter("type");
		String authCode = request.getParameter("authCode");
		TokenUtility tokenUtility = new TokenUtility();
		TokenResponse tokenResponse = tokenUtility.fetchAccessToken(authCode);
		String url = null;
		if ("consumer".equals(type)) {
			url = Util.CONSUMER_SANDBOX_ENDPOINT + "/Patient/" + tokenResponse.getPatient();
		} else if ("provider".equals(type)) {
			url = Util.PROVIDER_SANDBOX_ENDPOINT + "/HealthcareService/BurrClinicServices";
		}
		
		try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
			HttpGet httpGet = new HttpGet(url);
			httpGet.setHeader("Authorization", "Bearer " + tokenResponse.getAccess_token());
			try (CloseableHttpResponse httpResponse = client.execute(httpGet)) {
				if (Util.isSuccess(httpResponse.getStatusLine().getStatusCode())) {
					String str = EntityUtils.toString(httpResponse.getEntity());
					System.out.println("Bhanu got response: " + str);
					response.setContentType("application/json");
					response.setCharacterEncoding("UTF-8");
					response.setStatus(HttpStatus.SC_OK);
					PrintWriter out = response.getWriter();
					out.print(str);
					out.flush();
				} else {
					response.sendError(httpResponse.getStatusLine().getStatusCode(), httpResponse.getStatusLine().getReasonPhrase());
				}
			}
		}
	}
}
