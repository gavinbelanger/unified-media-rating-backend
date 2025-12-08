package com.gavinbelanger.myumrp.controller;

import com.gavinbelanger.myumrp.model.Media;
import com.gavinbelanger.myumrp.service.MediaImportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/media")
public class MediaImportController {

    private final MediaImportService mediaImportService;

    public MediaImportController(MediaImportService mediaImportService) {
        this.mediaImportService = mediaImportService;
    }

    @PostMapping("/import")
    public ResponseEntity<Media> importMedia(
            @RequestParam String type,
            @RequestParam String apiId
    ) {
        Media m = mediaImportService.importMedia(type, apiId);
        return ResponseEntity.ok(m);
    }
}
