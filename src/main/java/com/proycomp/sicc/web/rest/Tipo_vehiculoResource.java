package com.proycomp.sicc.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.proycomp.sicc.domain.Tipo_vehiculo;
import com.proycomp.sicc.repository.Tipo_vehiculoRepository;
import com.proycomp.sicc.web.rest.util.HeaderUtil;
import com.proycomp.sicc.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Tipo_vehiculo.
 */
@RestController
@RequestMapping("/api")
public class Tipo_vehiculoResource {

    private final Logger log = LoggerFactory.getLogger(Tipo_vehiculoResource.class);
        
    @Inject
    private Tipo_vehiculoRepository tipo_vehiculoRepository;
    
    /**
     * POST  /tipo-vehiculos : Create a new tipo_vehiculo.
     *
     * @param tipo_vehiculo the tipo_vehiculo to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tipo_vehiculo, or with status 400 (Bad Request) if the tipo_vehiculo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/tipo-vehiculos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Tipo_vehiculo> createTipo_vehiculo(@RequestBody Tipo_vehiculo tipo_vehiculo) throws URISyntaxException {
        log.debug("REST request to save Tipo_vehiculo : {}", tipo_vehiculo);
        if (tipo_vehiculo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("tipo_vehiculo", "idexists", "A new tipo_vehiculo cannot already have an ID")).body(null);
        }
        Tipo_vehiculo result = tipo_vehiculoRepository.save(tipo_vehiculo);
        return ResponseEntity.created(new URI("/api/tipo-vehiculos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("tipo_vehiculo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tipo-vehiculos : Updates an existing tipo_vehiculo.
     *
     * @param tipo_vehiculo the tipo_vehiculo to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tipo_vehiculo,
     * or with status 400 (Bad Request) if the tipo_vehiculo is not valid,
     * or with status 500 (Internal Server Error) if the tipo_vehiculo couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/tipo-vehiculos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Tipo_vehiculo> updateTipo_vehiculo(@RequestBody Tipo_vehiculo tipo_vehiculo) throws URISyntaxException {
        log.debug("REST request to update Tipo_vehiculo : {}", tipo_vehiculo);
        if (tipo_vehiculo.getId() == null) {
            return createTipo_vehiculo(tipo_vehiculo);
        }
        Tipo_vehiculo result = tipo_vehiculoRepository.save(tipo_vehiculo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("tipo_vehiculo", tipo_vehiculo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tipo-vehiculos : get all the tipo_vehiculos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tipo_vehiculos in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/tipo-vehiculos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Tipo_vehiculo>> getAllTipo_vehiculos(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Tipo_vehiculos");
        Page<Tipo_vehiculo> page = tipo_vehiculoRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tipo-vehiculos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tipo-vehiculos/:id : get the "id" tipo_vehiculo.
     *
     * @param id the id of the tipo_vehiculo to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tipo_vehiculo, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/tipo-vehiculos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Tipo_vehiculo> getTipo_vehiculo(@PathVariable Long id) {
        log.debug("REST request to get Tipo_vehiculo : {}", id);
        Tipo_vehiculo tipo_vehiculo = tipo_vehiculoRepository.findOne(id);
        return Optional.ofNullable(tipo_vehiculo)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /tipo-vehiculos/:id : delete the "id" tipo_vehiculo.
     *
     * @param id the id of the tipo_vehiculo to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/tipo-vehiculos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTipo_vehiculo(@PathVariable Long id) {
        log.debug("REST request to delete Tipo_vehiculo : {}", id);
        tipo_vehiculoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("tipo_vehiculo", id.toString())).build();
    }

}
