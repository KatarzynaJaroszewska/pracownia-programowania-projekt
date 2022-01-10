package com.uam.pracowniaprogramowaniaprojekt.controller;

import com.uam.pracowniaprogramowaniaprojekt.service.ImportExportDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/data")
public class ImportExportDataRestController {

    private final ImportExportDataService importExportDataService;

    @Autowired
    public ImportExportDataRestController(ImportExportDataService importExportDataService) {
        this.importExportDataService = importExportDataService;
    }

    @GetMapping("/export")
    public ResponseEntity<String> exportDatabase() throws IOException {
        String exportData = importExportDataService.exportData();
        return ResponseEntity.ok()
                .body(exportData);
    }

    @PostMapping("/import")
    public ResponseEntity<String> importDatabase(@RequestBody String data) throws IOException {
        importExportDataService.importData(data);
        return ResponseEntity.ok()
                .body("Data was added successfully");
    }
}
