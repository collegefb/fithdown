package yt.javi.fithdown.core.application.article.requests;

public class GetArticleRequest {
  private final String id;

  public GetArticleRequest(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }
}
