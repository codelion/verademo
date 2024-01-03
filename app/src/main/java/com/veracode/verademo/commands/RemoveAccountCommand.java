@Override
public void execute(String blabberUsername) {
    String deleteListenersSql = "DELETE FROM listeners WHERE blabber=? OR listener=?;";
    logger.info(deleteListenersSql);
    try (PreparedStatement deleteListenersStmt = connect.prepareStatement(deleteListenersSql)) {
        deleteListenersStmt.setString(1, blabberUsername);
        deleteListenersStmt.setString(2, blabberUsername);
        deleteListenersStmt.execute();

        String selectBlabNameSql = "SELECT blab_name FROM users WHERE username = ?";
        try (PreparedStatement selectBlabNameStmt = connect.prepareStatement(selectBlabNameSql)) {
            selectBlabNameStmt.setString(1, blabberUsername);
            logger.info(selectBlabNameSql);
            ResultSet result = selectBlabNameStmt.executeQuery();
            if (result.next()) {
                String event = "Removed account for blabber " + result.getString(1);
                String insertHistorySql = "INSERT INTO users_history (blabber, event) VALUES (?, ?)";
                try (PreparedStatement insertHistoryStmt = connect.prepareStatement(insertHistorySql)) {
                    insertHistoryStmt.setString(1, blabberUsername);
                    insertHistoryStmt.setString(2, event);
                    logger.info(insertHistorySql);
                    insertHistoryStmt.execute();
                }
            }

            String deleteUserSql = "DELETE FROM users WHERE username = ?";
            try (PreparedStatement deleteUserStmt = connect.prepareStatement(deleteUserSql)) {
                deleteUserStmt.setString(1, blabberUsername);
                logger.info(deleteUserSql);
                deleteUserStmt.execute();
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}