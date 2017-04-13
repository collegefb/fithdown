package yt.javi.fithdown.app.infrastructure.repositories.inmemory;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import yt.javi.fithdown.core.model.source.Source;
import yt.javi.fithdown.core.model.source.SourceId;

public class SourceRepositoryInMemoryTest {

  private static final String SOURCE_ID = "test";

  private SourceRepositoryInMemory repository;

  @Mock
  private Source source;

  @Mock
  private SourceId sourceId;

  @Before
  public void setUp() throws Exception {
    initMocks(this);
    repository = new SourceRepositoryInMemory();
  }

  @Test
  public void itIsPossibleToSaveASource() {
    when(sourceId.getId()).thenReturn(SOURCE_ID);
    when(source.getSourceId()).thenReturn(sourceId);

    assertThat(repository.save(source), is(source));
  }

  @Test
  public void itIsPossibleToGetAnExistingSourceByItsId() {
    when(sourceId.getId()).thenReturn(SOURCE_ID);
    when(source.getSourceId()).thenReturn(sourceId);
    repository.save(source);

    assertThat(repository.findById(SOURCE_ID).orElse(null), is(source));
  }

  @Test
  public void itIsNotPossibleToGetANonExistingSourceByItsId() {
    assertThat(repository.findById("non_existing").isPresent(), is(false));
  }

  @Test
  public void itIsPossibleToGetAllSources() {
    when(sourceId.getId()).thenReturn(SOURCE_ID);
    when(source.getSourceId()).thenReturn(sourceId);
    repository.save(source);

    assertThat(repository.findAll(), hasItem(source));
  }

  @Test
  public void itIsPossibleToRemoveAnExistingSource() {
    when(sourceId.getId()).thenReturn(SOURCE_ID);
    when(source.getSourceId()).thenReturn(sourceId);
    repository.save(source);

    assertThat(repository.remove(source), is(true));
  }

  @Test
  public void itIsNotPossibleToRemoveANonExistingSource() {
    when(sourceId.getId()).thenReturn(SOURCE_ID);
    when(source.getSourceId()).thenReturn(sourceId);

    assertThat(repository.remove(source), is(false));
  }
}
