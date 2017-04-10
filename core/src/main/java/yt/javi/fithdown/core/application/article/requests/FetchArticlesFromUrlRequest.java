package yt.javi.fithdown.core.application.article.requests;

import java.net.MalformedURLException;
import java.net.URL;

public class FetchArticlesFromUrlRequest {
  private final URL url;

  public FetchArticlesFromUrlRequest(String url) throws MalformedURLException {
    this.url = new URL(url);
  }

  public URL getUrl() {
    return url;
  }
}
