/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.karsha.controler;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
//import org.karsha.data.TaxonomyDB;
import org.karsha.entities.Phrase;
//import org.karsha.entities.Taxonomy;

/**
 *
 * @author v-saba
 */
public class SelectPhrasesServlet extends HttpServlet{
    
    
    @Override 
    protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        
    }
    
    @Override 
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
String userPath = request.getServletPath();
        String action;
        String url="";
     
        try{
            action = request.getParameter("form-action");
            
            // code to process when phrases are being selected
            if(action.equals("add_phrases")){
                
                HashMap<Integer,Phrase> selectedPhrasesMap = null;
                HttpSession session = request.getSession();
                
                String[] selectedPhrases = (request.getParameter("selected_phrases").toString()).split("\t");
                String[] selectedPhrasesIndexes = (request.getParameter("selected_phrases_index").toString()).split("\t");

                // get the selected hashmap from session. if its null (as in the case for the 1st time) then create a new one)
                selectedPhrasesMap = ( HashMap<Integer,Phrase>)session.getAttribute("selectedPhrasesMap");
                HashMap<Integer,String> allphrasesMap = ( HashMap<Integer,String>)session.getAttribute("allphrases");
                 
                if(selectedPhrasesMap == null)
                    selectedPhrasesMap = new HashMap<Integer,Phrase>();
                
                for(int s=0;s<=selectedPhrasesIndexes.length-1;s++){
                    
                    Phrase p = null;
                     
                    int key =Integer.parseInt(selectedPhrasesIndexes[s]); 
                    
                    
                    if(selectedPhrasesMap.containsKey(key)){
                        p = (Phrase)selectedPhrasesMap.get(key);
                        p.setScore(Integer.parseInt(request.getParameter("phrase_relevance_" + selectedPhrasesIndexes[s])));
                        
                    }
                    
                    
                    else{
                        p = new Phrase();
                        p.setPhraseContent(selectedPhrases[s]);
                        p.setScore(0);
                        selectedPhrasesMap.put(Integer.parseInt(selectedPhrasesIndexes[s]), p);
                        
                        allphrasesMap.remove(Integer.parseInt(selectedPhrasesIndexes[s]) );
                    }
                }
                
                session.setAttribute("allphrases", allphrasesMap);
                session.setAttribute("selectedPhrasesMap", selectedPhrasesMap);
                
                // resetting the hidden values so that value are not lost 
                request.setAttribute("selected_phrases",request.getParameter("selected_phrases").toString());
                request.setAttribute("selected_phrases_index",request.getParameter("selected_phrases_index"));
		
                url = "/WEB-INF/view/topkphrases.jsp";
                
                
            }else if(action.equals("remove_phrases")){
                
                String[] removedPhrasesIndexes = (request.getParameter("removed_phrases_index").toString()).split("\t");
                HttpSession session = request.getSession();
                
                
                HashMap<Integer,String> allphrasesMap = (HashMap<Integer,String>)session.getAttribute("allphrases");
                
                HashMap<Integer,Phrase> selectedPhrasesMap = (HashMap<Integer,Phrase>)session.getAttribute("selectedPhrasesMap");
                
                for(int s=0;s<=removedPhrasesIndexes.length-1;s++){
                    
                    int key = Integer.parseInt(removedPhrasesIndexes[s]);
                    Phrase p = selectedPhrasesMap.get(key);
                    
                    //remove from selected Hash Map
                    selectedPhrasesMap.remove(key);
                    
                    // add to all phrases map
                    allphrasesMap.put(key, p.getPhraseContent());
                }
                 
                for (Map.Entry entry : selectedPhrasesMap.entrySet()) {
                    int selectedPhraseRelevanceIndex = Integer.parseInt(request.getParameter("phrase_relevance_" + entry.getKey()).toString());
                    Phrase p = (Phrase)entry.getValue();
                    p.setScore(selectedPhraseRelevanceIndex);
                }
                
                session.setAttribute("allphrases", allphrasesMap);
                session.setAttribute("selectedPhrasesMap", selectedPhrasesMap);
                
                url = "/WEB-INF/view/topkphrases.jsp";
                
            }else if(action.equals("save_phrases")){
                
                HttpSession session = request.getSession();
                
                HashMap<Integer,Phrase> selectedPhrasesMap = (HashMap<Integer,Phrase>)session.getAttribute("selectedPhrasesMap");
                
                for (Map.Entry entry : selectedPhrasesMap.entrySet()) {
                    Phrase p = ((Phrase)entry.getValue());
                    //p.setDateUpdated(new Date().toString());
                    p.setScore(Integer.parseInt(request.getParameter("phrase_relevance_" + entry.getKey().toString())));
                    p.setDocId(Integer.parseInt(session.getAttribute("documentID").toString()));
                    p.setUserId(Integer.parseInt(session.getAttribute("userId").toString()));
                    
                }
                
                // reset session.
                session.setAttribute("selectedPhrasesMap", selectedPhrasesMap);
                
                
                /*code to pulling taxo tree from databse*/
                  /*
           
                int collectionID = (Integer) session.getAttribute("collectionType");
                ArrayList<Taxonomy> taxotree= TaxonomyDB.getTaxoTermsByCollectionID(collectionID);

                Tree<String> tree = new ArrayListTree<String>();
                tree.add("root");
                for(int i = 0; i<taxotree.size();i++){
                    if(taxotree.get(i).getParentId()==0){
                        
                        int parentsID=taxotree.get(i).getTaxoId();
                        String parentTerm=taxotree.get(i).getPhraseContent();
                        
                        try {
                            tree.add("root",parentTerm );
                        } catch (NodeNotFoundException ex) {
                            Logger.getLogger(ClassifyDocServelet.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        for(int j=0;j<taxotree.size();j++) {
                            if(taxotree.get(j).getParentId()==parentsID){
                                    try {
                                        tree.add(parentTerm,taxotree.get(j).getPhraseContent() );
                                    } catch (NodeNotFoundException ex) {
                                        Logger.getLogger(ClassifyDocServelet.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                            }
                        }
                    }
                session.setAttribute("taxonomyTree",tree);

                url ="/classify";
            
                }
                
                */
        
            }
            
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
        
        RequestDispatcher dispatcher =getServletContext().getRequestDispatcher(url);
        dispatcher.forward(request, response);
        
        
    }
    
}
