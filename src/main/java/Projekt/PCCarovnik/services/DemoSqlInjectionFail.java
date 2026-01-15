import java.sql.*;

public class DemoSqlInjectionFail {
  public void bad(Connection conn, String username) throws SQLException {
    String q = "SELECT * FROM users WHERE username = '" + username + "'";
    Statement st = conn.createStatement();
    st.executeQuery(q);
  }
}
