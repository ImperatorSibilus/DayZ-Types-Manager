import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ItemClass {
	
	private String name;
	public int nominal;
	public int lifetime;
	public int restock;
	public int min;
	public int quantmin;
	public int quantmax;
	public int cost;
	public boolean[] flags;
	public String category;
	public String tag;
	public List<String> usage = new ArrayList<String>();
	public List<String> value = new ArrayList<String>();
	
	public ItemClass(String code) {
		
		Pattern pattern = Pattern.compile("[^\\n]*<type name=\"(\\S*)\">[\\s\\S]*?\\s*<\\/type>");
		Matcher matcher = pattern.matcher(code);
		if (matcher.find()) {
			name = code.substring(matcher.start(1), matcher.end(1));
		}
		
		pattern = Pattern.compile("[^\\n]*<nominal>([0-9]+)<\\/nominal>");
		matcher = pattern.matcher(code);
		if (matcher.find()) {
			nominal = Integer.valueOf(code.substring(matcher.start(1), matcher.end(1)));
		} else {
			nominal = -2;
		}
		
		pattern = Pattern.compile("[^\\n]*<lifetime>([0-9]+)<\\/lifetime>");
		matcher = pattern.matcher(code);
		if (matcher.find()) {
			lifetime = Integer.valueOf(code.substring(matcher.start(1), matcher.end(1)));
		} else {
			lifetime = -2;
		}
		
		pattern = Pattern.compile("[^\\n]*<restock>([0-9]+)<\\/restock>");
		matcher = pattern.matcher(code);
		if (matcher.find()) {
			restock = Integer.valueOf(code.substring(matcher.start(1), matcher.end(1)));
		} else {
			restock = -2;
		}
		
		pattern = Pattern.compile("[^\\n]*<min>([0-9]+)<\\/min>");
		matcher = pattern.matcher(code);
		if (matcher.find()) {
			min = Integer.valueOf(code.substring(matcher.start(1), matcher.end(1)));
		} else {
			min = -2;
		}
		
		pattern = Pattern.compile("[^\\n]*<quantmin>(-?[0-9]+)<\\/quantmin>");
		matcher = pattern.matcher(code);
		if (matcher.find()) {
			quantmin = Integer.valueOf(code.substring(matcher.start(1), matcher.end(1)));
		} else {
			quantmin = -2;
		}
		
		pattern = Pattern.compile("[^\\n]*<quantmax>(-?[0-9]+)<\\/quantmax>");
		matcher = pattern.matcher(code);
		if (matcher.find()) {
			quantmax = Integer.valueOf(code.substring(matcher.start(1), matcher.end(1)));
		} else {
			quantmax = -2;
		}
		
		pattern = Pattern.compile("[^\\n]*<cost>([0-9]+)<\\/cost>");
		matcher = pattern.matcher(code);
		if (matcher.find()) {
			cost = Integer.valueOf(code.substring(matcher.start(1), matcher.end(1)));
		} else {
			cost = -2;
		}
		
		pattern = Pattern.compile("[^\\n]*<flags count_in_cargo=\"([0|1])\" count_in_hoarder=\"([0|1])\" count_in_map=\"([0|1])\" count_in_player=\"([0|1])\" crafted=\"([0|1])\" deloot=\"([0|1])\"\\/>");
		matcher = pattern.matcher(code);
		if (matcher.find()) {
			flags = new boolean[6];
			for (int i = 0; i<flags.length; i++) {
				int flag;
				flag = Integer.parseInt(code.substring(matcher.start(i+1), matcher.end(i+1)));
				flags[i] = flag == 1;
			}
		} else {
			flags = null;
		}
		
		pattern = Pattern.compile("[^\\n]*<category name=\"([a-zA-Z]+)\"\\/>");
		matcher = pattern.matcher(code);
		if (matcher.find()) {
			category = code.substring(matcher.start(1), matcher.end(1));
		} else {
			category = null;
		}
		
		pattern = Pattern.compile("[^\\n]*<tag name=\"([a-zA-Z]+)\"\\/>");
		matcher = pattern.matcher(code);
		if (matcher.find()) {
			tag = code.substring(matcher.start(1), matcher.end(1));
		} else {
			tag = null;
		}
		
		pattern = Pattern.compile("[^\\n]*<usage name=\"([a-zA-Z]+)\"\\/>");
		matcher = pattern.matcher(code);
		while (matcher.find()) {
			usage.add(code.substring(matcher.start(1), matcher.end(1)));
		}
		
		pattern = Pattern.compile("[^\\n]*<value name=\"([a-zA-Z0-9]+)\"\\/>");
		matcher = pattern.matcher(code);
		while (matcher.find()) {
			value.add(code.substring(matcher.start(1), matcher.end(1)));
		}
		
	}
	
	public String getName() {
		return name;
	}
	
	public String exportAsXML() {
		
		String returnString = "\t<type name=\"" + name + "\">\n";
		if (nominal != -2) returnString += "\t\t<nominal>" + nominal + "</nominal>\n";
		if (lifetime != -2) returnString += "\t\t<lifetime>"+ lifetime + "</lifetime>\n";
		if (restock != -2) returnString += "\t\t<restock>" + restock + "</restock>\n";
		if (restock != -2) returnString += "\t\t<min>" + min + "</min>\n";
		if (quantmin != -2) returnString += "\t\t<quantmin>" + quantmin + "</quantmin>\n";
		if (quantmax != -2) returnString += "\t\t<quantmax>" + quantmax + "</quantmax>\n";
		if (cost != -2) returnString += "\t\t<cost>" + cost + "</cost>\n";
		
		String textFlags[] = new String[6];
		if (flags != null) {
			for (int i = 0; i<flags.length; i++) {
				textFlags[i] = flags[i] ? "1":"0";
			}
			returnString += "\t\t<flags count_in_cargo=\"" + textFlags[0] + "\" count_in_hoarder=\"" + textFlags[1] + "\" count_in_map=\"" + textFlags[2] + "\" count_in_player=\"" + textFlags[3] + "\" crafted=\"" + textFlags[4] + "\" deloot=\"" + textFlags[5] + "\"/>\n";
		}
		
		if (category != null) returnString += "\t\t<category name=\"" + category + "\"/>\n";
		if (tag != null) returnString += "\t\t<tag name=\"" + tag + "\"/>\n";
		
		for (String singleUsage: usage) returnString += "\t\t<usage name=\"" + singleUsage + "\"/>\n";
		for (String singleValue: value) returnString += "\t\t<value name=\"" + singleValue + "\"/>\n";
		
		returnString += "\t</type>\n";
		return returnString;
	}
	
	public Object[] exportContentsForTable() {
		
		Object[] returnObjects = new Object[19];
		
		returnObjects[Column.NAME] = name;
		if (nominal != -2) returnObjects[Column.NOMINAL] = nominal;
		if (lifetime != -2) returnObjects[Column.LIFETIME] = lifetime;
		if (restock != -2) returnObjects[Column.RESTOCK] = restock;
		if (min != -2) returnObjects[Column.MIN] = min;
		if (quantmin != -2) returnObjects[Column.QUANTMIN] = quantmin;
		if (quantmax != -2) returnObjects[Column.QUANTMAX] = quantmax;
		if (cost != -2) returnObjects[Column.COST] = cost;
		
		if (flags != null) {
			for (int i = 0; i<flags.length; i++) {
				returnObjects[Column.COUNTINCARGO+i] = flags[i];
			}
		} else {
			for (int i = 0; i<6; i++) {
				returnObjects[Column.COUNTINCARGO+i] = false;
			}
		}
		
		returnObjects[Column.CATEGORY] = category;
		returnObjects[Column.TAG] = tag;
		
		if (usage.size() > 0) {
			String usageString = usage.get(0);
			for (int i = 1; i<usage.size(); i++) {
				String singleUsage = usage.get(i);
				if (singleUsage != null)
				{
					usageString = usageString + ", " + singleUsage;
				}
			}
			returnObjects[Column.USAGE] = usageString;
		}
		
		if (value.size() > 0) {
			String valueString = value.get(0);
			for (int i = 1; i<value.size(); i++) {
				String singleValue = value.get(i);
				if (singleValue != null)
				{
					valueString = valueString + ", " + singleValue;
				}
			}
			returnObjects[Column.VALUE] = valueString;
		}
		
		return returnObjects;
		
	}

}
