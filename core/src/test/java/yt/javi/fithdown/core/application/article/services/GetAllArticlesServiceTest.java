package yt.javi.fithdown.core.application.article.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import yt.javi.fithdown.core.application.article.requests.GetAllArticlesRequest;
import yt.javi.fithdown.core.application.article.responses.ArticleResponse;
import yt.javi.fithdown.core.application.article.responses.ArticleResponseFactory;
import yt.javi.fithdown.core.model.article.Article;
import yt.javi.fithdown.core.model.article.ArticleRepository;

import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

;

public class GetAllArticlesServiceTest {
  private GetAllArticlesService service;

  @Mock
  private ArticleRepository repository;

  @Mock
  private ArticleResponseFactory responseFactory;

  @Mock
  private Article article;

  @Mock
  private ArticleResponse response;

  @Mock
  private GetAllArticlesRequest request;

  @Before
  public void setUp() throws Exception {
    initMocks(this);

    service = new GetAllArticlesService(repository, responseFactory);
  }

  @Test
  public void itIsPossibleToGetAllSources() {
    doReturn(singletonList(article)).when(repository).findAll();
    doReturn(response).when(responseFactory).articleResponse(article);

    assertThat(service.execute(request), hasItem(response));
    verify(repository, times(1)).findAll();
    verify(responseFactory, times(1)).articleResponse(article);
  }
}