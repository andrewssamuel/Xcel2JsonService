package com.firstwicket.controller;

import com.firstwicket.excel.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.*;

import java.io.*;
import java.util.*;

/**
 * Created by andrewssamuel on 5/15/16.
 */
@RestController
public class FileUploadController {

    @RequestMapping(method = RequestMethod.POST, value = "/convertJson")
    public List convertJson(@RequestParam("file") MultipartFile file) throws IOException {

        XcelConverter xcelConverter =  null;

        if(!file.isEmpty()){
            xcelConverter = XcelConverter.newInstance();

        }

        return xcelConverter.excelParser(file.getInputStream());

    }
}
