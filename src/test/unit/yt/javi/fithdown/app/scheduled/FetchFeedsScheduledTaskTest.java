package yt.javi.fithdown.app.scheduled;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import yt.javi.fithdown.core.application.article.requests.CreateArticleRequest;
import yt.javi.fithdown.core.application.article.requests.FetchArticlesFromUrlRequest;
import yt.javi.fithdown.core.application.article.responses.ArticleResponse;
import yt.javi.fithdown.core.application.article.services.CreateArticleService;
import yt.javi.fithdown.core.application.article.services.FetchArticlesFromUrlService;
import yt.javi.fithdown.core.application.source.requests.GetAllSourcesRequest;
import yt.javi.fithdown.core.application.source.responses.SourceResponse;
import yt.javi.fithdown.core.application.source.services.GetAllSourcesService;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.Optional.of;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.initMocks;

public class FetchFeedsScheduledTaskTest {
  private static final String SOURCE_NAME = "test";

  private FetchFeedsScheduledTask scheduledTask;

  @Mock
  private GetAllSourcesService getAllSourcesService;

  @Mock
  private FetchArticlesFromUrlService fetchArticlesFromUrlService;

  @Mock
  private CreateArticleService createArticleService;

  @Mock
  private SourceResponse sourceResponse;

  @Mock
  private ArticleResponse articleResponse;

  @Before
  public void setUp() throws Exception {
    initMocks(this);

    scheduledTask = new FetchFeedsScheduledTask(getAllSourcesService, fetchArticlesFromUrlService, createArticleService);
  }

  @Test
  public void itFetchesAllArticlesFromAllSources() {
    doReturn(singletonList(sourceResponse)).when(getAllSourcesService).execute(any(GetAllSourcesRequest.class));
    doReturn("http://github.com").when(sourceResponse).getUrl();
    doReturn(SOURCE_NAME).when(sourceResponse).getName();
    doReturn(singletonList(articleResponse)).when(fetchArticlesFromUrlService).execute(any(FetchArticlesFromUrlRequest.class));
    doReturn(100L).when(articleResponse).getCreated();
    doReturn("http://test.ing").when(articleResponse).getUrl();
    doReturn("test").when(articleResponse).getTitle();
    doReturn("test").when(articleResponse).getContent();
    doReturn(emptyList()).when(articleResponse).getCategories();
    doReturn(of(articleResponse)).when(createArticleService).execute(any(CreateArticleRequest.class));

    assertThat(scheduledTask.fetchArticlesFromSources().get(SOURCE_NAME), is(1));
  }
}