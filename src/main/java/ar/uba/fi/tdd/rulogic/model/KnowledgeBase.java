package ar.uba.fi.tdd.rulogic.model;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class KnowledgeBase {
	private List<Fact> facts;
	private List<Rule> rules;
	public KnowledgeBase() {
		facts = new ArrayList<Fact>();
		rules = new ArrayList<Rule>();
		File file = new File("./src/main/resources/rules.db");
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		DataInputStream dis = null;

		try {
			fis = new FileInputStream(file);
			bis = new BufferedInputStream(fis);
			dis = new DataInputStream(bis);

			while (dis.available() != 0) {
				String line = parseLine(dis.readLine());
				if (isARule(line)) {
					rules.add(new Rule(line));
				} else {
					facts.add(new Fact(line));
				}
			}
			fis.close();
			bis.close();
			dis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean answer(String query) {
		String line = parseLine(query);
		return isRuleInDB(line) || isFactInDB(new Fact(line));
	}

	public boolean isFactInDB(Fact factToSearch) {
		boolean factFound = false;
		for (Fact fact : facts) {
			if (fact.isEqualTo(factToSearch)) {
				factFound = true;
			}
		}
		return factFound;
	}

	private boolean isARule(String line) {
		return line.indexOf(":-") > 0;
	}

	private boolean isRuleInDB(String line) {
		boolean ruleFound = false;
		for (Rule rule : rules) {
			if (rule.comply(line, this)) {
				ruleFound = true;
			}
		}
		return ruleFound;
	}

	private String parseLine(String line) {
		return line.replaceAll("\\s+","");
	}
}
