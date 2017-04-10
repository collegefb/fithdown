package yt.javi.fithdown.core.application.article.services;

import yt.javi.fithdown.core.application.Service;
import yt.javi.fithdown.core.application.article.requests.GetAllArticlesRequest;
import yt.javi.fithdown.core.application.article.responses.ArticleResponse;
import yt.javi.fithdown.core.application.article.responses.ArticleResponseFactory;
import yt.javi.fithdown.core.model.article.ArticleRepository;

import java.util.Collection;

import static java.util.stream.Collectors.toList;

public class GetAllArticlesService implements Service<GetAllArticlesRequest, Collection<ArticleResponse>> {
  private final ArticleRepository repository;

  private final ArticleResponseFactory responseFactory;

  public GetAllArticlesService(ArticleRepository repository, ArticleResponseFactory responseFactory) {
    this.repository = repository;
    this.responseFactory = responseFactory;
  }

  @Override
  public Collection<ArticleResponse> execute(GetAllArticlesRequest request) {
    return repository.findAll().stream().map(responseFactory::articleResponse).collect(toList());
  }
}
