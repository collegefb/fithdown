package yt.javi.fithdown.core.model.article;

import static java.time.Instant.ofEpochMilli;
import static java.time.ZoneOffset.UTC;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;

public class ArticleFactory {

  public Article getArticle(
      String articleId, Long created, String url, String title, String content)
      throws MalformedURLException {
    return new Article(new ArticleId(articleId), getCreated(created), new URL(url), title, content);
  }

  public Article getArticle(Long created, String url, String title, String content)
      throws MalformedURLException {
    return new Article(new ArticleId(), getCreated(created), new URL(url), title, content);
  }

  private LocalDateTime getCreated(Long created) {
    return ofEpochMilli(created).atOffset(UTC).toLocalDateTime();
  }
}
