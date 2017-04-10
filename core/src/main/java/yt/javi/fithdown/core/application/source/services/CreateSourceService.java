package yt.javi.fithdown.core.application.source.services;

import yt.javi.fithdown.core.application.Service;
import yt.javi.fithdown.core.application.source.requests.CreateSourceRequest;
import yt.javi.fithdown.core.application.source.responses.SourceResponse;
import yt.javi.fithdown.core.application.source.responses.SourceResponseFactory;
import yt.javi.fithdown.core.model.source.SourceFactory;
import yt.javi.fithdown.core.model.source.SourceRepository;

import java.net.MalformedURLException;
import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;

public class CreateSourceService implements Service<CreateSourceRequest, Optional<SourceResponse>> {
  private final SourceFactory sourceFactory;

  private final SourceRepository sourceRepository;

  private final SourceResponseFactory sourceResponseFactory;

  public CreateSourceService(
          SourceFactory sourceFactory,
          SourceRepository sourceRepository,
          SourceResponseFactory sourceResponseFactory
  ) {
    this.sourceFactory = sourceFactory;
    this.sourceRepository = sourceRepository;
    this.sourceResponseFactory = sourceResponseFactory;
  }

  @Override
  public Optional<SourceResponse> execute(CreateSourceRequest request) {
    try {
      return ofNullable(
              sourceResponseFactory.sourceResponse(
                      sourceRepository.save(
                              sourceFactory.getSource(request.getName(), request.getUrl())
                      )
              )
      );
    } catch (MalformedURLException e) {
      return empty();
    }
  }
}
