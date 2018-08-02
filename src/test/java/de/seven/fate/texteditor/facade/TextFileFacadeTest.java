package de.seven.fate.texteditor.facade;

import de.seven.fate.model.builder.annotation.Model;
import de.seven.fate.model.builder.util.FieldInjectionUtil;
import de.seven.fate.texteditor.service.TextFileService;
import de.seven.fate.texteditor.vo.TextFile;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class TextFileFacadeTest {

    @InjectMocks
    TextFileFacade sut;

    @Mock
    TextFileService textFileService;

    @Mock
    TextFile model;

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

        sut.getTextFiles();

        verify(textFileService).getTextFiles();
    }

    @Test
    public void createTextFile() {

        sut.createTextFile(model);

        verify(textFileService).createTextFile(model);
    }

    @Test
    public void updateTextFile() {

        sut.updateTextFile(model);

        verify(textFileService).updateTextFile(model);
    }

    @Test
    public void getTextFile() {

        sut.getTextFile(id);

        verify(textFileService).getTextFile(id);
    }

    @Test
    public void deleteTextFile() {

        sut.deleteTextFile(id);

        verify(textFileService).deleteTextFile(id);
    }

    @Test
    public void existsTextFile() {

        sut.existsTextFile(textFileName);

        verify(textFileService).existsTextFile(textFileName);
    }
}