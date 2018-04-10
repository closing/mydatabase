package common.servlet;

import java.io.FilterOutputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;

public class Base64Encoder extends FilterOutputStream {

  private static final char[] chars = {
    'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
    'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
    'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd',
    'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
    'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x',
    'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7',
    '8', '9', '+', '/'
  };

  private int charCount;
  private int carryOver;

  public Base64Encoder(OutputStream out) {
    super(out);
  }

  public void write(int b) throws IOException {
    // Take 24-bits from three octets, translate into four encoded chars
    // Break lines at 76 chars
    // If necessary, pad with 0 bits on the right at the end
    // Use = signs as padding at the end to ensure encodedLength % 4 == 0

    // Remove the sign bit,
    // thanks to Christian Schweingruber <chrigu@lorraine.ch>
    if (b < 0) {
      b += 256;
    }

    // First byte use first six bits, save last two bits
    if (charCount % 3 == 0) {
      int lookup = b >> 2;
      carryOver = b & 3;        // last two bits
      out.write(chars[lookup]);
    }
    // Second byte use previous two bits and first four new bits,
    // save last four bits
    else if (charCount % 3 == 1) {
      int lookup = ((carryOver << 4) + (b >> 4)) & 63;
      carryOver = b & 15;       // last four bits
      out.write(chars[lookup]);
    }
    // Third byte use previous four bits and first two new bits,
    // then use last six new bits
    else if (charCount % 3 == 2) {
      int lookup = ((carryOver << 2) + (b >> 6)) & 63;
      out.write(chars[lookup]);
      lookup = b & 63;          // last six bits
      out.write(chars[lookup]);
      carryOver = 0;
    }
    charCount++;

    // Add newline every 76 output chars (that's 57 input chars)
    if (charCount % 57 == 0) {
      out.write('\n');
    }
  }
	
  public void write(byte[] buf, int off, int len) throws IOException {
    // This could of course be optimized
    for (int i = 0; i < len; i++) {
      write(buf[off + i]);
    }
  }
	
  public void close() throws IOException {
    // Handle leftover bytes
    if (charCount % 3 == 1) {  // one leftover
      int lookup = (carryOver << 4) & 63;
      out.write(chars[lookup]);
      out.write('=');
      out.write('=');
    }
    else if (charCount % 3 == 2) {  // two leftovers
      int lookup = (carryOver << 2) & 63;
      out.write(chars[lookup]);
      out.write('=');
    }
    super.close();
  }
	
  public static String encode(String unencoded) {
    byte[] bytes = null;
    try {
      bytes = unencoded.getBytes("8859_1");
    }
    catch (UnsupportedEncodingException ignored) { }
    return encode(bytes);
  }
	
  public static String encode(byte[] bytes) {
    ByteArrayOutputStream out = 
      new ByteArrayOutputStream((int) (bytes.length * 1.37));
    Base64Encoder encodedOut = new Base64Encoder(out);
    
    try {
      encodedOut.write(bytes);
      encodedOut.close();

      return out.toString("8859_1");
    }
    catch (IOException ignored) { return null; }
  }

  public static void main(String[] args) throws Exception {
    if (args.length != 1) {
      System.err.println(
        "Usage: java com.oreilly.servlet.Base64Encoder fileToEncode");
      return;
    }

    Base64Encoder encoder = null;
    BufferedInputStream in = null;
    try {
      encoder = new Base64Encoder(System.out);
      in = new BufferedInputStream(new FileInputStream(args[0]));

      byte[] buf = new byte[4 * 1024];  // 4K buffer
      int bytesRead;
      while ((bytesRead = in.read(buf)) != -1) {
        encoder.write(buf, 0, bytesRead);
      }
    }
    finally {
      if (in != null) in.close();
      if (encoder != null) encoder.close();
    }
  }
}
