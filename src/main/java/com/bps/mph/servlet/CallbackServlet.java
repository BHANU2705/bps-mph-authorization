package com.bps.mph.servlet; 

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/callback")
public class CallbackServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public CallbackServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String msg = "Bhanu received this code from Cigna: " + request.getQueryString();
		System.out.println(msg);
		response.getWriter().append(msg);
	}

}
