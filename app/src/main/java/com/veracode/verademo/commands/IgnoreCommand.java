@Override
public void execute(String blabberUsername) {
    String sqlQuery = "DELETE FROM listeners WHERE blabber=? AND listener=?;";
    logger.info(sqlQuery);
    PreparedStatement action;
    try {
        action = connect.prepareStatement(sqlQuery);

        action.setString(1, blabberUsername);
        action.setString(2, username);
        action.execute();

        sqlQuery = "SELECT blab_name FROM users WHERE username = ?";
        PreparedStatement selectStatement = connect.prepareStatement(sqlQuery);
        selectStatement.setString(1, blabberUsername);
        logger.info(sqlQuery);
        ResultSet result = selectStatement.executeQuery();
        result.next();

        String event = username + " is now ignoring " + blabberUsername + " (" + result.getString(1) + ")";
        sqlQuery = "INSERT INTO users_history (blabber, event) VALUES (?, ?)";
        PreparedStatement insertStatement = connect.prepareStatement(sqlQuery);
        insertStatement.setString(1, username);
        insertStatement.setString(2, event);
        logger.info(sqlQuery);
        insertStatement.execute();
    } catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
}