

/**
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
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>
 *
 * Date Author Changes April 14, 2012 v-bala Created
 *
 */

package org.karsha.data;



import java.sql.*;

public class DBUtil
{
    public static void closeStatement(Statement s)
    {
        try
        {
            if (s != null)
                s.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
    
    public static void closePreparedStatement(Statement ps)
    {
        try
        {
            if (ps != null)
                ps.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static void closeResultSet(ResultSet rs)
    {
        try
        {
            if (rs != null)
                rs.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
}
