package yt.javi.fithdown.core.application.article.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import yt.javi.fithdown.core.application.article.requests.FetchArticlesFromUrlRequest;
import yt.javi.fithdown.core.application.article.responses.ArticleResponse;
import yt.javi.fithdown.core.application.article.responses.ArticleResponseFactory;
import yt.javi.fithdown.core.model.article.Article;
import yt.javi.fithdown.core.model.article.ArticleRepository;

import java.net.MalformedURLException;
import java.net.URL;

import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.initMocks;

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
    doReturn(url).when(request).getUrl();
    doReturn(singletonList(article)).when(repository).fetchFromUrl(url);
    doReturn(response).when(responseFactory).articleResponse(article);

    assertThat(service.execute(request), hasItem(response));
  }
}