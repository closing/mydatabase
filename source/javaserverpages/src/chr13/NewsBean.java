package chr13;

import java.io.Serializable;
import java.util.ArrayList;

public class NewsBean implements Serializable {
  private static final long serialVersionUID = -1L;

  private ArrayList newsItems = new ArrayList();
  private int[] idSequence = new int[1];

  public NewsBean() {
    addDefaultItems();
  }

  public NewsItemBean[] getNewsItems() {
    NewsItemBean[] a = null;
    synchronized (newsItems) {
      a = (NewsItemBean[]) newsItems.toArray(new NewsItemBean[newsItems.size()]);
    }
    return a;
  }

  public void setNewsItem(NewsItemBean newsItem) {
    synchronized (idSequence) {
      newsItem.setId(idSequence[0]++);
    }
    synchronized (newsItems) {
      newsItems.add(newsItem);
    }
  }

  public void removeNewsItem(int id) {
    synchronized (newsItems) {
      for (int i = 0; i < newsItems.size(); i++) {
        NewsItemBean item = (NewsItemBean) newsItems.get(i);
        if (id == item.getId()) {
          newsItems.remove(i);
          break;
        }
      }
    }
  }

  private void addDefaultItems() {
    NewsItemBean item = new NewsItemBean();
    item.setCategory("JSP");
    item.setMsg("2nd edition of O'Reilly JSP book released!");
    item.setPostedBy("Hans Bergsten");
    setNewsItem(item);

    item = new NewsItemBean();
    item.setCategory("Servlet");
    item.setMsg("Servets and JSP: a powerful duo.");
    item.setPostedBy("Hans Bergsten");
    setNewsItem(item);

    item = new NewsItemBean();
    item.setCategory("EJB");
    item.setMsg("JSP makes a great front-end for EJB.");
    item.setPostedBy("Hans Bergsten");
    setNewsItem(item);
  }
}
