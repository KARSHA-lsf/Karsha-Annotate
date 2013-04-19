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
 * Date Author Changes Jan 22, 2013 Kasun Perera Created
 *
 */
package org.karsha.document;


/**
 * TODO- describe the purpose of the class
 *
 */

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.SimpleBookmark;

import com.itextpdf.text.pdf.parser.PdfTextExtractor;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

//import java.util.*;
public class PDFBookmark {

    private LinkedHashMap<String, String> docsSeperated = new LinkedHashMap<String, String>();
/*
 * param- pdf -get pdf file as a byte stream  
 */
    public LinkedHashMap splitPDFByBookmarks(byte[] pdf) {
        try {
            PdfReader reader = new PdfReader(pdf);
            //List of bookmarks: each bookmark is a map with values for title, page, etc
            List<HashMap<String, Object>> bookmarks = SimpleBookmark.getBookmark(reader);
            for (int i = 0; i < bookmarks.size(); i++) {
                HashMap bm = bookmarks.get(i);
                HashMap nextBM = i == bookmarks.size() - 1 ? null : bookmarks.get(i + 1);
                HashMap previBM;
                if (i ==0) {
                    previBM = bookmarks.get(i);
                } else {
                    previBM = bookmarks.get(i-1);
                }

                //String title = ((String) bm.get("Title")).split(" ")[1]; //get only the up to 2nd word
                if ((String) bm.get("Title") != null && (String) bm.get("Page") != null) {


                    String title = ((String) bm.get("Title"));
                    // log.debug("Titel: " + title);

                  //  System.out.println((String) bm.get("Page"));
                    String startPage = ((String) bm.get("Page")).split(" ")[0];
                    //String startPage = ((String) bm.get("Page"));
                    String startPageNextBM = nextBM == null ? "" + (reader.getNumberOfPages() + 1) : ((String) nextBM.get("Page")).split(" ")[0];
                    //log.debug("Page: " + startPage);
                    // log.debug("------------------");

                    parsePdf(reader, title, ((String) previBM.get("Title")), Integer.valueOf(startPage), Integer.valueOf(startPageNextBM));



                    // extractBookmarkToPDF(reader, Integer.valueOf(startPage), Integer.valueOf(startPageNextBM), title + ".pdf", outputFolder);
                }
            }
        } catch (IOException e) {
            //log.error(e.getMessage());
        }
        return docsSeperated;
    }

    /*
     * extract the text from doc_sections and put it to a LinkedHashMap
     */
    public void parsePdf(PdfReader reader, String title, String preBokmarkTitle, int pageFrom, int pageTo) throws IOException {

        String pagetext = null;

        String text = null;
        String txtPreSec;
        if (PdfTextExtractor.getTextFromPage(reader, pageFrom).contains(title)) {
            text = PdfTextExtractor.getTextFromPage(reader, pageFrom);
           // System.out.println(title + " contains at " + text.indexOf(title));
            txtPreSec = text.substring(0, text.indexOf(title));
            if (!txtPreSec.isEmpty()) {
                addToPreSec(preBokmarkTitle, txtPreSec);
            }
            pagetext = text.substring(text.indexOf(title));
            //  System.out.println(pagetext);
            pageFrom++;
        } else {
           // System.out.println("Can't find String " + title);
        }

        try {
            while (pageFrom < pageTo) {
                pagetext = pagetext + PdfTextExtractor.getTextFromPage(reader, pageFrom);
                pageFrom++;
            }
            //  System.out.println(pagetext);

            if (!pagetext.isEmpty()) {

                docsSeperated.put(title, pagetext);
            }
        } catch (Exception ex) {
            // log.error(ex.getMessage());
        }

    }

    public void addToPreSec(String secName, String textToAdd) {
        if (docsSeperated.containsKey(secName)) {

            docsSeperated.put(secName, (String) docsSeperated.get(secName) + textToAdd);
        }

    }
}
