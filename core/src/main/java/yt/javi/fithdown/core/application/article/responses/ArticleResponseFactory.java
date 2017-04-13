package yt.javi.fithdown.core.application.article.responses;

import yt.javi.fithdown.core.model.article.Article;

public class ArticleResponseFactory {

  public ArticleResponse articleResponse(Article article) {
    return new ArticleResponse(article);
  }
}
