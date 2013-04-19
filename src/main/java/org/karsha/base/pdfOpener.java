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
package org.karsha.base ;

import java.io.* ;
import java.lang.* ;
import java.sql.Blob ;
//import oracle.sql.* ; 

/**
 *
 * @author User
 */
public class pdfOpener {
    
    public pdfOpener(String filename, Blob blob){
     /*   try
    {
      File              blobFile   = new File(filename);
      FileOutputStream  outStream  = new FileOutputStream(blobFile);
      InputStream       inStream   = blob.getBinaryStream();
 
      int     length  = -1;
      int     size    = blob..getBufferSize();
      byte[]  buffer  = new byte[size];
 
      while ((length = inStream.read(buffer)) != -1)
      {
        outStream.write(buffer, 0, length);
        outStream.flush();
      }
 
      inStream.close();
      outStream.close();
    }
    catch (Exception e)
    {
      e.printStackTrace();
      System.out.println("ERROR(djv_exportBlob) Unable to export:"+filename);
    }*/
    }
    
}
