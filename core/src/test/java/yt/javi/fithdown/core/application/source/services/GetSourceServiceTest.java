package yt.javi.fithdown.core.application.source.services;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import yt.javi.fithdown.core.application.source.requests.GetSourceRequest;
import yt.javi.fithdown.core.application.source.responses.SourceResponse;
import yt.javi.fithdown.core.application.source.responses.SourceResponseFactory;
import yt.javi.fithdown.core.model.source.Source;
import yt.javi.fithdown.core.model.source.SourceRepository;

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
    when(request.getId()).thenReturn(SOURCE_ID);
    when(repository.findById(SOURCE_ID)).thenReturn(of(source));
    when(responseFactory.sourceResponse(source)).thenReturn(sourceResponse);

    assertThat(service.execute(request).orElse(null), is(sourceResponse));
  }

  @Test
  public void itIsNotPossibleToGetANonExistingSource() {
    when(request.getId()).thenReturn(SOURCE_ID);
    when(repository.findById(SOURCE_ID)).thenReturn(empty());
    when(responseFactory.sourceResponse(source)).thenReturn(sourceResponse);

    assertThat(service.execute(request).orElse(null), nullValue());
  }
}
