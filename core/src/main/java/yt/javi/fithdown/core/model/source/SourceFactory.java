package yt.javi.fithdown.core.model.source;

import static java.net.URI.create;

public class SourceFactory {
  public Source getSource(String sourceId, String name, String url) {
    return new Source(new SourceId(sourceId), name, create(url));
  }

  public Source getSource(String name, String url) {
    return new Source(new SourceId(), name, create(url));
  }
}
