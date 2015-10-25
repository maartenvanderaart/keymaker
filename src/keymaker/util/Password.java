package keymaker.util;

import java.math.BigInteger;
import java.util.Date;
import java.util.Random;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.codec.digest.Crypt;

public class Password {
	private String password;
	private String crypt;
	private String salt;
	private String username;
	private String servername;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getServername() {
		return servername;
	}
	public void setServername(String servername) {
		this.servername = servername;
	}
	private Date date = new Date();
	
	public Date getDate() {
		return date;
	}
	public String getPassword() {
		return password;
	}
	public void generatePassword() {
		Random random = new Random();
		this.password = new BigInteger(130, random).toString(32);
		generateSalt(16);
		generateCrypt(password);
	}
	public String getSalt() {
		return salt;
	}
	private void generateSalt(int length) {
		this.salt = "$6$" + RandomStringUtils.randomAlphanumeric(length);
	}
	public String getCrypt() {
		return crypt;
	}
	private void generateCrypt(String password){
		this.crypt = Crypt.crypt(password, salt);
	}
}
