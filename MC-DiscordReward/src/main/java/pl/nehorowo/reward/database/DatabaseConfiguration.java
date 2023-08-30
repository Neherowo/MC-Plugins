package pl.nehorowo.reward.database;

public record DatabaseConfiguration(String host, String username, String password, String table, int port,
                                    boolean ssl) {

}
