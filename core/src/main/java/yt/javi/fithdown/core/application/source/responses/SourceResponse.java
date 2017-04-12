package yt.javi.fithdown.core.application.source.responses;

import yt.javi.fithdown.core.model.source.Source;

public class SourceResponse {

  private final Source source;

  SourceResponse(Source source) {
    this.source = source;
  }

  public String getSourceId() {
    return source.getSourceId().getId();
  }

  public String getName() {
    return source.getName();
  }

  public String getUrl() {
    return source.getUrl().toString();
  }
}
