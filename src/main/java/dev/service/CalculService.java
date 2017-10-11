package dev.service;

import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.exception.CalculServiceException;

public class CalculService {

	private static final Logger LOG = LoggerFactory.getLogger(CalculService.class);
	
	int result;
	
	public int additionner(String expression) throws CalculServiceException{
		if(!expression.matches("([0-9]*[+])*[0-9]*")){
			throw new CalculServiceException("L'expression " + expression +" est invalide");
		}
		result = 0;
		
		String[] elements = expression.split("\\+");
		
		Stream.of(elements).forEach(e -> result += Integer.parseInt(e));

		return result;
		
	}

}
