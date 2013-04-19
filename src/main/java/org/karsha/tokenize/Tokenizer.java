package org.karsha.tokenize;
/**
 * Copyright (C) 2012, Lanka Software Foundation.
 *
 * Date Author Changes 
 * 2010 Shanchan Wu Created (Lucene 2.9 compatible)
 * 
 *
 */

import java.io.Reader;

import org.apache.lucene.analysis.TokenStream;

public interface Tokenizer {
	public TokenStream tokenStream(String text);

	public TokenStream tokenStream(Reader reader);

	public String processText(String text);
}
