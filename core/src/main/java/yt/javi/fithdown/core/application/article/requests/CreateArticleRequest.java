package yt.javi.fithdown.core.application.article.requests;

import java.util.Collection;

public class CreateArticleRequest {
  private final Long created;

  private final String url;

  private final String title;

  private final String content;

  private final Collection<String> categories;

  public CreateArticleRequest(Long created, String url, String title, String content, Collection<String> categories) {
    this.created = created;
    this.url = url;
    this.title = title;
    this.content = content;
    this.categories = categories;
  }

  public Long getCreated() {
    return created;
  }

  public String getUrl() {
    return url;
  }

  public String getTitle() {
    return title;
  }

  public String getContent() {
    return content;
  }

  public Collection<String> getCategories() {
    return categories;
  }
}
