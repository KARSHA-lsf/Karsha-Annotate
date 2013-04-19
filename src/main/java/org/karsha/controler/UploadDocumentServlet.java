/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.karsha.controler;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;
import org.karsha.data.CollectionTypDB;
import org.karsha.data.DocumentDB;
import org.karsha.entities.CollectionType;
import org.karsha.entities.Document;

import org.apache.commons.fileupload.FileItem;

import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.karsha.data.DocSectionDB;
import org.karsha.document.PDFBookmark;
import org.karsha.entities.DocSection;


/**
 *
 * @author v-saba
 */
public class UploadDocumentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.getRequestDispatcher("/WEB-INF/view/uploaddoc.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        SecurityManager sm = System.getSecurityManager();

        HttpSession session = request.getSession();
        String userPath = request.getServletPath();
        String url = "";
        List items = null;

        if (userPath.equals("/uploaddocuments")) {

            boolean isMultipart = ServletFileUpload.isMultipartContent(request);


            if (isMultipart) {
                DiskFileItemFactory factory = new DiskFileItemFactory();
                factory.setSizeThreshold(5000000);
                //  FileItemFactory factory = new DiskFileItemFactory();

                ServletFileUpload upload = new ServletFileUpload(factory);
                try {
                    // items = upload.
                    items = upload.parseRequest(request);
                } catch (Exception ex) {
                    Logger.getLogger(UploadDocumentServlet.class.getName()).log(Level.SEVERE, null, ex);
                }

                HashMap requestParameters = new HashMap();

                try {



                    Iterator iterator = items.iterator();
                    while (iterator.hasNext()) {

                        FileItem item = (FileItem) iterator.next();

                        if (item.isFormField()) {

                            requestParameters.put(item.getFieldName(), item.getString());

                        } else {
                            PDFBookmark pdfBookmark = new PDFBookmark();
                            byte[] fileContent = item.get();
                            LinkedHashMap<String, String> docSectionMap = pdfBookmark.splitPDFByBookmarks(fileContent);

                            if (!docSectionMap.isEmpty()) {


                                Document newDocument = new Document();
                                newDocument.setDocumentName(requestParameters.get("docName").toString());
                                newDocument.setUserId(Integer.parseInt(session.getAttribute("userId").toString()));
                                String a = requestParameters.get("collection_type_new").toString();
                                newDocument.setCollectionId(Integer.parseInt(a));
                                //  newDocument.setDocumentContent(fileContent);

                                request.setAttribute("requestParameters", requestParameters);

                                if (DocumentDB.isFileNameDuplicate(newDocument.getDocumentName())) {
                                    url = "/WEB-INF/view/uploaddoc.jsp?error=File name already exists, please enter another file name";
                                } else {
                                    //DocumentDB.insert(newDocument);
                                    DocumentDB.insertDocMetaData(newDocument);
                                    int docId = DocumentDB.getDocumentDataByDocName(requestParameters.get("docName").toString()).getDocId();

                                    DocSection docSection = new DocSection();
                                    for (Map.Entry entry : docSectionMap.entrySet()) {
                                        docSection.setParentDocId(docId);
                                        docSection.setSectionName((String) entry.getKey());
                                        byte[] byte1=((String)entry.getValue()).getBytes();
                                        docSection.setSectionContent(byte1);
                                        docSection.setUserId(Integer.parseInt(session.getAttribute("userId").toString()));
                                        //To-Do set this correctly
                                        //docSection.setSectionCatog(Integer.parseInt(session.getAttribute("sectioncat").toString()));
                                        docSection.setSectionCatog(4);
                                        DocSectionDB.insert(docSection);

                                    }


                                    url = "/WEB-INF/view/uploaddoc.jsp?succuss=File Uploaded Successfully";
                                }


                            } /*
                             * check wheter text can be extracted
                             */ /*
                             * if(DocumentValidator.isDocValidated(fileContent)){
                             *
                             *
                             * Document newDocument = new Document();
                             * newDocument.setDocumentName(requestParameters.get("docName").toString());
                             * newDocument.setUserId(Integer.parseInt(session.getAttribute("userId").toString()));
                             * String a =
                             * requestParameters.get("collection_type_new").toString();
                             * newDocument.setCollectionId(Integer.parseInt(a));
                             * newDocument.setDocumentContent(fileContent);
                             *
                             * request.setAttribute("requestParameters",
                             * requestParameters);
                             *
                             * if
                             * (DocumentDB.isFileNameDuplicate(newDocument.getDocumentName()))
                             * { url = "/WEB-INF/view/uploaddoc.jsp?error=File
                             * name already exists, please enter another file
                             * name"; } else { //DocumentDB.insert(newDocument);
                             * DocumentDB.insertDocMetaData(newDocument); url =
                             * "/WEB-INF/view/uploaddoc.jsp?succuss=File
                             * Uploaded Successfully"; }
                             *
                             *
                             * }
                             *
                             */ else {
                                url = "/WEB-INF/view/uploaddoc.jsp?error=Text can't be extracted from file, Please try onother";
                            }







                        }

                    }

                } //                    catch (FileUploadException e) {
                //                        e.printStackTrace();
                //                        
                //                        
                //                    }
                //                    
                catch (Exception e) {

                    e.printStackTrace();
                }
            }

            request.getRequestDispatcher(url).forward(request, response);

        }

    }
}