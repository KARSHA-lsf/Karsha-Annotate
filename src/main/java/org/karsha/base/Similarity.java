/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.karsha.base;

import java.util.HashMap;
import java.util.Iterator;


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
 * Date Author Changes May 30, 2012 Kasun Perera Created
 *
 */


/**
 *
 * Class contains various methods for calculating
 * Similarity Scores
 */
public class Similarity {
    
    
    /**
     * Calculating Cosine Similarity between two documents.
     * @param vector1- Hashmap IFIDF score vector
     * @param vector2- Hashmap IFIDF score vector
     * @return -Cosine Similarity Score between two documents
     */
    public static double getCosineSimilarity(HashMap vector1, HashMap vector2) {
        double innerProduct = 0;
        double sumOfSqureOfVec1 = 0;
        double sumOfSqureOfVec2 = 0;

        for (Iterator it = vector1.keySet().iterator(); it.hasNext();) {
            String w = (String) it.next();
            float vec1Val = (Float) vector1.get(w);
            float vec2Val = (Float) (vector2.containsKey(w) ? vector2.get(w) : (float) 0);
            // innerProduct += (Float)vector1.get(w) * (Float)(vector2.containsKey(w) ? vector2.get(w) : 0);
            innerProduct += vec1Val * vec2Val;
            sumOfSqureOfVec1 += vec1Val * vec1Val;
            sumOfSqureOfVec2 += vec2Val * vec2Val;

        }
        return innerProduct / (Math.sqrt(sumOfSqureOfVec1) * Math.sqrt(sumOfSqureOfVec2));
    }
    
    /**
     * Calculating Cosine Jaccard between two documents.
     * @param vector1- Hashmap IFIDF score vector
     * @param vector2- Hashmap IFIDF score vector
     * @return -Jaccard Similarity Score between two documents
     */
    public static double getJaccardSimilarity(HashMap vector1, HashMap vector2) {
        double innerProduct = 0;
        double sumOfSqureOfVec1 = 0;
        double sumOfSqureOfVec2 = 0;

        for (Iterator it = vector1.keySet().iterator(); it.hasNext();) {
            String w = (String) it.next();
            float vec1Val = (Float) vector1.get(w);
            float vec2Val = (Float) (vector2.containsKey(w) ? vector2.get(w) : (float) 0);
            // innerProduct += (Float)vector1.get(w) * (Float)(vector2.containsKey(w) ? vector2.get(w) : 0);
            innerProduct += vec1Val * vec2Val;
            sumOfSqureOfVec1 += vec1Val * vec1Val;
            sumOfSqureOfVec2 += vec2Val * vec2Val;

        }
        return (innerProduct / (sumOfSqureOfVec1 + sumOfSqureOfVec2 - innerProduct));
    }
    
}
