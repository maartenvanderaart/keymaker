package keymaker.webapp;

import static spark.Spark.*;
import org.json.JSONObject;

import keymaker.util.Password;

public class Main {
    public static void main(String[] args) {
    	get("updatepassword/:username/:servername", (request, response) -> {
    		String servername = request.params(":servername");
    		String username = request.params(":username");
    		Password password = new Password();
    		password.generatePassword();
    		password.setServername(servername);
    		password.setUsername(username);
    		String chmodString = "chmod -p '" + password.getCrypt() + "' " + username +"\n";
    		System.out.println("server " + request.ip() + " requested a password change for user " + username + " on " + servername);
    		JSONObject record = new JSONObject();
    		record.put("Date", password.getDate().toString());
    		record.put("Hostname", password.getServername());
    		record.put("Username", password.getUsername());
    		record.put("Password", password.getPassword());
    		System.out.println(record.toString());
    		return chmodString;
    	});

    }
}