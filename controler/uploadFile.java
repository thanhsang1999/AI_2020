package controler;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;


import model.NhanDienChoMeo;
import model.PetType;

@WebServlet("/uploadFile")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
		maxFileSize = 1024 * 1024 * 10, // 10MB
		maxRequestSize = 1024 * 1024 * 50) // 50MB
public class uploadFile extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public uploadFile() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		todo(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		todo(request, response);
	}

	private void todo(HttpServletRequest request, HttpServletResponse response) {
		try {
		File fileInput = GetFileInput(request, response);
		NhanDienChoMeo nChoMeo = new NhanDienChoMeo();
			PetType petType = nChoMeo.detectCat(fileInput);
			response.getWriter().write(petType.toString());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	private File GetFileInput(HttpServletRequest request, HttpServletResponse response) {
		try {
			String appPath = request.getServletContext().getRealPath("");
			appPath = appPath.replace('\\', '/');

			Part part = request.getPart("fileInput");
			if (part != null) {
				part.write(appPath + "/fileInput");
			}
			File tmpFile = new File(appPath + "/fileInput");
			return tmpFile;
		} catch (Exception e) {
			return null;
		}
	}


}
