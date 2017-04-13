package yt.javi.fithdown.core.application.article.services;

import static java.util.Collections.emptyList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.net.MalformedURLException;
import java.util.Collection;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import yt.javi.fithdown.core.application.article.requests.CreateArticleRequest;
import yt.javi.fithdown.core.application.article.responses.ArticleResponse;
import yt.javi.fithdown.core.application.article.responses.ArticleResponseFactory;
import yt.javi.fithdown.core.model.article.Article;
import yt.javi.fithdown.core.model.article.ArticleFactory;
import yt.javi.fithdown.core.model.article.ArticleRepository;

public class CreateArticleServiceTest {

  private static final Long ARTICLE_CREATED = 100000L;

  private static final String ARTICLE_URL = "http://www.test.ing";

  private static final String ARTICLE_TITLE = "test";

  private static final String ARTICLE_CONTENT = "content";

  private static final Collection<String> ARTICLE_CATEGORIES = emptyList();

  private CreateArticleService service;

  @Mock
  private ArticleFactory factory;

  @Mock
  private ArticleRepository repository;

  @Mock
  private ArticleResponseFactory responseFactory;

  @Mock
  private CreateArticleRequest request;

  @Mock
  private Article article;

  @Mock
  private ArticleResponse response;

  @Before
  public void setUp() throws Exception {
    initMocks(this);
    service = new CreateArticleService(factory, repository, responseFactory);
  }

  @Test
  public void itIsPossibleToAddANewSource() throws MalformedURLException {
    when(request.getCreated()).thenReturn(ARTICLE_CREATED);
    when(request.getUrl()).thenReturn(ARTICLE_URL);
    when(request.getTitle()).thenReturn(ARTICLE_TITLE);
    when(request.getContent()).thenReturn(ARTICLE_CONTENT);
    when(request.getCategories()).thenReturn(ARTICLE_CATEGORIES);
    when(factory.getArticle(ARTICLE_CREATED, ARTICLE_URL, ARTICLE_TITLE, ARTICLE_CONTENT))
        .thenReturn(article);
    when(article.setCategories(ARTICLE_CATEGORIES)).thenReturn(article);
    when(repository.save(article)).thenReturn(article);
    when(responseFactory.articleResponse(article)).thenReturn(response);

    assertThat(service.execute(request).orElse(null), is(response));
  }
}
