package de.seven.fate.texteditor.service.impl;

import de.seven.fate.model.builder.annotation.Model;
import de.seven.fate.model.builder.util.FieldInjectionUtil;
import de.seven.fate.texteditor.converter.TextFile2TextFileEntityConverter;
import de.seven.fate.texteditor.converter.TextFileEntity2TextFileConverter;
import de.seven.fate.texteditor.entity.TextFileEntity;
import de.seven.fate.texteditor.exception.NoSuchEntityException;
import de.seven.fate.texteditor.repository.TextFileRepository;
import de.seven.fate.texteditor.vo.TextFile;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DefaultTextFileServiceTest {

    @InjectMocks
    DefaultTextFileService sut;

    @Mock
    TextFileRepository textFileRepository;

    @Mock
    TextFile2TextFileEntityConverter textFile2TextFileEntityConverter;

    @Mock
    TextFileEntity2TextFileConverter textFileEntity2TextFileConverter;

    @Mock
    TextFile model;

    @Mock
    TextFileEntity entity;

    @Mock
    List<TextFileEntity> entities;

    @Model
    Long id;

    @Model
    String textFileName;

    @Before
    public void setUp() {
        FieldInjectionUtil.injectFields(this);
    }

    @Test
    public void getTextFiles() {

        when(textFileRepository.findAllSorted()).thenReturn(entities);

        sut.getTextFiles();

        verify(textFileRepository).findAllSorted();
        verify(textFileEntity2TextFileConverter).convertToList(entities);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createTextFileWillThrowException() {

        when(textFile2TextFileEntityConverter.convert(model)).thenReturn(entity);
        when(textFileRepository.exists(entity)).thenReturn(true);

        sut.createTextFile(model);
    }

    @Test
    public void createTextFile() {

        when(textFile2TextFileEntityConverter.convert(model)).thenReturn(entity);
        when(textFileRepository.exists(entity)).thenReturn(false);
        when(textFileRepository.save(entity)).thenReturn(entity);

        Long id = sut.createTextFile(model);

        assertEquals(entity.getId(), id);

        verify(textFileRepository).save(entity);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateTextFileWillThrowException() {

        when(textFile2TextFileEntityConverter.convert(model)).thenReturn(entity);
        when(textFileRepository.exists(entity)).thenReturn(false);

        sut.updateTextFile(model);
    }

    @Test
    public void updateTextFile() {

        when(textFile2TextFileEntityConverter.convert(model)).thenReturn(entity);
        when(textFileRepository.exists(entity)).thenReturn(true);
        when(textFileRepository.saveOrUpdate(entity)).thenReturn(entity);

        Long id = sut.updateTextFile(model);

        assertEquals(entity.getId(), id);

        verify(textFileRepository).saveOrUpdate(entity);
    }

    @Test(expected = NoSuchEntityException.class)
    public void getTextFileWillThrowException() {

        when(textFileRepository.getOne(id)).thenThrow(new NoSuchEntityException("", ""));

        sut.getTextFile(id);
    }

    @Test
    public void getTextFile() {

        when(textFileRepository.getOne(id)).thenReturn(entity);
        when(textFileEntity2TextFileConverter.convert(entity)).thenReturn(model);

        TextFile textFile = sut.getTextFile(id);

        assertEquals(model, textFile);
    }

    @Test
    public void deleteTextFile() {

        sut.deleteTextFile(id);

        verify(textFileRepository).deleteById(id);
    }

    @Test
    public void existsTextFileWillReturnWithFalse() {

        when(textFileRepository.findAllByFileNameContainingIgnoreCase(textFileName)).thenReturn(Collections.emptyList());

        boolean exists = sut.existsTextFile(textFileName);

        assertFalse(exists);

        verify(textFileRepository).findAllByFileNameContainingIgnoreCase(textFileName);
    }

    @Test
    public void existsTextFile() {

        when(textFileRepository.findAllByFileNameContainingIgnoreCase(textFileName)).thenReturn(entities);

        boolean exists = sut.existsTextFile(textFileName);

        assertTrue(exists);

        verify(textFileRepository).findAllByFileNameContainingIgnoreCase(textFileName);
    }
}