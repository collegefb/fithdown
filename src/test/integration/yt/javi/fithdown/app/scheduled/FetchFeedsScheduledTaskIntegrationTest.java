package yt.javi.fithdown.app.scheduled;

import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
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

import java.util.Collection;
import java.util.Date;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.Collections.singletonMap;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class FetchFeedsScheduledTaskIntegrationTest {
  private static final String ARTICLE_TITLE = "test";

  private static final String ARTICLE_CONTENT = "test";

  private static final String ARTICLE_ID = "test";

  private static final String ARTICLE_LINK = "http://test.ing";

  private static final Collection<String> ARTICLE_CATEGORIES = emptyList();

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
    createSourceService.execute(
            new CreateSourceRequest(
                    "test",
                    "http://github.com"
            )
    );
  }

  @Test
  public void itIsPossibleToGetAllFeedsFromSources() throws FeedException {
    doReturn(feed).when(feedInput).build(any(XmlReader.class));
    doReturn(singletonList(entry)).when(feed).getEntries();
    doReturn(new Date()).when(entry).getPublishedDate();
    doReturn(ARTICLE_LINK).when(entry).getLink();
    doReturn(ARTICLE_TITLE).when(entry).getTitle();
    doReturn(content).when(entry).getDescription();
    doReturn(ARTICLE_CATEGORIES).when(entry).getCategories();
    doReturn(ARTICLE_CONTENT).when(content).getValue();

    assertThat(fetchFeedsScheduledTask.fetchArticlesFromSources(), is(singletonMap("test", 1)));
  }
}