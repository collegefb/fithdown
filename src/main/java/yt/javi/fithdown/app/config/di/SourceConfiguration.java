package yt.javi.fithdown.app.config.di;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import yt.javi.fithdown.app.infrastructure.repositories.inmemory.SourceRepositoryInMemory;
import yt.javi.fithdown.core.application.source.responses.SourceResponseFactory;
import yt.javi.fithdown.core.application.source.services.CreateSourceService;
import yt.javi.fithdown.core.application.source.services.GetAllSourcesService;
import yt.javi.fithdown.core.application.source.services.GetSourceService;
import yt.javi.fithdown.core.model.source.SourceFactory;
import yt.javi.fithdown.core.model.source.SourceRepository;

@Configuration
public class SourceConfiguration {
  @Bean
  public SourceResponseFactory sourceResponseFactory() {
    return new SourceResponseFactory();
  }

  @Bean
  public SourceFactory sourceFactory() {
    return new SourceFactory();
  }

  @Bean
  public SourceRepository sourceRepository() {
    return new SourceRepositoryInMemory();
  }

  @Bean
  public CreateSourceService createSourceService(
          SourceFactory factory,
          SourceRepository repository,
          SourceResponseFactory responseFactory
  ) {
    return new CreateSourceService(factory, repository, responseFactory);
  }

  @Bean
  public GetAllSourcesService getAllSourcesService(
          SourceRepository repository,
          SourceResponseFactory responseFactory
  ) {
    return new GetAllSourcesService(repository, responseFactory);
  }

  @Bean
  public GetSourceService getSourceService(
          SourceRepository repository,
          SourceResponseFactory responseFactory
  ) {
    return new GetSourceService(repository, responseFactory);
  }
}
