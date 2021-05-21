package com.wxj.work.service;

import com.wxj.work.entity.File;
import com.wxj.work.entity.User;
import com.wxj.work.mapper.FileMapper;
import com.wxj.work.util.FileUtil;
import com.wxj.work.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class FileService {
    @Autowired
    private FileMapper fileMapper;

    public String photoUpload(@RequestParam("file") MultipartFile file, String number){
        if(file != null || !file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            String newFileName= UUID.randomUUID()+fileName;
            String path ="/uploadPhoto/";
            try {
                FileUtil.fileupload(file.getBytes(), path, newFileName);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            User userPhoto=new User();
            userPhoto.setPicture(newFileName);
            userPhoto.setNumber(Long.parseLong(number));
            int updatePhoto= fileMapper.uploadPhoto(userPhoto);
            System.out.println(updatePhoto);
        }
        return "success";
    }

    public String upload(@RequestParam("file") MultipartFile file, String workId, String workFlowId){
        if(file != null || !file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            String newFileName= UUID.randomUUID()+fileName;
            String path ="/uploadFile/";
            //ClassUtils.getDefaultClassLoader().getResource("").getPath()+"static/";
            try {
                // 该方法是对文件写入的封装，在util类中，导入该包即可使用，后面会给出方法
                FileUtil.fileupload(file.getBytes(), path, newFileName);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            File file1=new File();
            file1.setFileName(fileName);
            file1.setNewFileName(newFileName);
            file1.setPath(path+newFileName);
            file1.setWorkId(Long.parseLong(workId));
            file1.setWorkFlowId(Integer.parseInt(workFlowId));
            int updateFile= fileMapper.uploadFile(file1);
        }
        return "success";
    }

    public Result queryFile(File file){
        Result result=new Result();
        result.setSuccess(false);
        result.setDetail(null);
        try{
            List<File> fileList=fileMapper.queryFile(file);
            if(fileList!=null|!fileList.isEmpty()|!fileList.equals("")){
                result.setSuccess(true);
                result.setMsg("查询文件成功");
                result.setDetail(fileList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public Result queryPath(String newFileName){
        Result result=new Result();
        result.setSuccess(false);
        result.setDetail(null);
        try{
            File path= fileMapper.queryPath(newFileName);
            if(path!=null){
                result.setSuccess(true);
                result.setMsg("查询文件路径成功");
                result.setDetail(path);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
