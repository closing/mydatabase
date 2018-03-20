package chr04;

import java.io.IOException;
import java.io.FileNotFoundException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.servlet.ServletUtils;

public class ViewFileServlet extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // Use a ServletOutputStream because we may pass binary information
        ServletOutputStream out = res.getOutputStream();

        // Get the file to view
        String file = req.getPathTranslated();

        // No file, nothing to view
        if (file == null) {
            out.println("No file to view");
            return;
        }

        // Get and set the type of the file
        String contentType = getServletContext().getMimeType(file);
        res.setContentType(contentType);

        // Return the file
        try {
            ServletUtils.returnFile(file, out);
        } catch (FileNotFoundException e) {
            out.println("File not found");
        } catch (IOException e) {
            out.println("Problem sending file: " + e.getMessage());
        }
    }
}
