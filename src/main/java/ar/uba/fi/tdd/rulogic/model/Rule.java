package ar.uba.fi.tdd.rulogic.model;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class Rule {
	private String firstFact;
	private String secondFact;
	private String thirdFact;
	private String ruleName;

	public Rule(String line) {
		int startIndex = line.indexOf(":-") + 2;
		int endIndex = line.length();
		String factsString = line.substring(startIndex, endIndex);

		ruleName = getRuleName(line);

		endIndex = factsString.indexOf("),");
		firstFact = factsString.substring(0, endIndex + 1);
		factsString = factsString.substring(endIndex + 2, factsString.length());

		endIndex = factsString.indexOf("),");
		if (endIndex > 0) {
			secondFact = factsString.substring(0, endIndex + 1);
			factsString = factsString.substring(endIndex + 2, factsString.length());
			thirdFact = factsString;
		} else {
			secondFact = factsString.substring(0, factsString.length());
		}
	}

	private String getRuleName(String line) {
		int indexOfPrenthesis = line.indexOf("(");
		return line.substring(0, indexOfPrenthesis);
	}

	private String getFirstRuleParam(String line) { 
		int startIndex = line.indexOf("(") + 1;
		int endIndex = line.indexOf(",") > 0 ? line.indexOf(",") : line.indexOf(")");
		return line.substring(startIndex, endIndex);
	}

	private String getSecondRuleParam(String line) {
		int startIndex = line.indexOf(",") + 1;
		line = line.substring(startIndex);
		int endIndex = line.indexOf(",") > 0 ? line.indexOf(",") : line.indexOf(")");
		return line.substring(0, endIndex);
	}

	private String getThirdRuleParam(String line) {
		int startIndex = line.indexOf(",") + 1;
		line = line.substring(startIndex);
		startIndex = line.indexOf(",") + 1;
		line = line.substring(startIndex);
		int endIndex = line.indexOf(")");
		return line.substring(0, endIndex);
	}

	private String getFactFirstParamToReplace(String fact) {
		int startIndex = fact.indexOf("(") + 1;
		int endIndex = fact.indexOf(",") > 0 ? fact.indexOf(",") : fact.indexOf(")");
		return fact.substring(startIndex, endIndex);
	}

	private String getFactSecondParamToReplace(String fact) {
		int startIndex = fact.indexOf(",") + 1;
		int endIndex = fact.indexOf(")");
		return fact.substring(startIndex, endIndex);
	}

	public boolean comply(String line, KnowledgeBase knowledgeBase) { 
		Map<String, String> factToReplaceMap = new HashMap<String, String>();
		factToReplaceMap.put("X", getFirstRuleParam(line));
		factToReplaceMap.put("Y", getSecondRuleParam(line));
		factToReplaceMap.put("Z", getThirdRuleParam(line));
		Fact firstFactToSearch = new Fact(firstFact.replaceAll(getFactFirstParamToReplace(firstFact), factToReplaceMap.get(getFactFirstParamToReplace(firstFact))));
		Fact secondFactToSearch = new Fact(secondFact.replaceAll(getFactFirstParamToReplace(secondFact), factToReplaceMap.get(getFactFirstParamToReplace(secondFact))).replaceAll(getFactSecondParamToReplace(secondFact), factToReplaceMap.get(getFactSecondParamToReplace(secondFact))));
		Fact thirdFactToSearch;
		if (thirdFact != null) {
			thirdFactToSearch = new Fact(thirdFact.replaceAll(getFactFirstParamToReplace(thirdFact), factToReplaceMap.get(getFactFirstParamToReplace(thirdFact))).replaceAll(getFactSecondParamToReplace(thirdFact), factToReplaceMap.get(getFactSecondParamToReplace(thirdFact))));
			return ruleName.equals(getRuleName(line)) && knowledgeBase.isFactInDB(firstFactToSearch) && knowledgeBase.isFactInDB(secondFactToSearch) && knowledgeBase.isFactInDB(thirdFactToSearch);
		} else {
			return ruleName.equals(getRuleName(line)) && knowledgeBase.isFactInDB(firstFactToSearch) && knowledgeBase.isFactInDB(secondFactToSearch);
		}
	}
}