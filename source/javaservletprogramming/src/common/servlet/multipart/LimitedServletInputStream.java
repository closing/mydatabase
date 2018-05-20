package common.servlet.multipart;

import java.io.IOException;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;

public class LimitedServletInputStream extends ServletInputStream {

  private ServletInputStream in;
  private int totalExpected;

  private int totalRead = 0;

  public LimitedServletInputStream(ServletInputStream in, int totalExpected) {
    this.in = in;
    this.totalExpected = totalExpected;
  }

  public int readLine(byte b[], int off, int len) throws IOException {
    int result, left = totalExpected - totalRead;
    if (left <= 0) {
      return -1;
    } else {
      result = ((ServletInputStream) in).readLine(b, off, Math.min(left, len));
    }
    if (result > 0) {
      totalRead += result;
    }
    return result;
  }

  public int read() throws IOException {
    if (totalRead >= totalExpected) {
      return -1;
    }

    int result = in.read();
    if (result != -1) {
      totalRead++;
    }
    return result;
  }

  public int read(byte b[], int off, int len) throws IOException {
    int result, left = totalExpected - totalRead;
    if (left <= 0) {
      return -1;
    } else {
      result = in.read(b, off, Math.min(left, len));
    }
    if (result > 0) {
      totalRead += result;
    }
    return result;
  }

  // not supported
  public void setReadListener(ReadListener readListener) {
  }

  public boolean isReady() {
    return false;
  }

  public boolean isFinished() {
    return false;
  }
}
