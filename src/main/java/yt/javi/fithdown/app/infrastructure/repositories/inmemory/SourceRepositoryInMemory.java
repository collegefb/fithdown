package yt.javi.fithdown.app.infrastructure.repositories.inmemory;

import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import yt.javi.fithdown.core.model.source.Source;
import yt.javi.fithdown.core.model.source.SourceRepository;

public class SourceRepositoryInMemory implements SourceRepository {

  private Map<String, Source> storage = new HashMap<>();

  @Override
  public Source save(Source source) {
    storage.put(source.getSourceId().getId(), source);

    return source;
  }

  @Override
  public Optional<Source> findById(String id) {
    return storage.containsKey(id) ? ofNullable(storage.get(id)) : empty();
  }

  @Override
  public Collection<Source> findAll() {
    return storage.values();
  }

  @Override
  public boolean remove(Source source) {
    Optional<Source> id = findById(source.getSourceId().getId());
    boolean result = false;

    if (id.isPresent()) {
      result = true;
      storage.remove(source.getSourceId().getId());
    }

    return result;
  }
}
