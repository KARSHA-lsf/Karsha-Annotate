/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.karsha.query;


/**
 *
 * @author User
 */
public class QueryHandler {

    String domain;
    String query ;

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
    
    

    public QueryHandler() {
        domain = null;
        query = null;
       
    }
}
