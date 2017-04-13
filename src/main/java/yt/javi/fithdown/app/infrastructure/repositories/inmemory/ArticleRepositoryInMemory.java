package yt.javi.fithdown.app.infrastructure.repositories.inmemory;

import static java.util.Collections.emptyList;
import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

import com.rometools.rome.feed.synd.SyndCategory;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import yt.javi.fithdown.core.model.article.Article;
import yt.javi.fithdown.core.model.article.ArticleFactory;
import yt.javi.fithdown.core.model.article.ArticleRepository;

public class ArticleRepositoryInMemory implements ArticleRepository {

  private final SyndFeedInput feedInput;
  private final ArticleFactory factory;
  private Map<String, Article> storage = new HashMap<>();

  public ArticleRepositoryInMemory(SyndFeedInput feedInput, ArticleFactory factory) {
    this.feedInput = feedInput;
    this.factory = factory;
  }

  @Override
  public Article save(Article article) {
    storage.put(article.getArticleId().getId(), article);

    return article;
  }

  @Override
  public Optional<Article> findById(String id) {
    return storage.containsKey(id) ? ofNullable(storage.get(id)) : empty();
  }

  @Override
  public Collection<Article> findAll() {
    return storage.values();
  }

  @Override
  public boolean remove(Article article) {
    Optional<Article> id = findById(article.getArticleId().getId());
    boolean result = false;

    if (id.isPresent()) {
      result = true;
      storage.remove(article.getArticleId().getId());
    }

    return result;
  }

  @Override
  public Collection<Article> fetchFromUrl(URL url) {
    try {
      return feedInput
          .build(new XmlReader(url))
          .getEntries()
          .stream()
          .map(
              syndEntry -> {
                try {
                  return factory
                      .getArticle(
                          syndEntry.getPublishedDate().getTime(),
                          syndEntry.getLink(),
                          syndEntry.getTitle(),
                          syndEntry.getDescription().getValue())
                      .setCategories(
                          syndEntry
                              .getCategories()
                              .stream()
                              .map(SyndCategory::getName)
                              .collect(toList()));
                } catch (MalformedURLException e) {
                  return null;
                }
              })
          .filter(Objects::nonNull)
          .collect(toList());
    } catch (FeedException | IOException e) {
      return emptyList();
    }
  }
}
