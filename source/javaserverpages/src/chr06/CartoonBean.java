package chr06;
import java.util.List;
import java.util.ArrayList;
public class CartoonBean implements java.io.Serializable{
	
	private static int index = -1;
    private List fileNames;

    public CartoonBean() {
        initFileList();
    }
	
    public String getFileName() {
        index++;
        if (index > fileNames.size() - 1) {
            index = 0;
        }
        return (String) fileNames.get(index);
    }
	
    private void initFileList() {
        fileNames = new ArrayList();
        fileNames.add("dilbert2001113293109.gif");
        fileNames.add("dilbert2001166171101.gif");
        fileNames.add("dilbert2001166171108.gif");
        fileNames.add("dilbert2731150011029.gif");
    }
}
