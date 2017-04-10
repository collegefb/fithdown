package yt.javi.fithdown.core.model.source;

import java.net.MalformedURLException;
import java.net.URL;

public class SourceFactory {
  public Source getSource(String sourceId, String name, String url) throws MalformedURLException {
    return new Source(new SourceId(sourceId), name, new URL(url));
  }

  public Source getSource(String name, String url) throws MalformedURLException {
    return new Source(new SourceId(), name, new URL(url));
  }
}
