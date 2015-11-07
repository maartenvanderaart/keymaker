package keymaker.util;

import java.util.Date;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.codec.digest.Crypt;
import org.json.JSONObject;

public class Password {
	private final String password;
	private final String username;
	private final String servername;
	private final Date date = new Date();

	public Password(String username, String servername, int length){
		this.password = RandomStringUtils.randomAlphanumeric(length);
		this.username = username;
		this.servername = servername;
	}

	public String getUsername() {
		return username;
	}
	public String getServername() {
		return servername;
	}
	public Date getDate() {
		return date;
	}
	public String getPassword() {
		return password;
	}
	public String toCrypt(){
		return Crypt.crypt(password, "$6$" +  RandomStringUtils.randomAlphanumeric(16));
	}
	public JSONObject toJSON(){
		JSONObject record = new JSONObject();
		record.put("Date", date.toString());
		record.put("Hostname", servername);
		record.put("Username", username);
		record.put("Password", password);
		return record;
	}
	public String toString(){
		return "Date = " + date.toString() +
				", Hostname = " + servername +
				", Username = " + username +
				", Password = " + password;
	}
}
