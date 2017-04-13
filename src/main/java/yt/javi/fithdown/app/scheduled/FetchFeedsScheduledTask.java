package yt.javi.fithdown.app.scheduled;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import yt.javi.fithdown.core.application.article.requests.CreateArticleRequest;
import yt.javi.fithdown.core.application.article.requests.FetchArticlesFromUrlRequest;
import yt.javi.fithdown.core.application.article.services.CreateArticleService;
import yt.javi.fithdown.core.application.article.services.FetchArticlesFromUrlService;
import yt.javi.fithdown.core.application.source.requests.GetAllSourcesRequest;
import yt.javi.fithdown.core.application.source.services.GetAllSourcesService;

public class FetchFeedsScheduledTask {

  private static final Logger LOGGER = LoggerFactory.getLogger(FetchFeedsScheduledTask.class);

  private final GetAllSourcesService getAllSourcesService;

  private final FetchArticlesFromUrlService fetchArticlesFromUrlService;

  private final CreateArticleService createArticleService;

  public FetchFeedsScheduledTask(
      GetAllSourcesService getAllSourcesService,
      FetchArticlesFromUrlService fetchArticlesFromUrlService,
      CreateArticleService createArticleService) {
    this.getAllSourcesService = getAllSourcesService;
    this.fetchArticlesFromUrlService = fetchArticlesFromUrlService;
    this.createArticleService = createArticleService;
  }

  @Scheduled(fixedRate = 3600000)
  public Map<String, Integer> fetchArticlesFromSources() {
    Map<String, Integer> executionResult = new HashMap<>();

    getAllSourcesService
        .execute(new GetAllSourcesRequest())
        .forEach(
            sourceResponse -> {
              try {
                executionResult.put(sourceResponse.getName(), 0);
                fetchArticlesFromUrlService
                    .execute(new FetchArticlesFromUrlRequest(sourceResponse.getUrl()))
                    .forEach(
                        articleResponse -> {
                          createArticleService
                              .execute(
                                  new CreateArticleRequest(
                                      articleResponse.getCreated(),
                                      articleResponse.getUrl(),
                                      articleResponse.getTitle(),
                                      articleResponse.getContent(),
                                      articleResponse.getCategories()))
                              .ifPresent(
                                  articleResponse1 ->
                                      executionResult.put(
                                          sourceResponse.getName(),
                                          executionResult.get(sourceResponse.getName()) + 1));
                        });
              } catch (MalformedURLException e) {
                LOGGER.error(e.getMessage(), e);
              }
            });

    return executionResult;
  }
}
