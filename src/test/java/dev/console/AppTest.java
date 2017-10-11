package dev.console;

import dev.exception.CalculServiceException;
import dev.service.CalculService;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.mockito.Mockito.*;

import console.App;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

public class AppTest {

	private static final Logger LOG = LoggerFactory.getLogger(App.class);

	@Rule
	public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

	@Rule
	public final TextFromStandardInputStream systemInMock = TextFromStandardInputStream.emptyStandardInputStream();
	
	private App app;
	private CalculService calculService;

	@Before
	public void setUp() throws Exception {
		this.calculService = new CalculService();
		//this.calculService = mock(CalculService.class);
		this.app = new App(new Scanner(System.in), calculService);
	}

	@Test
	public void testAfficherTitre() throws Exception {
		this.app.afficherTitre();
		String logConsole = systemOutRule.getLog();
		assertThat(logConsole).contains("**** Application Calculatrice ****");
	}

	@Test
	public void testEvaluer() throws Exception {
		LOG.info("Etant donné, un service CalculService qui retourne 35 à l'évaluation de l'expression 1+34");
		String expression = "1+34";
		when(calculService.additionner(expression)).thenReturn(35); 
		
		LOG.info("Lorsque la méthode evaluer est invoquée");
		this.app.evaluer(expression);
		
		LOG.info("Alors le service est invoqué avec l'expression {}", expression);
		verify(calculService).additionner(expression);
		
		LOG.info("Alors dans la console, s'affiche 1+34=35");
		assertThat(systemOutRule.getLog()).contains("1+34=35");
	}
	
	@Test (expected = CalculServiceException.class)
	public void testExpression() throws Exception {
		String expression = "1+c";
		try {		
			when(calculService.additionner(expression)).thenThrow(CalculServiceException.class);
		} catch (CalculServiceException e) {
			assertThat(systemOutRule.getLog()).contains("L'expression " + expression + " est invalide");
		} 
			
		LOG.info("Lorsque la méthode evaluer est invoquée");
		this.app.evaluer(expression);
		
		LOG.info("Alors le service est invoqué avec l'expression {}", expression);
		verify(calculService).additionner(expression);
		
		
	}
	
	@Test 
	public void testFin() throws Exception {

		systemInMock.provideLines("fin");
		this.app.demarrer();
		
		assertThat(systemOutRule.getLog()).contains("Aurevoir :-(");
	}
	
	@Test 
	public void testExpressionFin() throws Exception {

		systemInMock.provideLines("1+2", "fin");
		this.app.demarrer();
				
		assertThat(systemOutRule.getLog()).contains("1+2=3");
		
		assertThat(systemOutRule.getLog()).contains("Aurevoir :-(");
	}
	
	@Test 
	public void testInvalideExpressionFin() throws Exception {

		systemInMock.provideLines("AAAA", "fin");
		this.app.demarrer();
				
		assertThat(systemOutRule.getLog()).contains("L'expression AAAA est invalide");
		
		assertThat(systemOutRule.getLog()).contains("Aurevoir :-(");
	}
	
	@Test 
	public void testDoubleExpressionFin() throws Exception {

		systemInMock.provideLines("1+2", "30+2", "fin");
		this.app.demarrer();
				
		assertThat(systemOutRule.getLog()).contains("1+2=3");
		
		assertThat(systemOutRule.getLog()).contains("30+2=32");
		
		assertThat(systemOutRule.getLog()).contains("Aurevoir :-(");
	}


}