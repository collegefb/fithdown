package yt.javi.fithdown.core.application.article.services;

import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import yt.javi.fithdown.core.application.article.requests.GetAllArticlesRequest;
import yt.javi.fithdown.core.application.article.responses.ArticleResponse;
import yt.javi.fithdown.core.application.article.responses.ArticleResponseFactory;
import yt.javi.fithdown.core.model.article.Article;
import yt.javi.fithdown.core.model.article.ArticleRepository;

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
    when(repository.findAll()).thenReturn(singletonList(article));
    when(responseFactory.articleResponse(article)).thenReturn(response);

    assertThat(service.execute(request), hasItem(response));
  }
}
