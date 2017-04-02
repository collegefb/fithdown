package yt.javi.fithdown.core.application.source.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import yt.javi.fithdown.core.application.source.requests.GetSourceRequest;
import yt.javi.fithdown.core.application.source.responses.SourceResponse;
import yt.javi.fithdown.core.application.source.responses.SourceResponseFactory;
import yt.javi.fithdown.core.model.source.Source;
import yt.javi.fithdown.core.model.source.SourceRepository;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class GetSourceServiceTest {
  private static final String SOURCE_ID = "test";

  private GetSourceService service;

  @Mock
  private SourceRepository repository;

  @Mock
  private SourceResponseFactory responseFactory;

  @Mock
  private GetSourceRequest request;

  @Mock
  private Source source;

  @Mock
  private SourceResponse sourceResponse;

  @Before
  public void setUp() throws Exception {
    initMocks(this);
    service = new GetSourceService(repository, responseFactory);
  }

  @Test
  public void itIsPossibleToGetAnExistingSource() {
    doReturn(SOURCE_ID).when(request).getId();
    doReturn(of(source)).when(repository).findById(SOURCE_ID);
    doReturn(sourceResponse).when(responseFactory).sourceResponse(source);

    assertThat(service.execute(request).orElse(null), is(sourceResponse));
    verify(repository, times(1)).findById(SOURCE_ID);
    verify(responseFactory, times(1)).sourceResponse(source);
  }

  @Test
  public void itIsNotPossibleToGetANonExistingSource() {
    doReturn(SOURCE_ID).when(request).getId();
    doReturn(empty()).when(repository).findById(SOURCE_ID);
    doReturn(sourceResponse).when(responseFactory).sourceResponse(source);

    assertThat(service.execute(request).orElse(null), nullValue());
    verify(repository, times(1)).findById(SOURCE_ID);
    verify(responseFactory, never()).sourceResponse(source);
  }
}