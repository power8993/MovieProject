package common.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet (
	description = "사용자가 웹에서 *.mp 을 했을 경우 이 서블릿이 응답을 해주도록 한다.",
	urlPatterns = {"*.mp"},
	initParams = { 
		@WebInitParam(name = "propertyConfig", value = "C:\\git\\MovieProject\\MovieProject\\src\\main\\webapp\\WEB-INF\\Command.Properties", description = "*.mp 에 대한 클래스의 매핑파일") 
	}
)
public class FrontController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private Map<String, Object> cmdMap = new HashMap<>();
	
	public void init(ServletConfig config) throws ServletException {
		
		
		FileInputStream fis = null;
		
		String props = config.getInitParameter("propertyConfig");
		
		try {
			fis = new FileInputStream(props);
			
			Properties pr = new Properties();
			
			pr.load(fis);
			
			Enumeration<Object> en = pr.keys();
			
			while(en.hasMoreElements()) {
				
				String key = (String)en.nextElement();
				
				String className = pr.getProperty(key);
				
				if(className != null) {
					
					className = className.trim();
					
					Class <?> cls = Class.forName(className);
					
					Constructor<?> constrt = cls.getDeclaredConstructor();
					
					Object obj = constrt.newInstance();
					
					cmdMap.put(key, obj);
					
				} // end of if(className != null)-----------------------------------------------------------
				
			} // end of while(en.hasMoreElements())-------------------------------
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println(">> 문자열로 명명되어진 클래스가 존재하지 않습니다. <<");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	} // end of public void init(ServletConfig config)----------------------------------------------
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String uri = request.getRequestURI();
		
		String key = uri.substring(request.getContextPath().length());
		
		AbstractController action = (AbstractController)cmdMap.get(key);
		
		if(action == null) {
			System.out.println(">>> " + key + " 은 URI 패턴에 매핑된 클래스는 없습니다.");
		}
		else {
			try {
				action.execute(request, response);
				
				boolean bool = action.isRedirect();
				String viewPage = action.getViewPage();
				
				if(!bool) {
					
					if(viewPage != null) {
						RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
						dispatcher.forward(request, response);
					}
					
				}
				else {
					
					if(viewPage != null) {
						response.sendRedirect(viewPage);
					}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
