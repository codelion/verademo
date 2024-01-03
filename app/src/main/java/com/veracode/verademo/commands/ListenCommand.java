// ... [rest of the imports and class code] ...

@Override
public void execute(String blabberUsername) {
    String insertListenersSql = "INSERT INTO listeners (blabber, listener, status) values (?, ?, 'Active');";
    logger.info(insertListenersSql);
    PreparedStatement action;
    try {
        action = connect.prepareStatement(insertListenersSql);

        action.setString(1, blabberUsername);
        action.setString(2, username);
        action.execute();

        String selectBlabNameSql = "SELECT blab_name FROM users WHERE username = ?";
        PreparedStatement selectStatement = connect.prepareStatement(selectBlabNameSql);
        selectStatement.setString(1, blabberUsername);
        logger.info(selectStatement.toString());
        ResultSet result = selectStatement.executeQuery();
        result.next();

        String event = username + " started listening to " + blabberUsername + " (" + result.getString(1) + ")";
        String insertHistorySql = "INSERT INTO users_history (blabber, event) VALUES (?, ?)";
        PreparedStatement insertHistoryAction = connect.prepareStatement(insertHistorySql);
        insertHistoryAction.setString(1, username);
        insertHistoryAction.setString(2, event);
        logger.info(insertHistoryAction.toString());
        insertHistoryAction.execute();

    } catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
}

// ... [rest of the class code] ...