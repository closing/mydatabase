package common.servlet.multipart;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.servlet.ServletInputStream;

public class FilePart extends Part {

  private String fileName;
  private String filePath;
  private String contentType;

  private PartInputStream partInput;

  private FileRenamePolicy policy;

  FilePart(String name, ServletInputStream in, String boundary, String contentType, String fileName, String filePath)
      throws IOException {
    super(name);
    this.fileName = fileName;
    this.filePath = filePath;
    this.contentType = contentType;
    partInput = new PartInputStream(in, boundary);
  }

  public void setRenamePolicy(FileRenamePolicy policy) {
    this.policy = policy;
  }

  public String getFileName() {
    return fileName;
  }

  public String getFilePath() {
    return filePath;
  }

  public String getContentType() {
    return contentType;
  }

  public InputStream getInputStream() {
    return partInput;
  }

  public long writeTo(File fileOrDirectory) throws IOException {
    long written = 0;

    OutputStream fileOut = null;
    try {
      // Only do something if this part contains a file
      if (fileName != null) {
        // Check if user supplied directory
        File file;
        if (fileOrDirectory.isDirectory()) {
          // Write it to that dir the user supplied, 
          // with the filename it arrived with
          file = new File(fileOrDirectory, fileName);
        } else {
          // Write it to the file the user supplied,
          // ignoring the filename it arrived with
          file = fileOrDirectory;
        }
        if (policy != null) {
          file = policy.rename(file);
          fileName = file.getName();
        }
        fileOut = new BufferedOutputStream(new FileOutputStream(file));
        written = write(fileOut);
      }
    } finally {
      if (fileOut != null)
        fileOut.close();
    }
    return written;
  }

  public long writeTo(OutputStream out) throws IOException {
    long size = 0;
    // Only do something if this part contains a file
    if (fileName != null) {
      // Write it out
      size = write(out);
    }
    return size;
  }

  long write(OutputStream out) throws IOException {
    // decode macbinary if this was sent
    if (contentType.equals("application/x-macbinary")) {
      out = new MacBinaryDecoderOutputStream(out);
    }
    long size = 0;
    int read;
    byte[] buf = new byte[8 * 1024];
    while ((read = partInput.read(buf)) != -1) {
      out.write(buf, 0, read);
      size += read;
    }
    return size;
  }

  public boolean isFile() {
    return true;
  }
}
