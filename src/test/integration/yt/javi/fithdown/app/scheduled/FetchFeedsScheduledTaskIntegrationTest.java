package yt.javi.fithdown.app.scheduled;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.Collections.singletonMap;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import com.rometools.rome.feed.synd.SyndCategory;
import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import java.util.Date;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import yt.javi.fithdown.app.Application;
import yt.javi.fithdown.core.application.source.requests.CreateSourceRequest;
import yt.javi.fithdown.core.application.source.services.CreateSourceService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class FetchFeedsScheduledTaskIntegrationTest {

  private static final String ARTICLE_TITLE = "test";

  private static final String ARTICLE_CONTENT = "test";

  private static final String ARTICLE_ID = "test";

  private static final String ARTICLE_LINK = "http://test.ing";

  private static final List<SyndCategory> ARTICLE_CATEGORIES = emptyList();

  @Autowired
  private FetchFeedsScheduledTask fetchFeedsScheduledTask;

  @Autowired
  private CreateSourceService createSourceService;

  @MockBean
  private SyndFeedInput feedInput;

  @Mock
  private SyndFeed feed;

  @Mock
  private SyndEntry entry;

  @Mock
  private SyndContent content;

  @Before
  public void setUp() {
    createSourceService.execute(new CreateSourceRequest("test", "http://github.com"));
  }

  @Test
  public void itIsPossibleToGetAllFeedsFromSources() throws FeedException {
    when(feedInput.build(any(XmlReader.class))).thenReturn(feed);
    when(feed.getEntries()).thenReturn(singletonList(entry));
    when(entry.getPublishedDate()).thenReturn(new Date());
    when(entry.getLink()).thenReturn(ARTICLE_LINK);
    when(entry.getTitle()).thenReturn(ARTICLE_TITLE);
    when(entry.getDescription()).thenReturn(content);
    when(entry.getCategories()).thenReturn(ARTICLE_CATEGORIES);
    when(content.getValue()).thenReturn(ARTICLE_CONTENT);

    assertThat(fetchFeedsScheduledTask.fetchArticlesFromSources(), is(singletonMap("test", 1)));
  }
}
