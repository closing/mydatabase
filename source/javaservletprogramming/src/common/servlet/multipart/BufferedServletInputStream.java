package common.servlet.multipart;

import java.io.IOException;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;

public class BufferedServletInputStream extends ServletInputStream {

  private ServletInputStream in;
  private byte[] buf = new byte[64 * 1024]; // 64k
  private int count;
  private int pos;

  public BufferedServletInputStream(ServletInputStream in) {
    this.in = in;
  }

  private void fill() throws IOException {
    int i = in.read(buf, 0, buf.length);
    if (i > 0) {
      pos = 0;
      count = i;
    }
  }

  public int readLine(byte b[], int off, int len) throws IOException {
    int total = 0;
    if (len == 0) {
      return 0;
    }

    int avail = count - pos;
    if (avail <= 0) {
      fill();
      avail = count - pos;
      if (avail <= 0) {
        return -1;
      }
    }
    int copy = Math.min(len, avail);
    int eol = findeol(buf, pos, copy);
    if (eol != -1) {
      copy = eol;
    }
    System.arraycopy(buf, pos, b, off, copy);
    pos += copy;
    total += copy;

    while (total < len && eol == -1) {
      fill();
      avail = count - pos;
      if (avail <= 0) {
        return total;
      }
      copy = Math.min(len - total, avail);
      eol = findeol(buf, pos, copy);
      if (eol != -1) {
        copy = eol;
      }
      System.arraycopy(buf, pos, b, off + total, copy);
      pos += copy;
      total += copy;
    }
    return total;
  }

  private static int findeol(byte b[], int pos, int len) {
    int end = pos + len;
    int i = pos;
    while (i < end) {
      if (b[i++] == '\n') {
        return i - pos;
      }
    }
    return -1;
  }

  public int read() throws IOException {
    if (count <= pos) {
      fill();
      if (count <= pos) {
        return -1;
      }
    }
    return buf[pos++] & 0xff;
  }

  public int read(byte b[], int off, int len) throws IOException {
    int total = 0;
    while (total < len) {
      int avail = count - pos;
      if (avail <= 0) {
        fill();
        avail = count - pos;
        if (avail <= 0) {
          if (total > 0)
            return total;
          else
            return -1;
        }
      }
      int copy = Math.min(len - total, avail);
      System.arraycopy(buf, pos, b, off + total, copy);
      pos += copy;
      total += copy;
    }
    return total;
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
