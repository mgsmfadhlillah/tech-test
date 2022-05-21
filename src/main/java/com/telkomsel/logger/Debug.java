package com.telkomsel.logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Debug {
  private static final Logger log = LogManager.getLogger("Debug_Log");
  public static void info(String msg) { log.info(msg); }
  public static void error(String msg, Exception ex) {log.error(msg, ex.fillInStackTrace()); }
}
