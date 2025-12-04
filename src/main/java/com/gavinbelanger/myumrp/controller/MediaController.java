package com.gavinbelanger.myumrp.controller;

import com.gavinbelanger.myumrp.model.Media;
import com.gavinbelanger.myumrp.service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/media")
public class MediaController {

    @Autowired
    private MediaService mediaService;

    @PostMapping("/add")
    public Media addMedia(@RequestBody Media media) {
        return mediaService.saveMedia(media);
    }

    @GetMapping("/all")
    public List<Media> getAllMedia() {
        return mediaService.getAllMedia();
    }
}

