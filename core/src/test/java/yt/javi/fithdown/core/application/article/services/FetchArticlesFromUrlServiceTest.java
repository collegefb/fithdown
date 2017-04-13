package yt.javi.fithdown.core.application.article.services;

import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.net.MalformedURLException;
import java.net.URL;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import yt.javi.fithdown.core.application.article.requests.FetchArticlesFromUrlRequest;
import yt.javi.fithdown.core.application.article.responses.ArticleResponse;
import yt.javi.fithdown.core.application.article.responses.ArticleResponseFactory;
import yt.javi.fithdown.core.model.article.Article;
import yt.javi.fithdown.core.model.article.ArticleRepository;

public class FetchArticlesFromUrlServiceTest {

  private FetchArticlesFromUrlService service;

  @Mock
  private ArticleRepository repository;

  @Mock
  private ArticleResponseFactory responseFactory;

  @Mock
  private FetchArticlesFromUrlRequest request;

  @Mock
  private Article article;

  @Mock
  private ArticleResponse response;

  @Before
  public void setUp() throws Exception {
    initMocks(this);

    service = new FetchArticlesFromUrlService(repository, responseFactory);
  }

  @Test
  public void itFetchesAllArticlesFromAUrl() throws MalformedURLException {
    URL url = new URL("http://www.test.ing");
    when(request.getUrl()).thenReturn(url);
    when(repository.fetchFromUrl(url)).thenReturn(singletonList(article));
    when(responseFactory.articleResponse(article)).thenReturn(response);

    assertThat(service.execute(request), hasItem(response));
  }
}
