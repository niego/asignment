package com.scrapper.asignment.controller;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scrapper.asignment.service.ScraperService;



@CrossOrigin
@RestController
@RequestMapping("/scraper")
public class ScrapperController {
	
	@Autowired
	ScraperService scraperService;
	
	@RequestMapping("/getTokped")
	@GetMapping("/http-servlet-response")
	public void getAll(HttpServletResponse response) throws InterruptedException, IOException {
		response.setContentType("text/csv");
	    response.setHeader("Content-Disposition", "attachment; filename=\"tokped_mobilephone_top100.csv\"");
	    exportCsv(response, scraperService.scrape());
	}
	
	public void exportCsv(HttpServletResponse response, String input) throws IOException {
		OutputStream csvWriter = response.getOutputStream();
		csvWriter.write(input.getBytes());
		csvWriter.flush();
		csvWriter.close();
	}

}
