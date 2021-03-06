package yt.javi.fithdown.core.model.source;

import static java.util.UUID.fromString;
import static java.util.UUID.randomUUID;

import java.util.UUID;

public class SourceId {

  private final UUID id;

  SourceId() {
    this.id = randomUUID();
  }

  SourceId(String id) {
    this.id = fromString(id);
  }

  public String getId() {
    return id.toString();
  }
}
