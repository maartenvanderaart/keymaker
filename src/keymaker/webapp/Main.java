package keymaker.webapp;

import static spark.Spark.*;

import keymaker.util.Password;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.apache.commons.configuration.*;
import org.apache.commons.configuration.ConfigurationException;
import java.io.FileWriter;


class Main {
    private static final Logger logger = LogManager.getLogger(Main.class.getName());
    private static int passwordLength = 12;
    private static String outputDir = "outputDir";



    public static void main(String[] args) {
        try {
            Configuration config = new PropertiesConfiguration("keymaker.properties");
            logger.info("Found \"keymaker.properties\"...");
            passwordLength = config.getInt("password.length");
            logger.info("Password length now set to: " + passwordLength);
            outputDir = config.getString("output.directory");
            logger.info("Output will be written to: " + outputDir);
        }
        catch(ConfigurationException cex)
        {
            logger.error(cex.toString(), "Unable to load \"keymaker.properties\"");
            logger.warn("Using default value for password length: " + passwordLength);
            logger.warn("Using default value for output directory: " + outputDir);
        }

        get("updatepassword/:username/:servername", (request, response) -> {
            Password password = new Password(request.params(":username"), request.params(":servername"), passwordLength);
            String usermodString = "usermod -p '" + password.toCrypt() + "' " + password.getUsername() +"\n";
            logger.info("server " + request.ip() + " requested a password change for user " + password.getUsername() +
                    " on " + password.getServername());
            String filename = outputDir + "/" + password.getUsername() + "@" + password.getServername() + ".txt";
            try (FileWriter file = new FileWriter(filename, true)){
                file.write(password.toString() + "\n");
                logger.info("wrote new password to " + filename);
            }
            return usermodString;
        });

    }
}