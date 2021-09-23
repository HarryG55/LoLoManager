package VO;

import javafx.beans.property.SimpleStringProperty;

/**
 * @author YachenGuo
 * @ClassName databaseVO
 * @Description
 * @createTime 2021 08 19 16:28
 */
public class DatabaseVO {

    private SimpleStringProperty databaseName = new SimpleStringProperty();
    private SimpleStringProperty databaseType = new SimpleStringProperty();
    private SimpleStringProperty host = new SimpleStringProperty();
    private SimpleStringProperty port = new SimpleStringProperty();
    private SimpleStringProperty user = new SimpleStringProperty();
    private SimpleStringProperty password = new SimpleStringProperty();
    private SimpleStringProperty memo = new SimpleStringProperty();
    private SimpleStringProperty state = new SimpleStringProperty();
    private SimpleStringProperty schema = new SimpleStringProperty();

    public String getSchema() {
        return schema.get();
    }

    public SimpleStringProperty schemaProperty() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema.set(schema);
    }

    public String getDatabaseName() {
        return databaseName.get();
    }

    public void setDatabaseName(String databaseName) { this.databaseName.setValue(databaseName); }

    public String getHost() {
        return host.get();
    }

    public void setHost(String host) {
        this.host.set(host);
    }

    public String getPort() {
        return port.get();
    }

    public void setPort(String port) {
        this.port.set(port);
    }

    public String getDatabaseType() {
        return databaseType.get();
    }

    public void setDatabaseType(String databaseType) {
        this.databaseType.set(databaseType);
    }

    public String getUser() {
        return user.get();
    }

    public void setUser(String user) { this.user.set(user); }

    public String getPassword() {
        return password.get();
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public String getMemo() {
        return memo.get();
    }

    public void setMemo(String memo) {
        this.memo.set(memo);
    }

    public String getState() {
        return state.get();
    }

    public void setState(String state) {
        this.state.set(state);
    }
}
