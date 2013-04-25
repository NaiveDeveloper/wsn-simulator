package org.jcjxb.wsn.web;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		return "home";
	}
	
	@RequestMapping(value = "/{path:.*}.jspt", method = RequestMethod.GET)
	public String jsp(@PathVariable("path") String path) {
		return path;
	}
	
	@RequestMapping(value = "/config", method = RequestMethod.GET)
	public String config() {
		return "config";
	}
	
	public Map<String, String> addSimulation() {
		return null;
	}
}
