package de.seven.fate.texteditor.resource;

import de.seven.fate.texteditor.facade.TextFileFacade;
import de.seven.fate.texteditor.vo.TextFile;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/texts", produces = MediaType.APPLICATION_JSON_VALUE)
public class TextFileResource {

    private final TextFileFacade textFileFacade;

    public TextFileResource(TextFileFacade textFileFacade) {
        this.textFileFacade = textFileFacade;
    }

    @GetMapping
    public ResponseEntity query() {

        return ResponseEntity.ok(textFileFacade.getTextFiles());
    }

    @PostMapping
    public ResponseEntity createTextFile(@Valid @RequestBody TextFile textFile, BindingResult result) {

        return ResponseEntity.status(HttpStatus.CREATED).body(textFileFacade.createTextFile(textFile));
    }

    @PutMapping
    public ResponseEntity updateTextFile(@RequestBody TextFile textFile, BindingResult result) {

        return ResponseEntity.ok(textFileFacade.updateTextFile(textFile));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteTextFile(@PathVariable Long id) {

        textFileFacade.deleteTextFile(id);

        return ResponseEntity.accepted().build();
    }

    @GetMapping("/{fileName}")
    public ResponseEntity existsTextFile(@PathVariable String fileName) {

        return ResponseEntity.ok(textFileFacade.existsTextFile(fileName));
    }
}
