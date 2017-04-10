package yt.javi.fithdown.core.model.article;

import java.net.URL;
import java.util.Collection;
import java.util.Optional;

public interface ArticleRepository {
  Article save(Article article);

  Optional<Article> findById(String id);

  Collection<Article> findAll();

  boolean remove(Article article);

  Collection<Article> fetchFromUrl(URL url);
}
