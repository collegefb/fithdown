package yt.javi.fithdown.core.model.article;

import static java.util.UUID.fromString;
import static java.util.UUID.randomUUID;

import java.util.UUID;

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
