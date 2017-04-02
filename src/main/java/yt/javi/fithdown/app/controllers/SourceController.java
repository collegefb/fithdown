package yt.javi.fithdown.app.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yt.javi.fithdown.app.requests.CreateSourceRequestBody;
import yt.javi.fithdown.app.responses.SourceResponseEntity;
import yt.javi.fithdown.core.application.source.requests.CreateSourceRequest;
import yt.javi.fithdown.core.application.source.requests.GetAllSourcesRequest;
import yt.javi.fithdown.core.application.source.requests.GetSourceRequest;
import yt.javi.fithdown.core.application.source.services.CreateSourceService;
import yt.javi.fithdown.core.application.source.services.GetAllSourcesService;
import yt.javi.fithdown.core.application.source.services.GetSourceService;

import java.util.Collection;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(value = "/sources", produces = APPLICATION_JSON_UTF8_VALUE, consumes = APPLICATION_JSON_UTF8_VALUE)
public class SourceController {
  private final CreateSourceService createSourceService;

  private final GetAllSourcesService getAllSourcesService;

  private final GetSourceService getSourceService;

  public SourceController(
          CreateSourceService createSourceService,
          GetAllSourcesService getAllSourcesService,
          GetSourceService getSourceService
  ) {
    this.createSourceService = createSourceService;
    this.getAllSourcesService = getAllSourcesService;
    this.getSourceService = getSourceService;
  }

  @RequestMapping
  public ResponseEntity<Collection<SourceResponseEntity>> getAll() {
    return new ResponseEntity<Collection<SourceResponseEntity>>(
            getAllSourcesService.execute(new GetAllSourcesRequest()).stream().map(SourceResponseEntity::new).collect(toList()),
            OK
    );
  }

  @RequestMapping(value = "/{id}")
  public ResponseEntity<SourceResponseEntity> get(@PathVariable("id") String id) {
    return getSourceService.execute(new GetSourceRequest(id))
            .map(sourceResponse -> new ResponseEntity<SourceResponseEntity>(new SourceResponseEntity(sourceResponse), OK))
            .orElse(new ResponseEntity<SourceResponseEntity>(NOT_FOUND));
  }

  @RequestMapping(method = POST)
  public ResponseEntity<SourceResponseEntity> post(@RequestBody CreateSourceRequestBody requestBody) {
    return new ResponseEntity<SourceResponseEntity>(
            new SourceResponseEntity(
                    createSourceService.execute(
                            new CreateSourceRequest(requestBody.getName(), requestBody.getUrl())
                    )
            ),
            CREATED
    );
  }
}
