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

/**
 * Servlet implementation class uploadFile
 */
@WebServlet("/uploadFile")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
maxFileSize = 1024 * 1024 * 10, // 10MB
maxRequestSize = 1024 * 1024 * 50) // 50MB
public class uploadFile extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public uploadFile() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		todo(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		todo(request, response);
	}

	private void todo(HttpServletRequest request, HttpServletResponse response){
		try {
			String appPath = request.getServletContext().getRealPath("");
			appPath = appPath.replace('\\', '/');
			
			Part part = request.getPart("filename");
			if (part!=null) {
				part.write(appPath+"/test");
			}
			File tmpFile = new File(appPath+"/test");
			System.out.println(tmpFile.getAbsolutePath());
			System.out.println(tmpFile.exists());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
//		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
//		if (isMultipart) {
//			FileItemFactory factory = new DiskFileItemFactory();
//			ServletFileUpload upload = new ServletFileUpload(factory);
//			String UPLOAD_DIRECTORY = getServletContext().getRealPath(File.separator + "Upload" + File.separator);
//			if (!new File(UPLOAD_DIRECTORY).exists()) {
//				new File(UPLOAD_DIRECTORY).mkdir();
//			}
//			try {
//				List<FileItem> multiparts = upload.parseRequest((RequestContext) request);
//				for (FileItem item : multiparts) {
//					if (!item.isFormField()) {
//						String name = new File(item.getName()).getName();
//						item.write(new File(UPLOAD_DIRECTORY + File.separator + name));
//						System.out.println("Folder file upload on server : " + UPLOAD_DIRECTORY);
//						System.out.println("File upload success");
//					}
//				}
//			} catch (Exception e) {
//				System.out.println("Exception : " + e.toString());
//				System.out.println("File upload failed");
//			}
//		}
	}
}
