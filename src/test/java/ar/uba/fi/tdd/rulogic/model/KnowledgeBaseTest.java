package ar.uba.fi.tdd.rulogic.model;

import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

public class KnowledgeBaseTest {
	@InjectMocks
	private KnowledgeBase knowledgeBase;

	@Before
	public void setUp() throws Exception {
		initMocks(this);
	}

	@Test
	public void testFactIsNotInDB() {
		Assert.assertFalse(this.knowledgeBase.answer("varon (javier)."));
	}

	public void testFactIsInDB() {
		Assert.assertTrue(this.knowledgeBase.answer("varon(juan)."));
	}

	public void testDaughterRuleIsNotInDB() {
		Assert.assertFalse(this.knowledgeBase.answer("hija(juan, pepe)."));
	}

	public void testDaughterRuleIsInDB() {
		Assert.assertTrue(this.knowledgeBase.answer("hijo(pepe, juan)."));
	}

	public void testUncleRuleIsNotInDB() {
		Assert.assertFalse(this.knowledgeBase.answer("tio(maria, pepe, juan)."));
	}

	public void testUncleRuleIsInDB() {
		Assert.assertTrue(this.knowledgeBase.answer("tio(nicolas, alejandro, roberto)."));
	}
}
