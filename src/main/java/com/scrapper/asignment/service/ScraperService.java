package com.scrapper.asignment.service;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ScraperService {
	ChromeDriver driver;
	
	@PostConstruct
	void postConstruct() {
		System.setProperty("webdriver.chrome.driver", "src/main/resources/driver/chromedriver.exe");
	}
	
	public String scrape() throws InterruptedException, IOException {
		String URL =  "https://www.tokopedia.com/p/handphone-tablet/handphone";
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get(URL);
		JavascriptExecutor je = (JavascriptExecutor) driver;
		je.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		String csv = "Name of Product, Description, Image Link, Price, Rating(out of 5 stars)";
		
		Thread.sleep(1000);
		WebElement elem = driver.findElementByClassName("css-13l3l78");
		List<WebElement> elemlist = elem.findElements(By.className("css-bk6tzz"));
		int i = 0;
		for(WebElement we : elemlist) {
			csv += "\n"+we.findElement(By.className("css-1bjwylw")).getText()+",";
			csv += " "+we.findElement(By.className("css-vbihp9")).getText().replaceAll("\\r\\n|\\r|\\n", " ")+",";
			csv += " "+we.findElement(By.tagName("img")).getAttribute("src")+",";
			csv += " "+we.findElement(By.className("css-o5uqvq")).getText()+",";
			if(we.findElements(By.className("css-153qjw7")).size()>0) {
				WebElement rating = we.findElement(By.className("css-153qjw7"));
				String rtinner = rating.getAttribute("innerHTML");
				csv += "Star " + (countWords(rtinner, "4fede911.svg")-countWords(rtinner, "cd445e3b.svg")) + " of " + rating.getText();
			}
			i++;
		}
		driver.quit();
		
		if(i != 100) {
			int difference = 100-i;
			driver = new ChromeDriver();
			String URLN =  "https://www.tokopedia.com/p/handphone-tablet/handphone?page=2";
			driver.manage().window().maximize();
			driver.get(URLN);
			je = (JavascriptExecutor) driver;
			je.executeScript("window.scrollTo(0, document.body.scrollHeight)");
			
			Thread.sleep(1000);
			WebElement elemn = driver.findElementByClassName("css-13l3l78");
			List<WebElement> elemlistn = elemn.findElements(By.className("css-bk6tzz"));
			int j = 0;
			for(WebElement we : elemlistn) {
				if(j < difference) {
					csv += "\n"+we.findElement(By.className("css-1bjwylw")).getText()+",";
					csv += " "+we.findElement(By.tagName("img")).getAttribute("src")+",";
					csv += " "+we.findElement(By.className("css-vbihp9")).getText()+",";
					csv += " "+we.findElement(By.className("css-o5uqvq")).getText()+",";
					if(we.findElements(By.className("css-153qjw7")).size()>0) {
						WebElement rating = we.findElement(By.className("css-153qjw7"));
						String rtinner = rating.getAttribute("innerHTML");
						csv += "Star " + (countWords(rtinner, "4fede911.svg")-countWords(rtinner, "cd445e3b.svg")) + " of " + rating.getText();
					}
				}
				j++;
			}
			driver.quit();
		}
		return csv;
	}
	
	public static int countWords(String in, String pattern){
		int i = 0;
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher( in );
		while (m.find()) {
		    i++;
		}
		return i;
	}
}
