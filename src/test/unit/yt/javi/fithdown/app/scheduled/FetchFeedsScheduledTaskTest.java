package yt.javi.fithdown.app.scheduled;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.Optional.of;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

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

    scheduledTask =
        new FetchFeedsScheduledTask(
            getAllSourcesService, fetchArticlesFromUrlService, createArticleService);
  }

  @Test
  public void itFetchesAllArticlesFromAllSources() {
    when(getAllSourcesService.execute(any(GetAllSourcesRequest.class)))
        .thenReturn(singletonList(sourceResponse));
    when(sourceResponse.getUrl()).thenReturn("http://github.com");
    when(sourceResponse.getName()).thenReturn(SOURCE_NAME);
    when(fetchArticlesFromUrlService.execute(any(FetchArticlesFromUrlRequest.class)))
        .thenReturn(singletonList(articleResponse));
    when(articleResponse.getCreated()).thenReturn(100L);
    when(articleResponse.getUrl()).thenReturn("http://test.ing");
    when(articleResponse.getTitle()).thenReturn("test");
    when(articleResponse.getContent()).thenReturn("test");
    when(articleResponse.getCategories()).thenReturn(emptyList());
    when(createArticleService.execute(any(CreateArticleRequest.class)))
        .thenReturn(of(articleResponse));

    assertThat(scheduledTask.fetchArticlesFromSources().get(SOURCE_NAME), is(1));
  }
}
