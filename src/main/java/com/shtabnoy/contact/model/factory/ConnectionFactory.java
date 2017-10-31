package com.shtabnoy.contact.model.factory;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionFactory {
    private Connection con;

    public Connection getConnection() {
        try {
            InitialContext initContext = new InitialContext();
            DataSource ds = (DataSource) initContext.lookup("java:comp/env/jdbc/contact_directory");
            con = ds.getConnection();


        } catch (NamingException | SQLException e) {
            e.printStackTrace();
        }
        return con;
    }
}
