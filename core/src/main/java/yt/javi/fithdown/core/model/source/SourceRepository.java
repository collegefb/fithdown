package yt.javi.fithdown.core.model.source;

import java.util.Collection;
import java.util.Optional;

public interface SourceRepository {
  Source save(Source source);

  Optional<Source> findById(String id);

  Collection<Source> findAll();

  boolean remove(Source source);
}
