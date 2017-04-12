package yt.javi.fithdown.core.application.source.services;

import static java.util.stream.Collectors.toList;

import java.util.Collection;
import yt.javi.fithdown.core.application.Service;
import yt.javi.fithdown.core.application.source.requests.GetAllSourcesRequest;
import yt.javi.fithdown.core.application.source.responses.SourceResponse;
import yt.javi.fithdown.core.application.source.responses.SourceResponseFactory;
import yt.javi.fithdown.core.model.source.SourceRepository;

public class GetAllSourcesService
    implements Service<GetAllSourcesRequest, Collection<SourceResponse>> {

  private final SourceRepository sourceRepository;

  private final SourceResponseFactory responseFactory;

  public GetAllSourcesService(
      SourceRepository sourceRepository, SourceResponseFactory responseFactory) {
    this.sourceRepository = sourceRepository;
    this.responseFactory = responseFactory;
  }

  @Override
  public Collection<SourceResponse> execute(GetAllSourcesRequest request) {
    return sourceRepository
        .findAll()
        .stream()
        .map(responseFactory::sourceResponse)
        .collect(toList());
  }
}
