package yt.javi.fithdown.core.application.article.services;

import yt.javi.fithdown.core.application.Service;
import yt.javi.fithdown.core.application.article.requests.CreateArticleRequest;
import yt.javi.fithdown.core.application.article.responses.ArticleResponse;
import yt.javi.fithdown.core.application.article.responses.ArticleResponseFactory;
import yt.javi.fithdown.core.model.article.ArticleFactory;
import yt.javi.fithdown.core.model.article.ArticleRepository;

import java.net.MalformedURLException;
import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;

public class CreateArticleService implements Service<CreateArticleRequest, Optional<ArticleResponse>> {
  private final ArticleFactory articleFactory;

  private final ArticleRepository articleRepository;

  private final ArticleResponseFactory articleResponseFactory;

  public CreateArticleService(
          ArticleFactory articleFactory,
          ArticleRepository articleRepository,
          ArticleResponseFactory articleResponseFactory
  ) {
    this.articleFactory = articleFactory;
    this.articleRepository = articleRepository;
    this.articleResponseFactory = articleResponseFactory;
  }

  @Override
  public Optional<ArticleResponse> execute(CreateArticleRequest request) {
    try {
      return ofNullable(
              articleResponseFactory.articleResponse(
                      articleRepository.save(
                              articleFactory.getArticle(
                                      request.getCreated(),
                                      request.getUrl(),
                                      request.getTitle(),
                                      request.getContent()
                              ).setCategories(request.getCategories())
                      )
              )
      );
    } catch (MalformedURLException e) {
      return empty();
    }
  }
}
