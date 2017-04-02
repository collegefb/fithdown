package yt.javi.fithdown.core.model.source;

import java.net.URI;

public class Source {
  private final SourceId sourceId;

  private final String name;

  private URI url;

  Source(SourceId sourceId, String name, URI url) {
    this.sourceId = sourceId;
    this.name = name;
    this.url = url;
  }

  public SourceId getSourceId() {
    return sourceId;
  }

  public String getName() {
    return name;
  }

  public URI getUrl() {
    return url;
  }

  public Source setUrl(URI url) {
    this.url = url;

    return this;
  }
}
