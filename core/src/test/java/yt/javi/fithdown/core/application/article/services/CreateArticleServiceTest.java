package yt.javi.fithdown.core.application.article.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import yt.javi.fithdown.core.application.article.requests.CreateArticleRequest;
import yt.javi.fithdown.core.application.article.responses.ArticleResponse;
import yt.javi.fithdown.core.application.article.responses.ArticleResponseFactory;
import yt.javi.fithdown.core.model.article.Article;
import yt.javi.fithdown.core.model.article.ArticleFactory;
import yt.javi.fithdown.core.model.article.ArticleRepository;

import java.net.MalformedURLException;
import java.util.Collection;

import static java.util.Collections.emptyList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

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
    doReturn(ARTICLE_CREATED).when(request).getCreated();
    doReturn(ARTICLE_URL).when(request).getUrl();
    doReturn(ARTICLE_TITLE).when(request).getTitle();
    doReturn(ARTICLE_CONTENT).when(request).getContent();
    doReturn(ARTICLE_CATEGORIES).when(request).getCategories();
    doReturn(article).when(factory).getArticle(ARTICLE_CREATED, ARTICLE_URL, ARTICLE_TITLE, ARTICLE_CONTENT);
    doReturn(article).when(article).setCategories(ARTICLE_CATEGORIES);
    doReturn(article).when(repository).save(article);
    doReturn(response).when(responseFactory).articleResponse(article);

    assertThat(service.execute(request).orElse(null), is(response));

    verify(repository, times(1)).save(article);
    verify(responseFactory, times(1)).articleResponse(article);
  }
}