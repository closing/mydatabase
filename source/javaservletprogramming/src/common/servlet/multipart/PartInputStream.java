package common.servlet.multipart;

import java.io.FilterInputStream;
import java.io.IOException;
import javax.servlet.ServletInputStream;

public class PartInputStream extends FilterInputStream {
  private String boundary;
  private byte[] buf = new byte[64 * 1024]; // 64k

  private int count;
  private int pos;
  private boolean eof;

  PartInputStream(ServletInputStream in, String boundary) throws IOException {
    super(in);
    this.boundary = boundary;
  }

  private void fill() throws IOException {
    if (eof)
      return;

    // as long as we are not just starting up
    if (count > 0) {
      // if the caller left the requisite amount spare in the buffer
      if (count - pos == 2) {
        // copy it back to the start of the buffer
        System.arraycopy(buf, pos, buf, 0, count - pos);
        count -= pos;
        pos = 0;
      } else {
        // should never happen, but just in case
        throw new IllegalStateException("fill() detected illegal buffer state");
      }
    }

    // Try and fill the entire buffer, starting at count, line by line
    // but never read so close to the end that we might split a boundary
    // Thanks to Tony Chu, tony.chu@brio.com, for the -2 suggestion.
    int read = 0;
    int boundaryLength = boundary.length();
    int maxRead = buf.length - boundaryLength - 2; // -2 is for /r/n
    while (count < maxRead) {
      // read a line
      read = ((ServletInputStream) in).readLine(buf, count, buf.length - count);
      // check for eof and boundary
      if (read == -1) {
        throw new IOException("unexpected end of part");
      } else {
        if (read >= boundaryLength) {
          eof = true;
          for (int i = 0; i < boundaryLength; i++) {
            if (boundary.charAt(i) != buf[count + i]) {
              // Not the boundary!
              eof = false;
              break;
            }
          }
          if (eof) {
            break;
          }
        }
      }
      // success
      count += read;
    }
  }

  public int read() throws IOException {
    if (count - pos <= 2) {
      fill();
      if (count - pos <= 2) {
        return -1;
      }
    }
    return buf[pos++] & 0xff;
  }

  public int read(byte b[]) throws IOException {
    return read(b, 0, b.length);
  }

  public int read(byte b[], int off, int len) throws IOException {
    int total = 0;
    if (len == 0) {
      return 0;
    }

    int avail = count - pos - 2;
    if (avail <= 0) {
      fill();
      avail = count - pos - 2;
      if (avail <= 0) {
        return -1;
      }
    }
    int copy = Math.min(len, avail);
    System.arraycopy(buf, pos, b, off, copy);
    pos += copy;
    total += copy;

    while (total < len) {
      fill();
      avail = count - pos - 2;
      if (avail <= 0) {
        return total;
      }
      copy = Math.min(len - total, avail);
      System.arraycopy(buf, pos, b, off + total, copy);
      pos += copy;
      total += copy;
    }
    return total;
  }

  public int available() throws IOException {
    int avail = (count - pos - 2) + in.available();
    // Never return a negative value
    return (avail < 0 ? 0 : avail);
  }

  public void close() throws IOException {
    if (!eof) {
      while (read(buf, 0, buf.length) != -1)
        ; // do nothing
    }
  }
}
