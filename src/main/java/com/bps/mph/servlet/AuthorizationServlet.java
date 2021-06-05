package com.bps.mph.servlet;

import java.io.IOException;
import java.text.MessageFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bps.mph.util.Util;

@WebServlet("/authorize")
public class AuthorizationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String url = Util.SANDBOX_AUTHORIZATION_URL;
		int random = Util.getRandom(0, 10);
		String nonce = "nonce_" + random;
		String state = "state_" + random;
		String clientId = Util.CLIENT_ID;
		String redirectUrl = Util.REDIRECT_URL;

		String queryParamsFormat = "?nonce={0}&response_type=code&state={1}&client_id={2}&scope=patient/*.read%20openid%20fhirUser&redirect_uri={3}";
		String queryParam = MessageFormat.format(queryParamsFormat, nonce, state, clientId, redirectUrl);
		String finalUrl = url + queryParam;
		System.out.println("Authorize URL: " + finalUrl);
		response.sendRedirect(finalUrl);  
	}
}
