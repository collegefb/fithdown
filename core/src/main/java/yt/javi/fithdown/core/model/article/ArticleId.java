package yt.javi.fithdown.core.model.article;

import java.util.UUID;

import static java.util.UUID.fromString;
import static java.util.UUID.randomUUID;

public class ArticleId {
  private final UUID id;

  ArticleId() {
    this.id = randomUUID();
  }

  ArticleId(String id) {
    this.id = fromString(id);
  }

  public String getId() {
    return id.toString();
  }

}
