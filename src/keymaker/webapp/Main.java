package keymaker.webapp;

import static spark.Spark.*;

import keymaker.util.Password;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileWriter;


public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class.getName());

    public static void main(String[] args) {
    	get("updatepassword/:username/:servername", (request, response) -> {
    		Password password = new Password(request.params(":username"), request.params(":servername"));
    		String usermodString = "usermod -p '" + password.getCrypt() + "' " + password.getUsername() +"\n";
    		logger.info("server " + request.ip() + " requested a password change for user " + password.getUsername() +
					" on " + password.getServername());
            //[TODO] Make the output directory configurable
            String filename = "outputDir/" + password.getUsername() + "@" + password.getServername() + ".txt";
            try (FileWriter file = new FileWriter(filename, true)){
                file.write(password.toString() + "\n");
                logger.info("wrote new password to " + filename);
            }
    		return usermodString;
    	});

    }
}