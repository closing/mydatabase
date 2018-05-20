package common.servlet;

import java.io.IOException;
import java.io.File;
import java.util.Hashtable;
import java.util.Vector;
import java.util.Enumeration;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpUtils;

import common.servlet.multipart.MultipartParser;
import common.servlet.multipart.Part;
import common.servlet.multipart.FilePart;
import common.servlet.multipart.ParamPart;
import common.servlet.multipart.FileRenamePolicy;

public class MultipartRequest {

  private static final int DEFAULT_MAX_POST_SIZE = 1024 * 1024; // 1 Meg

  protected Hashtable parameters = new Hashtable(); // name - Vector of values
  protected Hashtable files = new Hashtable(); // name - UploadedFile

  public MultipartRequest(HttpServletRequest request, String saveDirectory) throws IOException {
    this(request, saveDirectory, DEFAULT_MAX_POST_SIZE);
  }

  public MultipartRequest(HttpServletRequest request, String saveDirectory, int maxPostSize) throws IOException {
    this(request, saveDirectory, maxPostSize, null, null);
  }

  public MultipartRequest(HttpServletRequest request, String saveDirectory, String encoding) throws IOException {
    this(request, saveDirectory, DEFAULT_MAX_POST_SIZE, encoding, null);
  }

  public MultipartRequest(HttpServletRequest request, String saveDirectory, int maxPostSize, FileRenamePolicy policy)
      throws IOException {
    this(request, saveDirectory, maxPostSize, null, policy);
  }

  public MultipartRequest(HttpServletRequest request, String saveDirectory, int maxPostSize, String encoding)
      throws IOException {
    this(request, saveDirectory, maxPostSize, encoding, null);
  }

  public MultipartRequest(HttpServletRequest request, String saveDirectory, int maxPostSize, String encoding,
      FileRenamePolicy policy) throws IOException {
    // Sanity check values
    if (request == null)
      throw new IllegalArgumentException("request cannot be null");
    if (saveDirectory == null)
      throw new IllegalArgumentException("saveDirectory cannot be null");
    if (maxPostSize <= 0) {
      throw new IllegalArgumentException("maxPostSize must be positive");
    }

    // Save the dir
    File dir = new File(saveDirectory);

    // Check saveDirectory is truly a directory
    if (!dir.isDirectory())
      throw new IllegalArgumentException("Not a directory: " + saveDirectory);

    // Check saveDirectory is writable
    if (!dir.canWrite())
      throw new IllegalArgumentException("Not writable: " + saveDirectory);

    // Parse the incoming multipart, storing files in the dir provided, 
    // and populate the meta objects which describe what we found
    MultipartParser parser = new MultipartParser(request, maxPostSize, true, true, encoding);

    // Some people like to fetch query string parameters from
    // MultipartRequest, so here we make that possible.  Thanks to 
    // Ben Johnson, ben.johnson@merrillcorp.com, for the idea.
    if (request.getQueryString() != null) {
      // Let HttpUtils create a name->String[] structure
      Hashtable queryParameters = HttpUtils.parseQueryString(request.getQueryString());
      // For our own use, name it a name->Vector structure
      Enumeration queryParameterNames = queryParameters.keys();
      while (queryParameterNames.hasMoreElements()) {
        Object paramName = queryParameterNames.nextElement();
        String[] values = (String[]) queryParameters.get(paramName);
        Vector newValues = new Vector();
        for (int i = 0; i < values.length; i++) {
          newValues.add(values[i]);
        }
        parameters.put(paramName, newValues);
      }
    }

    Part part;
    while ((part = parser.readNextPart()) != null) {
      String name = part.getName();
      if (name == null) {
        throw new IOException("Malformed input: parameter name missing (known Opera 7 bug)");
      }
      if (part.isParam()) {
        // It's a parameter part, add it to the vector of values
        ParamPart paramPart = (ParamPart) part;
        String value = paramPart.getStringValue();
        Vector existingValues = (Vector) parameters.get(name);
        if (existingValues == null) {
          existingValues = new Vector();
          parameters.put(name, existingValues);
        }
        existingValues.addElement(value);
      } else if (part.isFile()) {
        // It's a file part
        FilePart filePart = (FilePart) part;
        String fileName = filePart.getFileName();
        if (fileName != null) {
          filePart.setRenamePolicy(policy); // null policy is OK
          // The part actually contained a file
          filePart.writeTo(dir);
          files.put(name,
              new UploadedFile(dir.toString(), filePart.getFileName(), fileName, filePart.getContentType()));
        } else {
          // The field did not contain a file
          files.put(name, new UploadedFile(null, null, null, null));
        }
      }
    }
  }

  public MultipartRequest(ServletRequest request, String saveDirectory) throws IOException {
    this((HttpServletRequest) request, saveDirectory);
  }

  public MultipartRequest(ServletRequest request, String saveDirectory, int maxPostSize) throws IOException {
    this((HttpServletRequest) request, saveDirectory, maxPostSize);
  }

  public Enumeration getParameterNames() {
    return parameters.keys();
  }

  public Enumeration getFileNames() {
    return files.keys();
  }

  public String getParameter(String name) {
    try {
      Vector values = (Vector) parameters.get(name);
      if (values == null || values.size() == 0) {
        return null;
      }
      String value = (String) values.elementAt(values.size() - 1);
      return value;
    } catch (Exception e) {
      return null;
    }
  }

  public String[] getParameterValues(String name) {
    try {
      Vector values = (Vector) parameters.get(name);
      if (values == null || values.size() == 0) {
        return null;
      }
      String[] valuesArray = new String[values.size()];
      values.copyInto(valuesArray);
      return valuesArray;
    } catch (Exception e) {
      return null;
    }
  }

  public String getFilesystemName(String name) {
    try {
      UploadedFile file = (UploadedFile) files.get(name);
      return file.getFilesystemName(); // may be null
    } catch (Exception e) {
      return null;
    }
  }

  public String getOriginalFileName(String name) {
    try {
      UploadedFile file = (UploadedFile) files.get(name);
      return file.getOriginalFileName(); // may be null
    } catch (Exception e) {
      return null;
    }
  }

  public String getContentType(String name) {
    try {
      UploadedFile file = (UploadedFile) files.get(name);
      return file.getContentType(); // may be null
    } catch (Exception e) {
      return null;
    }
  }

  public File getFile(String name) {
    try {
      UploadedFile file = (UploadedFile) files.get(name);
      return file.getFile(); // may be null
    } catch (Exception e) {
      return null;
    }
  }
}

class UploadedFile {

  private String dir;
  private String filename;
  private String original;
  private String type;

  UploadedFile(String dir, String filename, String original, String type) {
    this.dir = dir;
    this.filename = filename;
    this.original = original;
    this.type = type;
  }

  public String getContentType() {
    return type;
  }

  public String getFilesystemName() {
    return filename;
  }

  public String getOriginalFileName() {
    return original;
  }

  public File getFile() {
    if (dir == null || filename == null) {
      return null;
    } else {
      return new File(dir + File.separator + filename);
    }
  }
}
