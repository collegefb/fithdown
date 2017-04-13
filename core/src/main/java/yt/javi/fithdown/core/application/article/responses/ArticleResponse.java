package yt.javi.fithdown.core.application.article.responses;

import static java.time.ZoneOffset.UTC;

import java.util.Collection;
import yt.javi.fithdown.core.model.article.Article;

public class ArticleResponse {

  private final Article article;

  ArticleResponse(Article article) {
    this.article = article;
  }

  public String getArticleId() {
    return article.getArticleId().getId();
  }

  public Long getCreated() {
    return article.getCreated().toInstant(UTC).toEpochMilli();
  }

  public String getUrl() {
    return article.getUrl().toString();
  }

  public String getTitle() {
    return article.getTitle();
  }

  public String getContent() {
    return article.getContent();
  }

  public Collection<String> getCategories() {
    return article.getCategories();
  }
}
