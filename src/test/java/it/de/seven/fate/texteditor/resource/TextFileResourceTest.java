package it.de.seven.fate.texteditor.resource;

import de.seven.fate.model.builder.annotation.Model;
import de.seven.fate.model.builder.annotation.Models;
import de.seven.fate.model.builder.util.CollectionUtil;
import de.seven.fate.model.builder.util.FieldInjectionUtil;
import de.seven.fate.texteditor.SimpleTextEditorServerApplication;
import de.seven.fate.texteditor.converter.TextFile2TextFileEntityConverter;
import de.seven.fate.texteditor.converter.TextFileEntity2TextFileConverter;
import de.seven.fate.texteditor.entity.TextFileEntity;
import de.seven.fate.texteditor.repository.TextFileRepository;
import de.seven.fate.texteditor.resource.TextFileResource;
import de.seven.fate.texteditor.vo.ErrorMessage;
import de.seven.fate.texteditor.vo.TextFile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SimpleTextEditorServerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TextFileResourceTest {

    @Autowired
    TextFileResource it;

    @Autowired
    TextFileRepository textFileRepository;

    @Autowired
    TextFile2TextFileEntityConverter textFile2TextFileEntityConverter;

    @Autowired
    TextFileEntity2TextFileConverter textFileEntity2TextFileConverter;

    @Autowired
    TestRestTemplate restTemplate;

    String PATH = "/texts";

    @Models
    List<TextFile> models;

    @Model
    TextFile model;

    ParameterizedTypeReference<List<TextFile>> typeReference = new ParameterizedTypeReference<List<TextFile>>() {
    };

    ParameterizedTypeReference<List<ErrorMessage>> typeReferenceObjectError = new ParameterizedTypeReference<List<ErrorMessage>>() {
    };

    @Before
    public void setUp() {

        FieldInjectionUtil.injectFields(this);

        List<TextFileEntity> textFileEntities = textFile2TextFileEntityConverter.convertToList(models);

        textFileRepository.saveAll(textFileEntities);

        textFileEntity2TextFileConverter.update(textFileEntities, models);
    }

    @After
    public void tearDown() {
        textFileRepository.deleteAll();
    }

    @Test
    public void query() {

        //when
        ResponseEntity<List<TextFile>> response = restTemplate.exchange(PATH, HttpMethod.GET, null, typeReference);

        //then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        List<TextFile> entries = response.getBody();

        assertNotNull(entries);
        assertEquals(models.size(), entries.size());

    }

    @Test
    public void createTextFileWillReturnWithBadRequest() {

        model.setFileName(null);

        //when
        ResponseEntity<List<ErrorMessage>> response = restTemplate.exchange(PATH, HttpMethod.POST, new HttpEntity<>(model), typeReferenceObjectError);

        //then
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        List<ErrorMessage> responseBody = response.getBody();

        assertNotNull(responseBody);

        assertEquals(1, responseBody.size());
        assertEquals("textFile", responseBody.get(0).getObjectName());
        assertEquals("fileName", responseBody.get(0).getPropertyName());
        assertEquals("darf nicht null sein", responseBody.get(0).getDefaultMessage());
    }

    @Test
    public void createTextFile() {

        //when
        ResponseEntity<Long> response = restTemplate.exchange(PATH, HttpMethod.POST, new HttpEntity<>(model), ParameterizedTypeReference.forType(Long.class));

        //then
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        Long id = response.getBody();

        assertNotNull(id);
    }

    @Test
    public void updateTextFile() {

        //given
        TextFile textFile = CollectionUtil.random(models);

        textFile.setFileName(textFile.getFileName() + ".changed");

        //when
        ResponseEntity<Long> response = restTemplate.exchange(PATH, HttpMethod.PUT, new HttpEntity<>(textFile), ParameterizedTypeReference.forType(Long.class));

        //then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        Long id = response.getBody();

        assertNotNull(id);

    }

    @Test
    public void deleteTextFile() {

        //given
        TextFile textFile = CollectionUtil.random(models);

        //when
        ResponseEntity response = restTemplate.exchange(PATH + "/" + textFile.getId(), HttpMethod.DELETE, null, ParameterizedTypeReference.forType(Void.class));

        //then
        assertNotNull(response);
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
    }

    @Test
    public void existsTextFile() {

        //given
        TextFile textFile = CollectionUtil.random(models);

        //when
        ResponseEntity<Boolean> response = restTemplate.exchange(PATH + "/" + textFile.getFileName(), HttpMethod.GET, null, ParameterizedTypeReference.forType(Boolean.class));

        //then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody());
    }
}