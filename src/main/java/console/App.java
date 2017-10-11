package console;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.exception.CalculServiceException;
import dev.service.CalculService;

public class App {

	private Scanner scanner;
	private CalculService calculatrice;

	private static final Logger LOG = LoggerFactory.getLogger(App.class);

	public App(Scanner scanner, CalculService calculatrice) {
		this.scanner = scanner;
		this.calculatrice = calculatrice;
	}

	public void afficherTitre() {
		LOG.info("**** Application Calculatrice ****");
	}

	public void demarrer() {
		afficherTitre();
		String expression = afficherSaisie();
		while(!expression.equals("fin")){
			evaluer(expression);
			expression = afficherSaisie();
		}
		LOG.info("Aurevoir :-(");
	
	}

	public String afficherSaisie(){
		LOG.info("Veuillez saisir une expression");
		return scanner.next();
	}

	public void evaluer(String expression) {
		try {
			LOG.info("{}={}", expression, calculatrice.additionner(expression));
		} catch(CalculServiceException e){
			LOG.info(e.getMessage());
		}
	}
}
