package chr03;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PrimeSearcherServlet extends HttpServlet implements Runnable {

    private long lastPrime = 0l;
    private Date lastPrimeModified = new Date();
    private Thread searcher;

    public void init() throws ServletException {
        searcher = new Thread(this);
        searcher.setPriority(Thread.MIN_PRIORITY);
        searcher.start();
    }

    public void destroy() {
        searcher.stop();
    }

    public void run() {
        long candidate = 1000000000000001L;
        while (true) {
            if (isPrime(candidate)) {
                lastPrime = candidate;
                lastPrimeModified = new Date();
            }
            candidate += 2;
            try {
                searcher.sleep(200);
            } catch (InterruptedException ignored) {
            }
        }
    }

    private static boolean isPrime(long candidate) {
        long sqrt = (long) Math.sqrt(candidate);
        for (long i = 3; i <= sqrt; i += 2) {
            if (candidate % i == 0) {
                return false;
            }
        }
        return true;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain;charset=utf-8");

        PrintWriter out = response.getWriter();
        if (lastPrime==0) {
            out.println("Still searching for first prime...");
        }else {
            out.println("The last prime discovered was " + lastPrime);
            out.println(" at "+ lastPrimeModified);
        }
    }
}
