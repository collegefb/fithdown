package yt.javi.fithdown.core.application.source.services;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.net.MalformedURLException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import yt.javi.fithdown.core.application.source.requests.CreateSourceRequest;
import yt.javi.fithdown.core.application.source.responses.SourceResponse;
import yt.javi.fithdown.core.application.source.responses.SourceResponseFactory;
import yt.javi.fithdown.core.model.source.Source;
import yt.javi.fithdown.core.model.source.SourceFactory;
import yt.javi.fithdown.core.model.source.SourceRepository;

public class CreateSourceServiceTest {

  private static final String SOURCE_NAME = "test";

  private static final String SOURCE_URL = "http://www.test.ing";

  private CreateSourceService service;

  @Mock
  private SourceFactory sourceFactory;

  @Mock
  private SourceRepository sourceRepository;

  @Mock
  private SourceResponseFactory sourceResponseFactory;

  @Mock
  private CreateSourceRequest createSourceRequest;

  @Mock
  private Source source;

  @Mock
  private SourceResponse sourceResponse;

  @Before
  public void setUp() throws Exception {
    initMocks(this);
    service = new CreateSourceService(sourceFactory, sourceRepository, sourceResponseFactory);
  }

  @Test
  public void itIsPossibleToAddANewSource() throws MalformedURLException {
    when(createSourceRequest.getName()).thenReturn(SOURCE_NAME);
    when(createSourceRequest.getUrl()).thenReturn(SOURCE_URL);
    when(sourceFactory.getSource(SOURCE_NAME, SOURCE_URL)).thenReturn(source);
    when(sourceRepository.save(source)).thenReturn(source);
    when(sourceResponseFactory.sourceResponse(source)).thenReturn(sourceResponse);

    assertThat(service.execute(createSourceRequest).orElse(null), is(sourceResponse));
  }
}
