package com.joebrooks.mapshotservice.user.domain.status;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@RestController
@RequestMapping("/status/sitemap")
public class SiteMapController {

    @GetMapping
    public ResponseEntity<FileSystemResource> returnSiteMap() {
        HttpHeaders headers = new HttpHeaders();

        headers.setContentDisposition(ContentDisposition
                .builder("attachment")
                .filename("sitemap.xml")
                .build());

        return ResponseEntity.ok()
                .headers(headers)
                .body(new FileSystemResource(new File("sitemap.xml")));
    }
}
