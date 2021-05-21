package com.wxj.work.controller;

import com.wxj.work.entity.File;
import com.wxj.work.mapper.FileMapper;
import com.wxj.work.service.FileService;
import com.wxj.work.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Paths;

@RestController
@RequestMapping("/file")
public class FileController {

    private String uploadPathStr;

    @Autowired
    private FileService fileService;
    @Autowired
    private FileMapper fileMapper;

    @PostMapping("/photoUpload")
    @ResponseBody
    public String photoUpload(@RequestParam("file") MultipartFile file, String number){
        return fileService.photoUpload(file, number);
    }

    @PostMapping("/upload")
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile file, String workId, String workFlowId) {
        return fileService.upload(file, workId, workFlowId);

    }

    @PostMapping("/download")
    public ResponseEntity<FileSystemResource> download(@RequestParam String filename)
    {
        if(filename == null || filename.isEmpty()){
            return null;
        }else{
            File path= fileMapper.queryPath(filename);
            uploadPathStr=path.getPath();
            java.io.File file = Paths.get(uploadPathStr).toFile();
            if(file.exists() && file.canRead())
            {
                System.out.println("download file , filename is "+filename);
                return ResponseEntity.ok().body(new FileSystemResource(file));
                        //.contentType(file.getName().contains(".jpg") ? MediaType.IMAGE_JPEG : MediaType.IMAGE_PNG)

            }else {
                return null;
            }
        }

    }

    @PostMapping("/query")
    public Result query(@RequestBody File file){
        return fileService.queryFile(file);
    }
}
