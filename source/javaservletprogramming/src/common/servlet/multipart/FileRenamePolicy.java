package common.servlet.multipart;

import java.io.File;

public interface FileRenamePolicy {

  public File rename(File f);

}
