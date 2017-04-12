package yt.javi.fithdown.app.responses;

import yt.javi.fithdown.core.application.source.responses.SourceResponse;

public class SourceResponseEntity {

  private final SourceResponse response;

  public SourceResponseEntity(SourceResponse response) {
    this.response = response;
  }

  public String getId() {
    return response.getSourceId();
  }

  public String getName() {
    return response.getName();
  }

  public String getUrl() {
    return response.getUrl();
  }
}
