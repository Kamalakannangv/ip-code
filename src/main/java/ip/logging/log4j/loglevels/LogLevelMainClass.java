package ip.logging.log4j.loglevels;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class LogLevelMainClass {
	
	private static Logger log = Logger.getLogger(LogLevelMainClass.class);
	   
	   public static void main(String[] args) {
	      log.setLevel(Level.WARN);

	      log.trace("Trace Message!");
	      log.debug("Debug Message!");
	      log.info("Info Message!");
	      log.warn("Warn Message!");
	      log.error("Error Message!");
	      log.fatal("Fatal Message!");
	   }
}
