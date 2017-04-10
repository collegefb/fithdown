package yt.javi.fithdown.core.application.article.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import yt.javi.fithdown.core.application.article.requests.GetArticleRequest;
import yt.javi.fithdown.core.application.article.responses.ArticleResponse;
import yt.javi.fithdown.core.application.article.responses.ArticleResponseFactory;
import yt.javi.fithdown.core.model.article.Article;
import yt.javi.fithdown.core.model.article.ArticleRepository;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class GetArticleServiceTest {
  private static final String ARTICLE_ID = "test";

  private GetArticleService service;

  @Mock
  private ArticleRepository repository;

  @Mock
  private ArticleResponseFactory responseFactory;

  @Mock
  private GetArticleRequest request;

  @Mock
  private Article article;

  @Mock
  private ArticleResponse response;

  @Before
  public void setUp() throws Exception {
    initMocks(this);
    service = new GetArticleService(repository, responseFactory);
  }

  @Test
  public void itIsPossibleToGetAnExistingSource() {
    doReturn(ARTICLE_ID).when(request).getId();
    doReturn(of(article)).when(repository).findById(ARTICLE_ID);
    doReturn(response).when(responseFactory).articleResponse(article);

    assertThat(service.execute(request).orElse(null), is(response));
    verify(repository, times(1)).findById(ARTICLE_ID);
    verify(responseFactory, times(1)).articleResponse(article);
  }

  @Test
  public void itIsNotPossibleToGetANonExistingSource() {
    doReturn(ARTICLE_ID).when(request).getId();
    doReturn(empty()).when(repository).findById(ARTICLE_ID);
    doReturn(response).when(responseFactory).articleResponse(article);

    assertThat(service.execute(request).orElse(null), nullValue());
    verify(repository, times(1)).findById(ARTICLE_ID);
    verify(responseFactory, never()).articleResponse(article);
  }
}