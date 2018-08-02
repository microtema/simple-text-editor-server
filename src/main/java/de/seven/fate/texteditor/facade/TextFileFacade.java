package de.seven.fate.texteditor.facade;

import de.seven.fate.texteditor.service.TextFileService;
import de.seven.fate.texteditor.vo.TextFile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TextFileFacade {

    private final TextFileService textFileService;

    public TextFileFacade(TextFileService textFileService) {
        this.textFileService = textFileService;
    }


    public List<TextFile> getTextFiles() {

        return textFileService.getTextFiles();
    }


    public Long createTextFile(TextFile textFile) {

        return textFileService.createTextFile(textFile);
    }


    public Long updateTextFile(TextFile textFile) {

        return textFileService.updateTextFile(textFile);
    }


    public TextFile getTextFile(Long id) {

        return textFileService.getTextFile(id);
    }


    public void deleteTextFile(Long id) {

        textFileService.deleteTextFile(id);
    }


    public boolean existsTextFile(String fileName) {

        return textFileService.existsTextFile(fileName);
    }
}
