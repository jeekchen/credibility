package utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
	/**
	 * 判断是否汉字
	 * @param c char 要判断的字符
	 * @return boolean
	 */
	public static boolean isChinese(char c){
		String tmp = String.valueOf(c);
		return tmp.matches("[\u4e00-\u9fa5]+");
	}
	
	/**
	 * 判断整个字符串是否都是汉字
	 * @param str String
	 * @return boolean
	 */
	public static boolean isChinese(String str){
		return str.matches("[\u4e00-\u9fa5]+");
	}
	
	/**
	 * 判断字符串是否包含汉字
	 * @param str String
	 * @return boolean
	 */
	public static boolean containsChinese(String str){
		if(!hasText(str)){
			return false;
		}
		for(int i=0;i <str.length();i++){
			char c = str.charAt(i);
			if(isChinese(c)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 判断是否是超链接
	 * @param a String
	 * @return boolean
	 */
	public static boolean isA(String a){
		if(!hasText(a)){
			return false;
		}
		
		return a.matches("(<a\\s*href=[^>]*>)");
	}
	
	/**
	 * 判断是否URL
	 * @param url String
	 * @return boolean
	 */
	public static boolean isURL(String url){
		if(!hasText(url)){
			return false;
		}
		return url.matches("http://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?");
	}
	
	/**
	 * 判断是否Email字符串
	 * @param email String
	 * @return boolean
	 */
	public static boolean isEmail(String email){
		if(!hasText(email)){
			return false;
		}
		return email.matches("[_a-z0-9-]+(\\.[_a-z0-9-]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)*");
	}
	
	/**
	 * 获得字符的低八位的值
	 * @param c char
	 * @return int
	 */
	public static int getLowBit(char c){
		int intValue = (int)c;
		String hexValue = Integer.toHexString(intValue);
		int len = hexValue.length();
		if(len < 4){
			for(int i=len; i<4;i++){
				hexValue = "0" + hexValue;
			}
		}
		hexValue = hexValue.substring(2, 4);
		
		return Integer.parseInt(hexValue, 16);
	}
	
	/**
	 * 获得字符的高八位的值
	 * @param c char
	 * @return int
	 */
	public static int getHighBit(char c){
		int intValue = (int)c;
		String hexValue = Integer.toHexString(intValue);
		int len = hexValue.length();
		if(len < 4){
			for(int i=len; i<4;i++){
				hexValue = "0" + hexValue;
			}
		}
		hexValue = hexValue.substring(0, 2);
		
		return Integer.parseInt(hexValue, 16);
	}
	
	/**
	 * 判断是否是一个整数串
	 * @param value String
	 * @return boolean
	 */
	public static boolean isInteger(String value){
		if((value.startsWith("+"))||(value.startsWith("-"))){
			value = value.substring(1);
		}
		return isNumber(value);
	}
	
	/**
	 * 判断是否是一个Boolean类型字符
	 * @param value String
	 * @return boolean
	 */
	public static boolean isBoolean(String value){
		if((value.equalsIgnoreCase("true"))||(value.equalsIgnoreCase("false"))){
			return true;
		}
		return false;
	}
	
	/**
	 * 判断是否是一个数字串
	 * @param value
	 * @return
	 */
	public static boolean isNumber(String value){
		if(!hasText(value)){
			return false;
		}
		boolean isInt = true;
		for(int i=0;i<value.length();i++){
			char ch = value.charAt(i); 
			if(!Character.isDigit(ch)){
				isInt = false;
				break;
			}//if
		}//for
		return isInt; 
	}
	
	/**
	 * 删除文本中的HTML标记
	 * @param htmlText String HTML文本
	 * @return String
	 */
	public static String removeHtmlTag(String htmlText){
		String regex = "<.+?>";
		return htmlText.replaceAll(regex, "");
	}
	
	/**
	 * 根据正则表达式替换字符串
	 * @param regex String 正则表达式 
	 * @param input CharSequence 要匹配的字符序列
	 * @param replacement 替换字符串
	 * @param index int 替换第几个匹配的,0表示替换所有匹配的(相当于replaceAll),1表示替换第一个匹配的(相当于replaceFirst),2表示替换第二个匹配的,以此类推.
	 * @return String
	 */
	public static String replace(String regex, CharSequence input, String replacement, int index){
		Pattern p = Pattern.compile(regex);
		 Matcher m = p.matcher(input);
		 StringBuffer sb = new StringBuffer();
		 for(int i=1;m.find();i++){
			 if((index == 0) || (index == i)){
				 m.appendReplacement(sb, replacement);
			 }
		 }
		 m.appendTail(sb);
		return sb.toString();
	}
	
	/**
	 * 根据正则表达式查找第count次匹配的开始索引
	 * @param regex  正则表达式
	 * @param input CharSequence 要匹配的字符序列
	 * @param count 第几次匹配
	 * @return int
	 */
	public static int indexOf(String regex, CharSequence input, int count){
		int index = -1;
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(input);
		for(int i=1;m.find();i++){
			if(count == i){
				index = m.start();
				break;
			}
		}
		return index;
	}
	
	/**
	 * 截取两段文本中间的文本
	 * @param sourceText String 源文本
	 * @param beginText String 前段文本
	 * @param endText String 后段文本
	 * @return String[]
	 */
	public static String[] splitText(String sourceText, String beginText, String endText){
		String[] textArr = new String[3];
		
		if(beginText == null || endText == null){
			textArr[0] = "";
			textArr[1] = sourceText;
			textArr[2] = "";
			return textArr;
		}
		int preBeginIndex = sourceText.indexOf(beginText.trim());
		//前端字符串
		if (preBeginIndex < 0)
			return textArr = new String[]{"", sourceText, ""};
		else
			textArr[0] = sourceText.substring(0, preBeginIndex) + beginText;
		int preEndIndex = preBeginIndex + beginText.length();
		
		int posBeginIndex = sourceText.indexOf(endText.trim());
		//中间字符串
		if (posBeginIndex <= 0){
			textArr[1] = sourceText.substring(preEndIndex);
			textArr[2] = "";
			return textArr;
		}else
			textArr[1] = sourceText.substring(preEndIndex, posBeginIndex);
		
		int posEndIndex = posBeginIndex + endText.length();
		//后端字符串
		if(posEndIndex >= sourceText.length()){		
			textArr[2] = "";
			return textArr;
		}else{
			textArr[2] = endText + sourceText.substring(posEndIndex);
		}
		return textArr;
	}
	
	public static void matcher(String regex, CharSequence input){
		 Pattern p = Pattern.compile(regex);
		 Matcher m = p.matcher(input);
		 while (m.find()) {
			 System.out.println("[" + m.start() + "," + m.end() + ") " + m.group());
		 }
	}
	
	public static int matchCount(String regex, CharSequence input){
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(input);
		int count = 0;
		while (m.find()) {
			count ++ ;
		}
		return count;
	}
	
	public static boolean like(String inputText, String likeText) {
		StringBuilder regex = new StringBuilder();
		regex.append(".*");
		for(int i=0; i<likeText.length(); i++){
			char c = likeText.charAt(i);
			regex.append("[");
			regex.append(Character.toLowerCase(c));
			regex.append(Character.toUpperCase(c));
			regex.append("]");
		}
		regex.append(".*");
		return Pattern.matches(regex.toString(), inputText);
	}
	public static boolean likeSentence(String inputText, String likeText){
		return likeSentence(inputText, likeText, ';', ' ');
	}
	public static boolean likeSentence(String inputText, String likeText, char sentenceSeparator, char wordSeparator){
		String[] sentenceArr = inputText.split(sentenceSeparator + "");
		for(String sentence : sentenceArr){
			if(startsWithIgnoreCase(sentence, likeText)){
				return true;
			}
			String[] wordArr = sentence.split(wordSeparator + "");
			if(wordArr.length < likeText.length()){
				continue;
			}
			boolean wordMatch = true;
			for(int i=0; i<likeText.length(); i++){
				if(!startsWithIgnoreCase(wordArr[i], likeText.charAt(i) + "")){
					wordMatch = false;
					break;
				}
			}
			if(wordMatch){
				return true;
			}
		}
		
		return false;
	}
	public static boolean likeSentence2(String inputText, String likeText){
		return likeSentence2(inputText, likeText, ';', ' ');
	}
	public static boolean likeSentence2(String inputText, String likeText, char sentenceSeparator, char wordSeparator){
		String[] sentenceArr = inputText.split(sentenceSeparator + "");
		
		String[] wordArr = sentenceArr[0].split(wordSeparator + "");
		for(String word : wordArr){
			if(startsWithIgnoreCase(word, likeText)){
				return true;
			}
		}
		if(sentenceArr.length < likeText.length()){
			return false;
		}
		for(int i=0; i<likeText.length(); i++){
			String[] wordArr2 = sentenceArr[i].split(wordSeparator + "");
			boolean wordMatch = false;
			for(String word : wordArr2){
				if(startsWithIgnoreCase(word, likeText.charAt(i)+"")){
					wordMatch = true;
					break;
				}
			}
			if(!wordMatch){
				return false;
			}
		}
		
		return true;
	}
	public static boolean startsWithIgnoreCase(String inputText, String prefix){
		StringBuilder regex = new StringBuilder();
		for(int i=0; i<prefix.length(); i++){
			char c = prefix.charAt(i);
			regex.append("[");
			regex.append(Character.toLowerCase(c));
			regex.append(Character.toUpperCase(c));
			regex.append("]");
		}
		regex.append(".*");
		return Pattern.matches(regex.toString(), inputText);
	}
	public static boolean endsWithIgnoreCase(String inputText, String suffix){
		StringBuilder regex = new StringBuilder();
		regex.append(".*");
		for(int i=0; i<suffix.length(); i++){
			char c = suffix.charAt(i);
			regex.append("[");
			regex.append(Character.toLowerCase(c));
			regex.append(Character.toUpperCase(c));
			regex.append("]");
		}
		return Pattern.matches(regex.toString(), inputText);
	}
	
	/**
	 * 当 text 不为 null 且长度不为 0
	 * @param text String
	 * @return boolean
	 */
	public static boolean hasLength(String text) {
		return (text != null) && (text.length() > 0);
	}
	
	/**
	 * text 不能为 null 且必须至少包含一个非空格的字符
	 * @param text String
	 * @return boolean
	 */
	public static boolean hasText(String text) {
		return hasLength(text) && Pattern.matches(".*\\S.*", text);
	}
	
	/**
	 * 根据URL截取不带后缀的文件名
	 * @param url String
	 * @return String
	 */
	public static String getPageName(String url){
		int fullNameEndIndex = url.indexOf("?");
		if(fullNameEndIndex == -1){
			fullNameEndIndex = url.length();
		}
		String temp = url.substring(0, fullNameEndIndex);
		String fullFileName = temp.substring(temp.lastIndexOf("/")+1);
		String pageName = "";
		int fileNameEndIndex = fullFileName.lastIndexOf(".");
		if(fileNameEndIndex == -1){
			fileNameEndIndex = fullFileName.length();
		}
		pageName = fullFileName.substring(0, fileNameEndIndex);
		return pageName;
	}
	
	public static String toString(String[] arr, String separator){
		String result = "";
		for(String el : arr){
			result += el + separator;
		}
		if(result.endsWith(separator)){
			result = result.substring(0, result.length() - separator.length());
		}
		return result;
	}
	
	public static StringBuilder compareAndDeleteLastChar(StringBuilder sb, char c){
		if((sb.length() > 0) && (sb.charAt(sb.length() - 1) == c)){
			sb = sb.deleteCharAt(sb.length() - 1);
		}
		return sb;
	}
	public static StringBuffer compareAndDeleteLastChar(StringBuffer sb, char c){
		if((sb.length() > 0) && (sb.charAt(sb.length() - 1) == c)){
			sb = sb.deleteCharAt(sb.length() - 1);
		}
		return sb;
	}
	
	public static StringBuilder compareAndDeleteFirstChar(StringBuilder sb, char c){
		if((sb.length() > 0) && (sb.charAt(0) == c)){
			sb = sb.deleteCharAt(0);
		}
		return sb;
	}
	public static StringBuffer compareAndDeleteFirstChar(StringBuffer sb, char c){
		if((sb.length() > 0) && (sb.charAt(0) == c)){
			sb = sb.deleteCharAt(0);
		}
		return sb;
	}

	public static String lineSeparator(){
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		pw.println();
		pw.close();
		return sw.toString();
	}
	
	/**
	 * 如果str为null或"",就将str转化为define的值。函数将截取首尾空格后再判断。
	 * @param str 原字符串
	 * @param define str为null或""时的值
	 * @return 成功返回define，失败返回str。
	 * @throws 无。
	 */
	public static String nvl(String str, String define) {
		if (str == null || str.trim().equals("")) {
			return define;
		}
		return str.trim();
	}

	/**
	 * 如果str为null,就将str转化为""。函数将截取首尾空格后再判断。
	 * @param str 原字符串
	 * @return 成功返回"",失败返回原值。
	 * @throws 无
	 */
	public static String nvl(String str) {
		if (str == null || str.trim().equals("")) {
			return "";
		}
		return str.trim();
	}
	
	/**
	 * 从一个字符串中提取出一个子串。
	 * @param source 原字符串
	 * @param prefix 前缀
	 * @param postfix 后缀
	 * @return 返回前缀和后缀之间的字符串。若提取的字符串不存在，返回空。
	 * @throws 无
	 */
	public static String extractStr(String source, String prefix, String postfix){
		int index = source.indexOf(prefix);
		if (index<0)
			return "";
		String out = source.substring(index+prefix.length());	
		
		if (!nvl(postfix,"").equals("")){
			index = out.indexOf(postfix);
			if (index>=0)
				out = out.substring(0, index);	
		}
		return out.trim();
	}
	
	/**首字母转为大写
	 * @param str
	 * @return
	 */
	public static String upcaseFirst(String str){
		StringBuilder sb = new StringBuilder();
		char[] strChars = str.toCharArray();
		for (int i=0; i<strChars.length; i++){
			char c = str.charAt(i);			
			if (i==0 && c>=97 && c<=122){
				c -= 32;
			}
			sb.append(c);
		}
		return sb.toString();
	}
	
	/**Ojbect转为sql语句中适用的字符串，自动类型转换和加入带单引号
	 * @param obj
	 * @return
	 */
	public static String obj2sqlField(Object obj){
		if (obj==null)
			return String.format("%s", "null");
		if (obj instanceof java.lang.String){
			return String.format("'%s'", (String)obj);
		}else if (obj instanceof java.lang.Long){
			return String.format("%s", Long.toString((Long)obj));
		}else if (obj instanceof java.lang.Integer){
			return String.format("%s", Integer.toString((Integer)obj));
		}else if (obj instanceof java.util.Date){
			SimpleDateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	        return String.format("'%s'", dateformat.format((Date)obj));
		}else{
			return String.format("'%s'", (String)obj);
		}
	}
	
	/**sql特殊字符转义
	 * @param str
	 * @return 转义后的字符
	 */
	public static String sqlEscape(String str){		
		StringBuilder sb = new StringBuilder();
		int len = str.toCharArray().length;
		for (int i=0; i<len; i++){
			char c = str.charAt(i);
			switch (c){
			/*case '"':
				sb.append('"');
				break;*/
			case '\\':
				sb.append('\\');
				break;
			case '\'':
				sb.append('\\');
				break;
			default:				
				break;
			}
			sb.append(c);
		}
		return sb.toString();
		/*str = replaceAll(str, "\"", "\"\"");
		str = replaceAll(str, "\\","\\\\");
		str = replaceAll(str, "'", "\\'");
		
		return str;*/
	}	
	
	/**
	 * 全部替换
	 * @param source
	 * @param oldStr
	 * @param newStr
	 * @return
	 */
	public static  String replaceAll(String source, String oldStr, String newStr){       
		int index = source.indexOf(oldStr);
		int offset = newStr.length()-oldStr.length();
		while (index>-1){
			source = source.replace(oldStr, newStr);
			index = source.indexOf(oldStr, index+offset+1);
		}
		return source;
    }
	
	public static Boolean isNullOrEmpty(String str){
		return nvl(str).equals("");
	}
	
	/**
	 * 驼峰命名转为下划线命名，即遇到大写字母在前面插入下划线，然后将大写转成小写。
	 * @param str
	 * @return
	 */
	public static String camelCase2UnderLine(String str){
		str = nvl(str);
		StringBuilder sb = new StringBuilder();
		int len = str.toCharArray().length;
		for (int i=0; i<len; i++){
			char c = str.charAt(i);
			if (c>=65 && c<=90){
				if (i>0)
					sb.append('_');
				sb.append((char)(c+32));
			}else{
				sb.append(c);
			}
		}
		return sb.toString();
	}
	
	/**
	 * 判断是否合法的手机号码.
	 * 手机号码为11位数字。
	 * 国家号码段分配如下：
　　   * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
　　   * 联通：130、131、132、152、155、156、185、186
　　   * 电信：133、153、180、189、（1349卫通）
	 * @param mobiles 手机号码
	 * @return boolean
	 */
	public static boolean isMobileNO(String mobiles){
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}
	public static boolean isMobileNO2(String mobiles){
		String regExp = "^[1]([3][0-9]{1}|59|58|88|89)[0-9]{8}$";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(mobiles);
		return m.find();
	}
	
	public static String firstLetterToUpperCase(String s){
		String m = Character.toUpperCase(s.charAt(0)) + "";
		if(s.length() > 1){
			m += s.substring(1);
		}
		
		return m;
	}
	
	public static void main(String args[]){
//		String s1 = "http://www.baidu.com/index?url=http://www.baidu.com";
//		String s2 = "yuancihang@tom.com_";
//		System.out.println(StringUtil.isURL(s1));
//		System.out.println(StringUtil.isEmail(s2));
		/*System.out.println(MessageFormat.format("{0}2222{1}----", 10.1133f,"eee"));
		System.out.println(NumberFormat.getPercentInstance().format(0.123));
		System.out.println(NumberFormat.getCurrencyInstance().format(0.123));
		System.out.println(String.format("qq%.2frr", 0.12345));*/
		//System.out.println(StringUtil.upcaseFirst("z你好Abcde"));
		/*String str = "xxxx111111bbb111ccc";
		str = replaceAll(str, "111","222");
		System.out.println(str);*/
		/*String str = "IsABadApple_AA";
		str = camelCase2UnderLine(str);
		System.out.println(str);*/
		String str = "\"\"";
		str = sqlEscape(str);
		System.out.println(isMobileNO("15811368342"));
		System.out.println(isMobileNO("13933333333"));
	}
}
