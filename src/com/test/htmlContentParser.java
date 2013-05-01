package com.test;

import java.net.*;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class htmlContentParser {
        public static String getWebpageText(String url) throws IOException {
  
                String sourceLine;
                String content = "";

                URL address = new URL(url);

                InputStreamReader pageInput = new InputStreamReader(address.openStream());
                BufferedReader source = new BufferedReader(pageInput);

                while ((sourceLine = source.readLine()) != null)
                        content += sourceLine + "\t";

                Pattern style = Pattern.compile("<style.*?>.*?</style>");
                Matcher mstyle = style.matcher(content);
                while (mstyle.find()) content = mstyle.replaceAll("");

                Pattern script = Pattern.compile("<script.*?>.*?</script>");
                Matcher mscript = script.matcher(content);
                while (mscript.find()) content = mscript.replaceAll("");

                Pattern tag = Pattern.compile("<.*?>");
                Matcher mtag = tag.matcher(content);
                while (mtag.find()) content = mtag.replaceAll("");

                Pattern comment = Pattern.compile("<!--.*?-->");
                Matcher mcomment = comment.matcher(content);
                while (mcomment.find()) content = mcomment.replaceAll("");

                Pattern sChar = Pattern.compile("&.*?;");
                Matcher msChar = sChar.matcher(content);
                while (msChar.find()) content = msChar.replaceAll("");
                
                Pattern nLineChar = Pattern.compile("\t+");
                Matcher mnLine = nLineChar.matcher(content);
                while (mnLine.find()) content = mnLine.replaceAll("\n");
               
                return(content);
        }
}
