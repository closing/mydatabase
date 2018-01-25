package mypack;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.ServletOutputStream;

import java.io.IOException;

import javax.imageio.ImageIO;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class ImageServlet extends HttpServlet {
	private Font font = new Font("Courier", Font.BOLD, 12);
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		doGet(request, response);
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String count = request.getParameter("count");
		if (count == null) {
			count = "1";
		}
		
		int len = count.length();
		response.setContentType("image/jpeg");
		ServletOutputStream out = response.getOutputStream();
		BufferedImage image = new BufferedImage(11*len, 16, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, 11*len, 16);
		g.setColor(Color.white);
		g.setFont(font);
		
		char c;
		for (int i=0; i<len; i++) {
			c=count.charAt(i);
			g.drawString(c+"", i*11*1, 12);
			g.drawLine((i+1)*11-1, 0, (i+1)*11-1,16);
		}
		if (ImageIO.write(image, "JPEG", out)) {
			System.out.println("Successful");
		}
		else {
			System.out.println("failed");
		}
		
		out.close();
	}
}
