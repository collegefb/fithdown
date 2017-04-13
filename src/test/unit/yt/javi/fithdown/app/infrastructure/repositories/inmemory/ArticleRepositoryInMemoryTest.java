package yt.javi.fithdown.app.infrastructure.repositories.inmemory;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import yt.javi.fithdown.core.model.article.Article;
import yt.javi.fithdown.core.model.article.ArticleFactory;
import yt.javi.fithdown.core.model.article.ArticleId;

public class ArticleRepositoryInMemoryTest {

  private static final String ARTICLE_TITLE = "test";

  private static final String ARTICLE_CONTENT = "test";

  private static final String ARTICLE_ID = "test";

  private static final String ARTICLE_LINK = "http://test.ing";

  private static final List<String> ARTICLE_CATEGORIES = emptyList();

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
    when(articleId.getId()).thenReturn(ARTICLE_ID);
    when(article.getArticleId()).thenReturn(articleId);

    assertThat(repository.save(article), is(article));
  }

  @Test
  public void itIsPossibleToGetAnExistingArticleByItsId() {
    when(articleId.getId()).thenReturn(ARTICLE_ID);
    when(article.getArticleId()).thenReturn(articleId);
    repository.save(article);

    assertThat(repository.findById(ARTICLE_ID).orElse(null), is(article));
  }

  @Test
  public void itIsNotPossibleToGetANonExistingArticleByItsId() {
    assertThat(repository.findById("non_existing").isPresent(), is(false));
  }

  @Test
  public void itIsPossibleToGetAllArticles() {
    when(articleId.getId()).thenReturn(ARTICLE_ID);
    when(article.getArticleId()).thenReturn(articleId);
    repository.save(article);

    assertThat(repository.findAll(), hasItem(article));
  }

  @Test
  public void itIsPossibleToRemoveAnExistingArticle() {
    when(articleId.getId()).thenReturn(ARTICLE_ID);
    when(article.getArticleId()).thenReturn(articleId);
    repository.save(article);

    assertThat(repository.remove(article), is(true));
  }

  @Test
  public void itIsNotPossibleToRemoveANonExistingArticle() {
    when(articleId.getId()).thenReturn(ARTICLE_ID);
    when(article.getArticleId()).thenReturn(articleId);

    assertThat(repository.remove(article), is(false));
  }

  @Test
  public void itFetchesArticlesFromAGivenUrl() throws FeedException, MalformedURLException {
    when(feedInput.build(any(XmlReader.class))).thenReturn(feed);
    when(feed.getEntries()).thenReturn(singletonList(entry));
    when(entry.getPublishedDate()).thenReturn(new Date());
    when(entry.getLink()).thenReturn(ARTICLE_LINK);
    when(entry.getTitle()).thenReturn(ARTICLE_TITLE);
    when(entry.getDescription()).thenReturn(content);
    when(entry.getCategories()).thenReturn(emptyList());
    when(content.getValue()).thenReturn(ARTICLE_CONTENT);
    when(factory.getArticle(anyLong(), eq(ARTICLE_LINK), eq(ARTICLE_TITLE), eq(ARTICLE_CONTENT))).thenReturn(article);
    when(article.setCategories(ARTICLE_CATEGORIES)).thenReturn(article);

    assertThat(repository.fetchFromUrl(new URL("http://github.com")), hasItem(article));
  }
}
