package yt.javi.fithdown.core.model.article;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

public class Article {

  private final ArticleId articleId;

  private final LocalDateTime created;

  private URL url;

  private String title;

  private String content;

  private Collection<String> categories = new ArrayList<>();

  Article(ArticleId articleId, LocalDateTime created, URL url, String title, String content) {
    this.articleId = articleId;
    this.created = created;
    this.url = url;
    this.title = title;
    this.content = content;
  }

  public ArticleId getArticleId() {
    return articleId;
  }

  public LocalDateTime getCreated() {
    return created;
  }

  public URL getUrl() {
    return url;
  }

  public Article setUrl(URL url) {
    this.url = url;

    return this;
  }

  public String getTitle() {
    return title;
  }

  public Article setTitle(String title) {
    this.title = title;

    return this;
  }

  public String getContent() {
    return content;
  }

  public Article setContent(String content) {
    this.content = content;

    return this;
  }

  public Collection<String> getCategories() {
    return categories;
  }

  public Article setCategories(Collection<String> categories) {
    this.categories = categories;

    return this;
  }

  public Article addCategory(String category) {
    if (!categories.contains(category)) {
      categories.add(category);
    }

    return this;
  }
}
