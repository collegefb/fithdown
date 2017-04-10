package yt.javi.fithdown.core.application.source.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import yt.javi.fithdown.core.application.source.requests.CreateSourceRequest;
import yt.javi.fithdown.core.application.source.responses.SourceResponse;
import yt.javi.fithdown.core.application.source.responses.SourceResponseFactory;
import yt.javi.fithdown.core.model.source.Source;
import yt.javi.fithdown.core.model.source.SourceFactory;
import yt.javi.fithdown.core.model.source.SourceRepository;

import java.net.MalformedURLException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

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
    doReturn(SOURCE_NAME).when(createSourceRequest).getName();
    doReturn(SOURCE_URL).when(createSourceRequest).getUrl();
    doReturn(source).when(sourceFactory).getSource(SOURCE_NAME, SOURCE_URL);
    doReturn(source).when(sourceRepository).save(source);
    doReturn(sourceResponse).when(sourceResponseFactory).sourceResponse(source);

    assertThat(service.execute(createSourceRequest).orElse(null), is(sourceResponse));

    verify(sourceRepository, times(1)).save(source);
    verify(sourceResponseFactory, times(1)).sourceResponse(source);
  }
}