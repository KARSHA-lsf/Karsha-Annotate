/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.karsha.base;

import java.util.Comparator;
import java.util.Map;

/** 
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
 * 
 * Date             Author          Changes 
 * Aug 13, 2012     Kasun Perera    Created   
 * 
 */ 

/**
 * TODO- describe the  purpose  of  the  class
 * 
 */
class ValueComparator implements Comparator<String> {

    Map<String, Double> base;
    public ValueComparator(Map<String, Double> base) {
        this.base = base;
    }

    public int compare(String a, String b) {
        return base.get(b).compareTo(base.get(a));
    }
}
