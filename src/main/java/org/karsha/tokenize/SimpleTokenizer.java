package org.karsha.tokenize;

/**
 * Copyright (C) 2012, Lanka Software Foundation.
 *
 * Date Author Changes 
 * 2010 Shanchan Wu Created (Lucene 2.9 compatible)
 * June 1 2012 Kasun Perera Modified the program as compatible to Lucene 3.5
 *
 */

import java.io.File;
import java.io.Reader;
import java.io.StringReader;

import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tokenattributes.TermAttribute;
import org.apache.lucene.util.Version;

/**
 * SimpleTokenizer filters punctuation only
 */
public class SimpleTokenizer implements Tokenizer {

    private static boolean replaceInvalidAcronym = false;
    private static int maxTokenLength = 255;
    // currently not used
    @SuppressWarnings("unused")
    private static final String[] LUCENE_STOP_WORDS = {"a", "an", "and", "are", "as", "at", "be",
        "but", "by", "for", "if", "in", "into", "is", "it", "no", "not", "of", "on", "or",
        "such", "that", "the", "their", "then", "there", "these", "they", "this", "to", "was",
        "will", "with"};

    public SimpleTokenizer() {
    }

    public TokenStream tokenStream(String text) {
        return tokenStream(new StringReader(text));
    }

    public TokenStream tokenStream(Reader reader) {
        //StandardTokenizer tokenStream = new StandardTokenizer(reader, replaceInvalidAcronym);
        StandardTokenizer tokenStream = new StandardTokenizer(Version.LUCENE_35, reader);
        tokenStream.setMaxTokenLength(maxTokenLength);
        TokenStream result = new StandardFilter(tokenStream);

        result = new LowerCaseFilter(result);
        //result = new StopFilter(result, TERRIER_STOP_WORDS);
        //result = new PorterStemFilter(result);

        return result;
    }

    public String processText(String text) {
        StringBuffer str = new StringBuffer();
        TokenStream stream = tokenStream(new StringReader(text));
        Token token = new Token();

        try {
            
              while(stream.incrementToken()) {
                str.append(stream.getAttribute(TermAttribute.class).term());
                str.append(" ");

            }
//            while ((token = stream.next(token)) != null) {
//                str.append(token.termBuffer(), 0, token.termLength());
//                str.append(" ");
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //return str.toString().replace('-', ' ').trim();

        return str.toString();
    }

    public static void main(String[] args) {
        //String a = "This a big man. He is here:` to ' see? me \"hehe+\" ";
        String a = "[0013] [T=/p2] -- - a [/p1] Transfer with M2V5Engine incomplete : face No /p4 not created /p3@@@@[0013] [T=/p2] [/p1] 浣跨敤 M2V5 寮曟�?�绉";
        String x = "dd3] [T=/p2] -- - a [/p1] Trvvsfer with M2V5Engine incomplete : face No /p4 not created /p3@@@@[0013] [T=/p2] [/p1] 浣跨敤 M2V5 寮曟�?�绉";

        Tokenizer t = new SimpleTokenizer();
        String b = t.processText(a);
        System.out.println(b);

        System.out.println(t.processText(x));

        //================

        String tempFileName = "data/temp.txt";
        File tempFile = new File(tempFileName);
        System.out.println();
        if (tempFile.exists()) {
            System.out.println(true);
            tempFile.delete();
        }

        if (tempFile.exists()) {
            System.out.println("2222Here again");
        } else {
            System.out.println("NOO Here again");
        }

        //Tool.writeTextToFile("dfdfdf===========", tempFileName + "ttt");
        if (tempFile.exists()) {
            System.out.println("33333Here again");
        }

        File dest = new File(tempFileName + "_re");
        dest.renameTo(tempFile);
    }
}
