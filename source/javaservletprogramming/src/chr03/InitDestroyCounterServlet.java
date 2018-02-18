package chr03;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;

public class InitDestroyCounterServlet extends HttpServlet {

    private int counter;

    public void init() throws ServletException {
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        try {
            fileReader = new FileReader("InitDestroyCounterServlet.initial");
            bufferedReader = new BufferedReader(fileReader);
            String initial = bufferedReader.readLine();
            counter = Integer.parseInt(initial);
            return;
        }catch (FileNotFoundException ignore) {}
        catch (IOException e) {}
        catch (NumberFormatException ignored) {}
        finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            }catch(IOException ignore){}
        }
        String initial = getInitParameter("initial");
        try {
            counter = Integer.parseInt(initial);
            return;
        }catch(NumberFormatException e) {
            counter =0;
        }
    }

    public void destroy(){
        super.destroy();
        saveState();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain;charset=utf-8");

        counter++;
        PrintWriter out = response.getWriter();
        out.println("Since the beginning, this servlet has been accessed ");
        out.println(counter + " times.");
    }
    public void saveState() {
        FileWriter writer = null;
        PrintWriter printWriter = null;
        try{
            writer = new FileWriter("InitDestroyCounterServlet.initial");
            printWriter = new PrintWriter(writer);
            printWriter.println(counter);
            return;
        }catch(IOException e){}
        finally {
            if(printWriter != null ){
                printWriter.close();
            }
        }
    }
}
