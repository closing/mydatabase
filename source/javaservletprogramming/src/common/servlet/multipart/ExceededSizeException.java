package common.servlet.multipart;

import java.io.IOException;

public class ExceededSizeException extends IOException {
  private static final long serialVersionUID = -1L;

  public ExceededSizeException() {
    super();
  }

  public ExceededSizeException(String s) {
    super(s);
  }
}
