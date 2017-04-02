package yt.javi.fithdown.core.application.source.requests;

public class GetSourceRequest {
  private final String id;

  public GetSourceRequest(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }
}
