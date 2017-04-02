package yt.javi.fithdown.core.application.source.requests;

public class CreateSourceRequest {
  private final String name;

  private final String url;

  public CreateSourceRequest(String name, String url) {
    this.name = name;
    this.url = url;
  }

  public String getName() {
    return name;
  }

  public String getUrl() {
    return url;
  }
}
