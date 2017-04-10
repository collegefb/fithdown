package yt.javi.fithdown.app.config.di;

import com.rometools.rome.io.SyndFeedInput;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import yt.javi.fithdown.app.infrastructure.repositories.inmemory.ArticleRepositoryInMemory;
import yt.javi.fithdown.app.scheduled.FetchFeedsScheduledTask;
import yt.javi.fithdown.core.application.article.responses.ArticleResponseFactory;
import yt.javi.fithdown.core.application.article.services.CreateArticleService;
import yt.javi.fithdown.core.application.article.services.FetchArticlesFromUrlService;
import yt.javi.fithdown.core.application.article.services.GetAllArticlesService;
import yt.javi.fithdown.core.application.article.services.GetArticleService;
import yt.javi.fithdown.core.application.source.services.GetAllSourcesService;
import yt.javi.fithdown.core.model.article.ArticleFactory;
import yt.javi.fithdown.core.model.article.ArticleRepository;

@Configuration
public class ArticleConfiguration {
  @Bean
  public SyndFeedInput syndFeedInput() {
    return new SyndFeedInput();
  }

  @Bean
  public ArticleResponseFactory articleResponseFactory() {
    return new ArticleResponseFactory();
  }

  @Bean
  public ArticleFactory articleFactory() {
    return new ArticleFactory();
  }

  @Bean
  public ArticleRepository articleRepository(
          SyndFeedInput syndFeedInput,
          ArticleFactory factory
  ) {
    return new ArticleRepositoryInMemory(syndFeedInput, factory);
  }

  @Bean
  public CreateArticleService createArticleService(
          ArticleFactory factory,
          ArticleRepository repository,
          ArticleResponseFactory responseFactory
  ) {
    return new CreateArticleService(factory, repository, responseFactory);
  }

  @Bean
  public GetAllArticlesService getAllArticlesService(
          ArticleRepository repository,
          ArticleResponseFactory responseFactory
  ) {
    return new GetAllArticlesService(repository, responseFactory);
  }

  @Bean
  public GetArticleService getArticleService(
          ArticleRepository repository,
          ArticleResponseFactory responseFactory
  ) {
    return new GetArticleService(repository, responseFactory);
  }

  @Bean
  public FetchArticlesFromUrlService fetchArticlesFromUrlService(
          ArticleRepository repository,
          ArticleResponseFactory responseFactory
  ) {
    return new FetchArticlesFromUrlService(repository, responseFactory);
  }

  @Bean
  public FetchFeedsScheduledTask fetchFeedsScheduledTask(
          GetAllSourcesService getAllSourcesService,
          FetchArticlesFromUrlService fetchArticlesFromUrlService,
          CreateArticleService createArticleService
  ) {
    return new FetchFeedsScheduledTask(
            getAllSourcesService,
            fetchArticlesFromUrlService,
            createArticleService
    );
  }
}
