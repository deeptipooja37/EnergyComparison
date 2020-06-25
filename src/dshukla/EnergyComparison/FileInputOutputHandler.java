package dshukla.EnergyComparison;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class FileInputOutputHandler {
	
	/**
	 *  checks the input parameters for empty/null values
	 * */
	public void isEmpty(String[] args) throws ParamNotFoundException {
		if (args ==null || args.length==0 || args[0]==null || args[0]=="") 
			throw new ParamNotFoundException();
	}
	
	/**
	 * 	Function for output stream
	 * 
	 */
	public void writeResultObj(boolean isValid) {
		System.out.println(isValid ? "The file is a valid FizzBuzz File" :"The file is not a valid FizzBuzz File");
	}
	
	/**
	 * 	 Function to read json file
	 * 
	 * */
	public Object readJSONFile (String[] args) {
		JSONParser jsonParser = new JSONParser();
        
			/*
			 * 		Throws Custom Exception ParamNotFoundException
			 * */
			try {
				isEmpty(args);
			} catch (ParamNotFoundException e1) {
				e1.printStackTrace();
			}
			
	        try 
	        {
	        	Path p = Paths.get(args[0]);
	        	byte[] bytes = Files.readAllBytes(p);
				String content = new String(bytes);
	            Object  obj = jsonParser.parse(content);
	            if (obj instanceof JSONArray) {
	        	  return obj;
				}
	            	 
	        } catch (FileNotFoundException e) {
	        	System.out.println("No file found at specified location");
	            e.printStackTrace();
	        } catch (IOException e) {
	        	System.out.println("Please provide valid file location");
	           e.printStackTrace();
	        } catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		return null;
	}



	

}
