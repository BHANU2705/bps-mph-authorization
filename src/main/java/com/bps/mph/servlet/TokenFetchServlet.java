package com.bps.mph.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpStatus;

import com.bps.mph.api.TokenResponse;
import com.bps.mph.util.TokenUtility;
import com.google.gson.Gson;

@WebServlet("/token")
public class TokenFetchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		TokenUtility tokenUtility = new TokenUtility();
		TokenResponse tokenResponse = tokenUtility.fetchAccessToken(request.getParameter("code"), response);
		String tokenResponseString = new Gson().toJson(tokenResponse);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.setStatus(HttpStatus.SC_OK);
		PrintWriter out = response.getWriter();
		out.print(tokenResponseString);
		out.flush();
	}
}
