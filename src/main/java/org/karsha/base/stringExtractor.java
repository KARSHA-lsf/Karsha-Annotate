/*
 *   KarshaAnnotate- Annotation tool for financial documents
 *  
 *   Copyright (C) 2013, Lanka Software Foundation and and University of Maryland.
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
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.karsha.base;

import java.util.ArrayList;

/**
 *
 * @author User
 */
public class stringExtractor {

    public ArrayList<String> lstResult;
    String strInput;

    public stringExtractor(String strInput) {
        this.strInput = strInput;
        lstResult = new ArrayList<String>();
    }

    private boolean relevantStr(String strComing) {

        return (strComing.contains(".pdf"));
    }

    public void doExtraction() {
        final String ST = "<a";
        final String ET = "</a>";
        int curIndexST = strInput.lastIndexOf(ST,0);
        int curIndexET = strInput.indexOf(ET,0);
        int index = 0;
        do {
            String strTemp = strInput.substring(curIndexST, (curIndexET+4));
            if (relevantStr(strTemp)) {
                lstResult.add(index, strTemp);
                index++;
            }
            curIndexST++;
            curIndexST = strInput.indexOf(ST, curIndexST);
            curIndexET++;
            curIndexET = strInput.indexOf(ET, curIndexET);
        } while ((curIndexST != -1) & (curIndexET != -1));
    }
    public static void main(String argv[]){
        stringExtractor sE = new stringExtractor("<a herf =\"1.pdf\" > test 1 "
                + "</a><a herf =\"1.txt\" > test 1 </a>" 
                + "<a herf =\"2.pdf\" > test 2 </a>") ;
        sE.doExtraction() ;
        Object ia[] = sE.lstResult.toArray();
        for(int i=0; i<ia.length; i++)
            System.out.println(ia[i].toString()) ;
            
    }
}
