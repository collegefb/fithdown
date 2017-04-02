package yt.javi.fithdown.app.infrastructure.repositories.inmemory;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import yt.javi.fithdown.core.model.source.Source;
import yt.javi.fithdown.core.model.source.SourceId;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

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
    doReturn(SOURCE_ID).when(sourceId).getId();
    doReturn(sourceId).when(source).getSourceId();

    assertThat(repository.save(source), is(source));
    verify(sourceId, times(1)).getId();
  }

  @Test
  public void itIsPossibleToGetAnExistingSourceByItsId() {
    doReturn(SOURCE_ID).when(sourceId).getId();
    doReturn(sourceId).when(source).getSourceId();
    repository.save(source);

    assertThat(repository.findById(SOURCE_ID).orElse(null), is(source));
  }

  @Test
  public void itIsNotPossibleToGetANonExistingSourceByItsId() {
    assertThat(repository.findById("non_existing").isPresent(), is(false));
  }

  @Test
  public void itIsPossibleToGetAllSources() {
    doReturn(SOURCE_ID).when(sourceId).getId();
    doReturn(sourceId).when(source).getSourceId();
    repository.save(source);

    assertThat(repository.findAll(), hasItem(source));
  }

  @Test
  public void itIsPossibleToRemoveAnExistingSource() {
    doReturn(SOURCE_ID).when(sourceId).getId();
    doReturn(sourceId).when(source).getSourceId();
    repository.save(source);

    assertThat(repository.remove(source), is(true));
  }

  @Test
  public void itIsNotPossibleToRemoveANonExistingSource() {
    doReturn(SOURCE_ID).when(sourceId).getId();
    doReturn(sourceId).when(source).getSourceId();

    assertThat(repository.remove(source), is(false));
  }
}