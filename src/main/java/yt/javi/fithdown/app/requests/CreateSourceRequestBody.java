package yt.javi.fithdown.app.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateSourceRequestBody {
  private final String name;

  private final String url;

  public CreateSourceRequestBody(
          @JsonProperty("name") String name,
          @JsonProperty("url") String url
  ) {
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
