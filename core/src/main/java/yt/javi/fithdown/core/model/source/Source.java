package yt.javi.fithdown.core.model.source;

import java.net.URL;

public class Source {

  private final SourceId sourceId;

  private final String name;

  private URL url;

  Source(SourceId sourceId, String name, URL url) {
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

  public URL getUrl() {
    return url;
  }

  public Source setUrl(URL url) {
    this.url = url;

    return this;
  }
}
