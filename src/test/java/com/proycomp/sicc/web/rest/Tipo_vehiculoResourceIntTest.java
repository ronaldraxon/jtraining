package com.proycomp.sicc.web.rest;

import com.proycomp.sicc.SiccApp;
import com.proycomp.sicc.domain.Tipo_vehiculo;
import com.proycomp.sicc.repository.Tipo_vehiculoRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the Tipo_vehiculoResource REST controller.
 *
 * @see Tipo_vehiculoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SiccApp.class)
@WebAppConfiguration
@IntegrationTest
public class Tipo_vehiculoResourceIntTest {

    private static final String DEFAULT_TIPO = "AAAAA";
    private static final String UPDATED_TIPO = "BBBBB";
    private static final String DEFAULT_DESCRIPCION_USO = "AAAAA";
    private static final String UPDATED_DESCRIPCION_USO = "BBBBB";

    @Inject
    private Tipo_vehiculoRepository tipo_vehiculoRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTipo_vehiculoMockMvc;

    private Tipo_vehiculo tipo_vehiculo;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Tipo_vehiculoResource tipo_vehiculoResource = new Tipo_vehiculoResource();
        ReflectionTestUtils.setField(tipo_vehiculoResource, "tipo_vehiculoRepository", tipo_vehiculoRepository);
        this.restTipo_vehiculoMockMvc = MockMvcBuilders.standaloneSetup(tipo_vehiculoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        tipo_vehiculo = new Tipo_vehiculo();
        tipo_vehiculo.setTipo(DEFAULT_TIPO);
        tipo_vehiculo.setDescripcion_uso(DEFAULT_DESCRIPCION_USO);
    }

    @Test
    @Transactional
    public void createTipo_vehiculo() throws Exception {
        int databaseSizeBeforeCreate = tipo_vehiculoRepository.findAll().size();

        // Create the Tipo_vehiculo

        restTipo_vehiculoMockMvc.perform(post("/api/tipo-vehiculos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tipo_vehiculo)))
                .andExpect(status().isCreated());

        // Validate the Tipo_vehiculo in the database
        List<Tipo_vehiculo> tipo_vehiculos = tipo_vehiculoRepository.findAll();
        assertThat(tipo_vehiculos).hasSize(databaseSizeBeforeCreate + 1);
        Tipo_vehiculo testTipo_vehiculo = tipo_vehiculos.get(tipo_vehiculos.size() - 1);
        assertThat(testTipo_vehiculo.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testTipo_vehiculo.getDescripcion_uso()).isEqualTo(DEFAULT_DESCRIPCION_USO);
    }

    @Test
    @Transactional
    public void getAllTipo_vehiculos() throws Exception {
        // Initialize the database
        tipo_vehiculoRepository.saveAndFlush(tipo_vehiculo);

        // Get all the tipo_vehiculos
        restTipo_vehiculoMockMvc.perform(get("/api/tipo-vehiculos?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(tipo_vehiculo.getId().intValue())))
                .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())))
                .andExpect(jsonPath("$.[*].descripcion_uso").value(hasItem(DEFAULT_DESCRIPCION_USO.toString())));
    }

    @Test
    @Transactional
    public void getTipo_vehiculo() throws Exception {
        // Initialize the database
        tipo_vehiculoRepository.saveAndFlush(tipo_vehiculo);

        // Get the tipo_vehiculo
        restTipo_vehiculoMockMvc.perform(get("/api/tipo-vehiculos/{id}", tipo_vehiculo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(tipo_vehiculo.getId().intValue()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.toString()))
            .andExpect(jsonPath("$.descripcion_uso").value(DEFAULT_DESCRIPCION_USO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTipo_vehiculo() throws Exception {
        // Get the tipo_vehiculo
        restTipo_vehiculoMockMvc.perform(get("/api/tipo-vehiculos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipo_vehiculo() throws Exception {
        // Initialize the database
        tipo_vehiculoRepository.saveAndFlush(tipo_vehiculo);
        int databaseSizeBeforeUpdate = tipo_vehiculoRepository.findAll().size();

        // Update the tipo_vehiculo
        Tipo_vehiculo updatedTipo_vehiculo = new Tipo_vehiculo();
        updatedTipo_vehiculo.setId(tipo_vehiculo.getId());
        updatedTipo_vehiculo.setTipo(UPDATED_TIPO);
        updatedTipo_vehiculo.setDescripcion_uso(UPDATED_DESCRIPCION_USO);

        restTipo_vehiculoMockMvc.perform(put("/api/tipo-vehiculos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedTipo_vehiculo)))
                .andExpect(status().isOk());

        // Validate the Tipo_vehiculo in the database
        List<Tipo_vehiculo> tipo_vehiculos = tipo_vehiculoRepository.findAll();
        assertThat(tipo_vehiculos).hasSize(databaseSizeBeforeUpdate);
        Tipo_vehiculo testTipo_vehiculo = tipo_vehiculos.get(tipo_vehiculos.size() - 1);
        assertThat(testTipo_vehiculo.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testTipo_vehiculo.getDescripcion_uso()).isEqualTo(UPDATED_DESCRIPCION_USO);
    }

    @Test
    @Transactional
    public void deleteTipo_vehiculo() throws Exception {
        // Initialize the database
        tipo_vehiculoRepository.saveAndFlush(tipo_vehiculo);
        int databaseSizeBeforeDelete = tipo_vehiculoRepository.findAll().size();

        // Get the tipo_vehiculo
        restTipo_vehiculoMockMvc.perform(delete("/api/tipo-vehiculos/{id}", tipo_vehiculo.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Tipo_vehiculo> tipo_vehiculos = tipo_vehiculoRepository.findAll();
        assertThat(tipo_vehiculos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
