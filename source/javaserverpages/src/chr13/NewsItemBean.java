package chr13;

import java.io.Serializable;
import java.util.Date;

public class NewsItemBean implements Serializable {
  private static final long serialVersionUID = -1L;

  private String category;
  private String msg;
  private String postedBy;
  private Date postedDate = new Date();
  private int id;

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public String getPostedBy() {
    return postedBy;
  }

  public void setPostedBy(String postedBy) {
    this.postedBy = postedBy;
  }

  public Date getPostedDate() {
    return postedDate;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }
}
