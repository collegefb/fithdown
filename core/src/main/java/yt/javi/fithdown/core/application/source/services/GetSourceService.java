package yt.javi.fithdown.core.application.source.services;

import yt.javi.fithdown.core.application.Service;
import yt.javi.fithdown.core.application.source.requests.GetSourceRequest;
import yt.javi.fithdown.core.application.source.responses.SourceResponse;
import yt.javi.fithdown.core.application.source.responses.SourceResponseFactory;
import yt.javi.fithdown.core.model.source.SourceRepository;

import java.util.Optional;

public class GetSourceService implements Service<GetSourceRequest, Optional<SourceResponse>> {
  private final SourceRepository repository;

  private final SourceResponseFactory responseFactory;

  public GetSourceService(SourceRepository repository, SourceResponseFactory responseFactory) {
    this.repository = repository;
    this.responseFactory = responseFactory;
  }

  @Override
  public Optional<SourceResponse> execute(GetSourceRequest request) {
    return repository.findById(request.getId()).map(responseFactory::sourceResponse);
  }
}
