package com;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import model.Reader;

/**
 * Servlet implementation class ReaderAPI
 */
@WebServlet("/ReaderAPI")
public class ReaderAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;
		Reader readerObj = new Reader();
		String stsMsg = "";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReaderAPI() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    // Convert request parameters to a Map
 	private static Map getParasMap(HttpServletRequest request)
 	{
 		Map<String, String> map = new HashMap<String, String>();
 		try
 		{
 			Scanner scanner = new Scanner(request.getInputStream(), "UTF-8");
 			String queryString = scanner.hasNext() ?
 								scanner.useDelimiter("\\A").next() : "";
 			scanner.close();
 			
 			String[] params = queryString.split("&");
 			for (String param : params)
 			{
 				String[] p = param.split("=");
 				map.put(p[0], p[1]);
 			}
 		}
 		catch (Exception e)
 		{
 		}
 		return map;
 	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
						throws ServletException, IOException
	{
		String output = readerObj.insertReader(request.getParameter("readerName"),
							request.getParameter("readerEmail"),
							request.getParameter("readerPhone"),
							request.getParameter("readerPassword"));
		response.getWriter().write(output);
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			 			throws ServletException, IOException
	{
		Map paras = getParasMap(request);
		
		String output = readerObj.updateReader(paras.get("hidReaderIDSave").toString(),
							paras.get("readerName").toString(),
							paras.get("readerEmail").toString(),
							paras.get("readerPhone").toString(),
							paras.get("readerPassword").toString());
		response.getWriter().write(output);
	}
			
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			 			throws ServletException, IOException
	{
		Map paras = getParasMap(request);
		
		String output = readerObj.deleteReader(paras.get("readerID").toString());
		
		response.getWriter().write(output);
	}
			
}