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
//import java.nio.file;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import javax.servlet.http.HttpServletResponse;
//import org.apache.commons.io.IOUtils;
import com.flexbee.enc.helpers.AESCrypt;

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
	public void property(@RequestParam("fileUpload") MultipartFile fileUpload, @RequestParam("key") String key, HttpServletResponse response ) throws Exception {
		try {
			System.out.println("Enter /process");
			System.out.println(key);
		String inFileFullName = fileUpload.getOriginalFilename();
		System.out.println(inFileFullName);
		Path path = Paths.get(inFileFullName);
		String inFileName = path.getFileName().toString();
		System.out.println(inFileName);
		byte[] bytes = fileUpload.getBytes();
		//Cypher cypher = new Cypher();
		AESCrypt cypher = new AESCrypt(key);
		byte[] encryptedData = cypher.encode(bytes);
		//byte[] encBytes = EncDec.encrypt(bytes, 1);
		//byte[] decBytes = EncDec.decrypt(encBytes, 1);
		//System.out.println(encBytes.length);
	    //System.out.println(decBytes.length);
	    InputStream is = new ByteArrayInputStream(encryptedData);
	    response.setContentType("application/octet-stream");
	    String outFileName = inFileName + ".m";
        response.setHeader("Content-Disposition", "attachment; filename=" + outFileName);

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
	public void decrypt(@RequestParam("fileUpload2") MultipartFile fileUpload2, @RequestParam("key2") String key2, HttpServletResponse response ) throws Exception {
		try {
		byte[] bytes = fileUpload2.getBytes();
		AESCrypt cypher = new AESCrypt(key2);
		byte[] decBytes = cypher.decode(bytes);
		//byte[] encBytes = EncDec.encrypt(bytes, 1);
		//byte[] decBytes = EncDec.decrypt(bytes, 1);
		//System.out.println(decBytes.length);
		//System.out.println(decBytes.length);
		String inFileFullName = fileUpload2.getOriginalFilename();
		System.out.println(inFileFullName);
		Path path = Paths.get(inFileFullName);
		String inFileName = path.getFileName().toString();
		System.out.println(inFileName);
		String outFileName = inFileName.substring(0, inFileName.lastIndexOf("."));
		System.out.println(outFileName);
		
		InputStream is = new ByteArrayInputStream(decBytes);
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment; filename=" + outFileName);

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
