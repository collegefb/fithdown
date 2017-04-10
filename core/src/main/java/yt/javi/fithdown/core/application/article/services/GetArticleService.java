package yt.javi.fithdown.core.application.article.services;

import yt.javi.fithdown.core.application.Service;
import yt.javi.fithdown.core.application.article.requests.GetArticleRequest;
import yt.javi.fithdown.core.application.article.responses.ArticleResponse;
import yt.javi.fithdown.core.application.article.responses.ArticleResponseFactory;
import yt.javi.fithdown.core.model.article.ArticleRepository;

import java.util.Optional;

public class GetArticleService implements Service<GetArticleRequest, Optional<ArticleResponse>> {
  private final ArticleRepository repository;

  private final ArticleResponseFactory responseFactory;

  public GetArticleService(ArticleRepository repository, ArticleResponseFactory responseFactory) {
    this.repository = repository;
    this.responseFactory = responseFactory;
  }

  @Override
  public Optional<ArticleResponse> execute(GetArticleRequest request) {
    return repository.findById(request.getId()).map(responseFactory::articleResponse);
  }
}
