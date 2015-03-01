package com.sa.common.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.rest.sa.dao.BaseDAO;
import com.sa.common.model.Employee;
import com.sa.common.model.WeatherData;

@Controller
@RequestMapping("/movie")
public class MovieController {
	
@Autowired
private BaseDAO baseDAO;


	@RequestMapping(value="/{name}", method = RequestMethod.GET)
	public String getMovie(@PathVariable String name, ModelMap model) {
		model.addAttribute("movie", name);
		return "list";

	}

	@RequestMapping(value = "/json/{firstParam}/{secondParam}", method = RequestMethod.GET)
	public @ResponseBody Employee getShopInJSON(@PathVariable String firstParam, @PathVariable String secondParam) {
		Employee employee = new Employee();
		employee.setFirstName(firstParam);
		employee.setLastName(secondParam);
		return employee;
 
	}

	@RequestMapping(value = "/weather/{firstParam}/{secondParam}", method = RequestMethod.GET)
	public @ResponseBody WeatherData CallingWeatherApi(@PathVariable String firstParam, @PathVariable String secondParam) {
 	
		RestTemplate restTemplate = new RestTemplate();
        WeatherData data = restTemplate.getForObject("http://api.openweathermap.org/data/2.5/weather?q="+firstParam+","+secondParam, WeatherData.class);
        System.out.println("Name:    " + data.getName());
        System.out.println("About:   " + data.getId());
        System.out.println("Phone:   " + data.getCod());
        System.out.println("Temp:   " + data.getMain().getTemp());
        return data;
 
	}
	@RequestMapping(value = "/accessWeather", method = RequestMethod.GET)
	public @ResponseBody WeatherData accessWeatherApi(HttpServletRequest request) {
 	    String country = request.getParameter("country");
 	    String city = request.getParameter("city");
 	    System.out.println("city:"+city+"country:"+country);
 	   
 	   //MongoOperations mongoOperation = (MongoOperations) ApplicationContextUtils.getApplicationContext().getBean("mongoTemplate");
 	  
 		
 	 
		RestTemplate restTemplate = new RestTemplate();
        WeatherData data = restTemplate.getForObject("http://api.openweathermap.org/data/2.5/weather?q="+city+","+country, WeatherData.class);
        
        baseDAO.saveCollection(data);
        System.out.println("Name:    " + data.getName());
        System.out.println("About:   " + data.getId());
        System.out.println("Phone:   " + data.getCod());
        System.out.println("Temp:   " + data.getMain().getTemp());
        return data;
 
	}

	public BaseDAO getBaseDAO() {
		return baseDAO;
	}

	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}
}