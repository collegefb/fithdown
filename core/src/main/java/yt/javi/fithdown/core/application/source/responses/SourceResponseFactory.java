package yt.javi.fithdown.core.application.source.responses;

import yt.javi.fithdown.core.model.source.Source;

public class SourceResponseFactory {
  public SourceResponse sourceResponse(Source source) {
    return new SourceResponse(source);
  }
}
