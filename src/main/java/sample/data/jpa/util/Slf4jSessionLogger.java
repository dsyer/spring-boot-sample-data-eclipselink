package sample.data.jpa.util;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.persistence.logging.AbstractSessionLog;
import org.eclipse.persistence.logging.SessionLog;
import org.eclipse.persistence.logging.SessionLogEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * EclipseLink logger over Slf4J.
 * 
 * <p>
 * In order to register this class with EclipseLink, set the value of property
 * <code>eclipselink.logging.logger</code> to this class' fully qualified name
 * (<code>sample.data.jpa.util.Slf4jSessionLogger</code>).
 * 
 * <p>
 * Once registered, log level configuration is no longer defined within EclipseLink
 * (using property <code>eclipselink.logging.level</code>), 
 * but within whatever SL4J implementation is being used (<code>logback.xml</code> for <code>logback</code>,
 * <code>log4j.properties</code> for <code>log4j</code>, etc.).
 * 
 * <p>
 * The rest of EclipseLink logging properties can still be used 
 * (eclipselink.logging.timestamp, eclipselink.logging.thread,  
 * eclipselink.logging.session, eclipselink.logging.connection 
 * y eclipselink.logging.parameters) to configure the output format.
 * 
 * <p>
 * The following log categories are available:
 * 
 * <p>
 * <ul>
 * <li>org.eclipse.persistence.logging.default
 * <li>org.eclipse.persistence.logging.sql
 * <li>org.eclipse.persistence.logging.transaction
 * <li>org.eclipse.persistence.logging.event
 * <li>org.eclipse.persistence.logging.connection
 * <li>org.eclipse.persistence.logging.query
 * <li>org.eclipse.persistence.logging.cache
 * <li>org.eclipse.persistence.logging.propagation
 * <li>org.eclipse.persistence.logging.sequencing
 * <li>org.eclipse.persistence.logging.ejb
 * <li>org.eclipse.persistence.logging.ejb_or_metadata
 * <li>org.eclipse.persistence.logging.weaver
 * <li>org.eclipse.persistence.logging.properties
 * <li>org.eclipse.persistence.logging.server
 * </ul>
 * </p>
 * 
 * <p>
 *  The mapping between EclipseLink and SLF4J log levels is as follows: 
 * </p>
 * 
 * <ul>
 * <li>ALL,FINER,FINEST -> TRACE
 * <li>FINE -> DEBUG
 * <li>CONFIG,INFO -> INFO
 * <li>WARNING -> WARN
 * <li>SEVERE -> ERROR
 * </ul>
 * </p>
 * <p>
 * 
 * @author Miguel Angel Sosvilla Luis.
 * @author Igor Mukhin
 */
public class Slf4jSessionLogger extends AbstractSessionLog {

    public static final String ECLIPSELINK_NAMESPACE = "org.eclipse.persistence.logging";
    public static final String DEFAULT_CATEGORY = "default";

    public static final String DEFAULT_ECLIPSELINK_NAMESPACE = ECLIPSELINK_NAMESPACE + "." + DEFAULT_CATEGORY;

    private Map<Integer, LogLevel> mapLevels;
    private Map<String, Logger> categoryLoggers = new HashMap<String, Logger>();

    public Slf4jSessionLogger() {
        super();

        // set defaults to false
        setShouldDisplayData(true);
        setShouldPrintConnection(false);
        setShouldPrintDate(false);
        setShouldPrintSession(false);
        setShouldPrintThread(false);
        
        createCategoryLoggers();
        initMapLevels();
    }

    @Override
    public void log(SessionLogEntry entry) {
        if (!shouldLog(entry.getLevel(), entry.getNameSpace())) {
            return;
        }

        Logger logger = getLogger(entry.getNameSpace());
        LogLevel logLevel = getLogLevel(entry.getLevel());

        StringBuilder message = new StringBuilder();

        message.append(getSupplementDetailString(entry));
        message.append(formatMessage(entry));

        switch (logLevel) {
        case TRACE:
            logger.trace(message.toString());
            break;
        case DEBUG:
            logger.debug(message.toString());
            break;
        case INFO:
            logger.info(message.toString());
            break;
        case WARN:
            logger.warn(message.toString());
            break;
        case ERROR:
            logger.error(message.toString());
            break;
        case OFF:
            break;
        default:
            break;
        }
    }

    @Override
    public boolean shouldLog(int level, String category) {
        Logger logger = getLogger(category);
        boolean resp = false;

        LogLevel logLevel = getLogLevel(level);

        switch (logLevel) {
        case TRACE:
            resp = logger.isTraceEnabled();
            break;
        case DEBUG:
            resp = logger.isDebugEnabled();
            break;
        case INFO:
            resp = logger.isInfoEnabled();
            break;
        case WARN:
            resp = logger.isWarnEnabled();
            break;
        case ERROR:
            resp = logger.isErrorEnabled();
            break;
        case OFF:
            break;
        default:
            break;
        }

        return resp;
    }

    @Override
    public boolean shouldLog(int level) {
        return shouldLog(level, "default");
    }

    /**
     * Initialize loggers eagerly
     */
    private void createCategoryLoggers() {
        for (String category : SessionLog.loggerCatagories) {
            addLogger(category, ECLIPSELINK_NAMESPACE + "." + category);
        }
        
        // Logger default
        addLogger(DEFAULT_CATEGORY, DEFAULT_ECLIPSELINK_NAMESPACE);
    }

    /**
     * Add Logger to the categoryLoggers.
     */
    private void addLogger(String loggerCategory, String loggerNameSpace) {
        categoryLoggers.put(loggerCategory, LoggerFactory.getLogger(loggerNameSpace));
    }

    /**
     * Return the Logger for the given category
     */
    private Logger getLogger(String category) {
        if (!StringUtils.hasText(category) || !this.categoryLoggers.containsKey(category)) {
            category = DEFAULT_CATEGORY;
        }

        return categoryLoggers.get(category);

    }

    /**
     * Return the corresponding Slf4j Level for a given EclipseLink level.
     */
    private LogLevel getLogLevel(Integer level) {
        LogLevel logLevel = mapLevels.get(level);

        if (logLevel == null)
            logLevel = LogLevel.OFF;

        return logLevel;
    }

    /**
     * SLF4J log levels.
     */
    enum LogLevel {
        TRACE, DEBUG, INFO, WARN, ERROR, OFF
    }

    /**
     * Mapping between EclipseLink and SLF4J log levels.
     */
    private void initMapLevels() {
        mapLevels = new HashMap<Integer, LogLevel>();

        mapLevels.put(SessionLog.ALL, LogLevel.TRACE);
        mapLevels.put(SessionLog.FINEST, LogLevel.TRACE);
        mapLevels.put(SessionLog.FINER, LogLevel.TRACE);
        mapLevels.put(SessionLog.FINE, LogLevel.DEBUG);
        mapLevels.put(SessionLog.CONFIG, LogLevel.INFO);
        mapLevels.put(SessionLog.INFO, LogLevel.INFO);
        mapLevels.put(SessionLog.WARNING, LogLevel.WARN);
        mapLevels.put(SessionLog.SEVERE, LogLevel.ERROR);
    }

}