package yt.javi.fithdown.core.application.article.services;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import yt.javi.fithdown.core.application.article.requests.GetArticleRequest;
import yt.javi.fithdown.core.application.article.responses.ArticleResponse;
import yt.javi.fithdown.core.application.article.responses.ArticleResponseFactory;
import yt.javi.fithdown.core.model.article.Article;
import yt.javi.fithdown.core.model.article.ArticleRepository;

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
    when(request.getId()).thenReturn(ARTICLE_ID);
    when(repository.findById(ARTICLE_ID)).thenReturn(of(article));
    when(responseFactory.articleResponse(article)).thenReturn(response);

    assertThat(service.execute(request).orElse(null), is(response));
  }

  @Test
  public void itIsNotPossibleToGetANonExistingSource() {
    when(request.getId()).thenReturn(ARTICLE_ID);
    when(repository.findById(ARTICLE_ID)).thenReturn(empty());
    when(responseFactory.articleResponse(article)).thenReturn(response);

    assertThat(service.execute(request).orElse(null), nullValue());
  }
}
