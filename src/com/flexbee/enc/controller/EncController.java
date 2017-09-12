package com.flexbee.enc.controller;

import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.multipart.*;

import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import javax.servlet.http.HttpServletResponse;
//import org.apache.commons.io.IOUtils;


@Controller
public class EncController {
	@RequestMapping("/upload")
    public ModelAndView upload() {
        String message = "HELLO SPRING MVC HOW R U";
        System.out.println("Hello World!");
        return new ModelAndView("upload", "message", message);
    }

	@RequestMapping(value="/encry", method=RequestMethod.GET)
	public @ResponseBody String encry(@RequestParam("s") String s) {
		//int a = 1;
		System.out.println(s);
		return s;
	}

	@RequestMapping(value="/process", method = RequestMethod.POST)
	//@ResponseBody
	public void property(@RequestParam("fileUpload") MultipartFile fileUpload, HttpServletResponse response ) {
		try {
			System.out.println("Enter /process");
		byte[] bytes = fileUpload.getBytes();
		//byte[] encBytes = EncDec.encrypt(bytes, 1);
		//byte[] decBytes = EncDec.decrypt(encBytes, 1);
		//System.out.println(encBytes.length);
	    //System.out.println(decBytes.length);
	    InputStream is = new ByteArrayInputStream(bytes);
	    response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=\"test.txt\"");

	    //System.out.println(fileDto.getPassword());
	    org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
	    response.flushBuffer();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	    //return "Hello";
	}

	@RequestMapping(value="/decrypt", method = RequestMethod.POST)
		//@ResponseBody
	public void decrypt(@RequestParam("fileUpload2") MultipartFile fileUpload2, HttpServletResponse response ) {
		try {
		byte[] bytes = fileUpload2.getBytes();
		//byte[] encBytes = EncDec.encrypt(bytes, 1);
		//byte[] decBytes = EncDec.decrypt(bytes, 1);
		//System.out.println(decBytes.length);
		//System.out.println(decBytes.length);
		InputStream is = new ByteArrayInputStream(bytes);
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment; filename=\"test.txt\"");

		//System.out.println(fileDto.getPassword());
		org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
		response.flushBuffer();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		//return "Hello";
	}
}
