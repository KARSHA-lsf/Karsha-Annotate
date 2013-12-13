package org.karsha.data;

import org.karsha.entities.FiboRelation;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: randula
 * Date: 12/11/13
 * Time: 9:06 AM
 * To change this template use File | Settings | File Templates.
 */
public class FiboRelationDB {
    public static ArrayList<FiboRelation> getAllFiboRelations() {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        Statement ps = null;
        ResultSet rs = null;

        String query = "select Source_Node,Target_node from fibotermgraph";

        try {
            ps = connection.createStatement();

            rs = ps.executeQuery(query);
            ArrayList<FiboRelation> fiboRelationList = new ArrayList<FiboRelation>();
            while (rs.next()) {
                FiboRelation d = new FiboRelation();
                d.setFiboId(Integer.parseInt(rs.getString("Source_Node")));
                d.setChildFiboId(Integer.parseInt(rs.getString("Target_node")));
                fiboRelationList.add(d);
            }
            return fiboRelationList;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
}
