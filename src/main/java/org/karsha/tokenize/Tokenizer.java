
/**
 *   Copyright (C) 2013, Lanka Software Foundation and University of Maryland.
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU Affero General Public License as
 *   published by the Free Software Foundation, either version 3 of the
 *   License, or (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Affero General Public License for more details.
 *
 *   You should have received a copy of the GNU Affero General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>
 *
 * Date Author Changes 
 * 2010 Shanchan Wu Created (Lucene 2.9 compatible)
 * 
 *
 */
package org.karsha.tokenize;


import java.io.Reader;

import org.apache.lucene.analysis.TokenStream;

public interface Tokenizer {
	public TokenStream tokenStream(String text);

	public TokenStream tokenStream(Reader reader);

	public String processText(String text);
}
