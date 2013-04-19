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

/**
 *
 * @author User
 */
public class QueryDocHandler {
    String chkSelection[] ;

    public String[] getChkSelection() {
        return chkSelection;
    }

    public void setChkSelection(String[] chkSelection) {
        this.chkSelection = chkSelection;
    }
    
    public String getSelQueryDoc() {
        return selQueryDoc ;
    }

    public void setSelQueryDoc(String selQueryDoc) {
        this.selQueryDoc = selQueryDoc;
    }

   
    String selQueryDoc ;
    
    public QueryDocHandler(){
        selQueryDoc = null ;
        chkSelection = null ;
    }
}
