package yt.javi.fithdown.app.infrastructure.repositories.inmemory;

import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import yt.javi.fithdown.core.model.article.Article;
import yt.javi.fithdown.core.model.article.ArticleFactory;
import yt.javi.fithdown.core.model.article.ArticleId;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.Date;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class ArticleRepositoryInMemoryTest {
  private static final String ARTICLE_TITLE = "test";

  private static final String ARTICLE_CONTENT = "test";

  private static final String ARTICLE_ID = "test";

  private static final String ARTICLE_LINK = "http://test.ing";

  private static final Collection<String> ARTICLE_CATEGORIES = emptyList();

  private ArticleRepositoryInMemory repository;

  @Mock
  private SyndFeedInput feedInput;

  @Mock
  private ArticleFactory factory;

  @Mock
  private Article article;

  @Mock
  private ArticleId articleId;

  @Mock
  private SyndFeed feed;

  @Mock
  private SyndEntry entry;

  @Mock
  private SyndContent content;

  @Before
  public void setUp() throws Exception {
    initMocks(this);

    repository = new ArticleRepositoryInMemory(feedInput, factory);
  }

  @Test
  public void itIsPossibleToSaveAArticle() {
    doReturn(ARTICLE_ID).when(articleId).getId();
    doReturn(articleId).when(article).getArticleId();

    assertThat(repository.save(article), is(article));
    verify(articleId, times(1)).getId();
  }

  @Test
  public void itIsPossibleToGetAnExistingArticleByItsId() {
    doReturn(ARTICLE_ID).when(articleId).getId();
    doReturn(articleId).when(article).getArticleId();
    repository.save(article);

    assertThat(repository.findById(ARTICLE_ID).orElse(null), is(article));
  }

  @Test
  public void itIsNotPossibleToGetANonExistingArticleByItsId() {
    assertThat(repository.findById("non_existing").isPresent(), is(false));
  }

  @Test
  public void itIsPossibleToGetAllArticles() {
    doReturn(ARTICLE_ID).when(articleId).getId();
    doReturn(articleId).when(article).getArticleId();
    repository.save(article);

    assertThat(repository.findAll(), hasItem(article));
  }

  @Test
  public void itIsPossibleToRemoveAnExistingArticle() {
    doReturn(ARTICLE_ID).when(articleId).getId();
    doReturn(articleId).when(article).getArticleId();
    repository.save(article);

    assertThat(repository.remove(article), is(true));
  }

  @Test
  public void itIsNotPossibleToRemoveANonExistingArticle() {
    doReturn(ARTICLE_ID).when(articleId).getId();
    doReturn(articleId).when(article).getArticleId();

    assertThat(repository.remove(article), is(false));
  }

  @Test
  public void itFetchesArticlesFromAGivenUrl() throws FeedException, MalformedURLException {
    doReturn(feed).when(feedInput).build(any(XmlReader.class));
    doReturn(singletonList(entry)).when(feed).getEntries();
    doReturn(new Date()).when(entry).getPublishedDate();
    doReturn(ARTICLE_LINK).when(entry).getLink();
    doReturn(ARTICLE_TITLE).when(entry).getTitle();
    doReturn(content).when(entry).getDescription();
    doReturn(ARTICLE_CATEGORIES).when(entry).getCategories();
    doReturn(ARTICLE_CONTENT).when(content).getValue();
    doReturn(article).when(factory).getArticle(anyLong(), eq(ARTICLE_LINK), eq(ARTICLE_TITLE), eq(ARTICLE_CONTENT));
    doReturn(article).when(article).setCategories(ARTICLE_CATEGORIES);

    assertThat(repository.fetchFromUrl(new URL("http://github.com")), hasItem(article));
  }
}