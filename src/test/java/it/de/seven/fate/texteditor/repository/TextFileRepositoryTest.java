package it.de.seven.fate.texteditor.repository;

import de.seven.fate.model.builder.annotation.Models;
import de.seven.fate.model.builder.util.CollectionUtil;
import de.seven.fate.model.builder.util.FieldInjectionUtil;
import de.seven.fate.texteditor.SimpleTextEditorServerApplication;
import de.seven.fate.texteditor.entity.TextFileEntity;
import de.seven.fate.texteditor.repository.TextFileRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SimpleTextEditorServerApplication.class)
public class TextFileRepositoryTest {

    @Autowired
    TextFileRepository sut;

    @Models
    List<TextFileEntity> models;

    @Before
    public void setUp() {

        FieldInjectionUtil.injectFields(this);

        sut.saveAll(models);
    }

    @Test
    public void findAllByFileNameLike() {

        TextFileEntity entity = CollectionUtil.random(models);

        List<TextFileEntity> allByNameContains = sut.findAllByFileNameContainingIgnoreCase(entity.getFileName().substring(3));

        assertEquals(1, allByNameContains.size());
        assertEquals(entity, allByNameContains.get(0));
    }
}