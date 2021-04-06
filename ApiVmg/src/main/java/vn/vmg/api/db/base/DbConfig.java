package vn.vmg.api.db.base;

public class DbConfig {
	private String driver = "net.sourceforge.jtds.jdbc.Driver";
	private String url;
	private String user;
	private String pass;
	private int minConnection = 2;
	private int maxConnection = 100;
	private int partitionCount = 1;
	private int timeOutConnection = 180000;
	private int maxConnectionAgeInSeconds = 60;
	private String connectionTestStatement = "select 1 from dual";

	private boolean autoCommit = true;

	public String getDriver() {
		return this.driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUser() {
		return this.user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPass() {
		return this.pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public int getMinConnection() {
		return this.minConnection;
	}

	public void setMinConnection(int minConnection) {
		this.minConnection = minConnection;
	}

	public int getMaxConnection() {
		return this.maxConnection;
	}

	public void setMaxConnection(int maxConnection) {
		this.maxConnection = maxConnection;
	}

	public int getPartitionCount() {
		return this.partitionCount;
	}

	public void setPartitionCount(int partitionCount) {
		this.partitionCount = partitionCount;
	}

	public int getTimeOutConnection() {
		return this.timeOutConnection;
	}

	public void setTimeOutConnection(int timeOutConnection) {
		this.timeOutConnection = timeOutConnection;
	}

	public boolean isAutoCommit() {
		return autoCommit;
	}

	public void setAutoCommit(boolean autoCommit) {
		this.autoCommit = autoCommit;
	}

	public int getMaxConnectionAgeInSeconds() {
		return maxConnectionAgeInSeconds;
	}

	public void setMaxConnectionAgeInSeconds(int maxConnectionAgeInSeconds) {
		this.maxConnectionAgeInSeconds = maxConnectionAgeInSeconds;
	}

	public String getConnectionTestStatement() {
		return connectionTestStatement;
	}

	public void setConnectionTestStatement(String connectionTestStatement) {
		this.connectionTestStatement = connectionTestStatement;
	}
	
	

}
