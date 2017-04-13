package yt.javi.fithdown.core.application.source.services;

import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import yt.javi.fithdown.core.application.source.requests.GetAllSourcesRequest;
import yt.javi.fithdown.core.application.source.responses.SourceResponse;
import yt.javi.fithdown.core.application.source.responses.SourceResponseFactory;
import yt.javi.fithdown.core.model.source.Source;
import yt.javi.fithdown.core.model.source.SourceRepository;

public class GetAllSourcesServiceTest {

  private GetAllSourcesService service;

  @Mock
  private SourceRepository repository;

  @Mock
  private SourceResponseFactory responseFactory;

  @Mock
  private Source source;

  @Mock
  private SourceResponse sourceResponse;

  @Mock
  private GetAllSourcesRequest request;

  @Before
  public void setUp() throws Exception {
    initMocks(this);

    service = new GetAllSourcesService(repository, responseFactory);
  }

  @Test
  public void itIsPossibleToGetAllSources() {
    when(repository.findAll()).thenReturn(singletonList(source));
    when(responseFactory.sourceResponse(source)).thenReturn(sourceResponse);

    assertThat(service.execute(request), hasItem(sourceResponse));
  }
}
