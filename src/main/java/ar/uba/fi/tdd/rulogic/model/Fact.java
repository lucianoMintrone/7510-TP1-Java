package ar.uba.fi.tdd.rulogic.model;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class Fact {
	public String factName;
	public String firstFactParam;
	public String secondFactParam;

	public Fact(String line) {
		int indexOfPrenthesis = line.indexOf("(");
		factName = line.substring(0, indexOfPrenthesis);
		int firstFactParamStartIndex = line.indexOf("(") + 1;
		int firstFactParamEndIndex = line.indexOf(",") > 0 ? line.indexOf(",") : line.indexOf(")");
		firstFactParam = line.substring(firstFactParamStartIndex, firstFactParamEndIndex);
		int secondFactParamStartIndex = line.indexOf(",") + 1;
		int secondFactParamEndIndex = line.indexOf(")");
		secondFactParam = secondFactParamStartIndex > 0 ? line.substring(secondFactParamStartIndex, secondFactParamEndIndex) : null;
	}

	public boolean isEqualTo(Fact fact) {
		return factName.equals(fact.factName) && firstFactParam.equals(fact.firstFactParam) && ((secondFactParam == null && fact.secondFactParam == null) || secondFactParam.equals(fact.secondFactParam));
	}
}