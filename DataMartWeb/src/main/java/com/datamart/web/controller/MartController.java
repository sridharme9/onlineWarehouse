package com.datamart.web.controller;

import java.util.List;
import java.util.Map;

import com.datamart.web.dao.MartService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/datamart")
public class MartController {
	private MartService martService = new MartService();
	
	
	
	@RequestMapping(value ="/test", method = RequestMethod.GET)
	public String TestIt(){
		
		return "Hello ";
	}
	
	@RequestMapping(value="/createdim", method=RequestMethod.GET)
	public String CreateDim(@RequestParam Map<String, String> params){
		return martService.CreateDimension(params);
	}
	
	@RequestMapping(value="/createfact", method=RequestMethod.GET)
	public String CreateFact(@RequestParam Map<String, String> params){
		return martService.CreateDimension(params);
	}
	
	@RequestMapping(value="/insertdim", method=RequestMethod.GET)
	public String InsertDim(@RequestParam Map<String, String> params){
		return martService.InsertDim(params);
	}
	
	@RequestMapping(value="/getmarts", method=RequestMethod.GET)
	public List<Map<String, Object>> GetMarts(){
		return martService.GetMarts();
	}
	
	@RequestMapping(value="/getdims", method=RequestMethod.GET)
	public List<Map<String, Object>> GetDims(@RequestParam String mart){
		return martService.GetDims(mart);
	}
	
	@RequestMapping(value="/getfacts", method=RequestMethod.GET)
	public List<Map<String, Object>> GetFacts(@RequestParam String mart){
		return martService.GetFacts(mart);
	}
	
	@RequestMapping(value="/createmart", method=RequestMethod.GET)
	public String CreateMart(@RequestParam String mart){
		return martService.CreateMart(mart);
	}
	
	@RequestMapping(value="/getcols", method=RequestMethod.GET)
	public List<String> GetTableColumns(@RequestParam String tableName){
		return martService.GetDimColumns(tableName);
	}
	
	@RequestMapping(value="/getmartdetails", method=RequestMethod.GET)
	public Map<String, List<String>> GetMartDetails(@RequestParam String mart){
		return martService.GetMartDetails(mart);
	}
}

