package com.bj.remote;

import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.parser.ConfigurableFTPFileEntryParserImpl;
import org.apache.log4j.Logger;


/**
 * 指定文件过滤器，只适用于 unnix环境
 * @author wuqh for test
 *
 */
public class AcquireFTPEntryParser extends ConfigurableFTPFileEntryParserImpl {
	
	public static final Logger log = Logger.getLogger(AcquireFTPEntryParser.class);
	
	static final String DEFAULT_DATE_FORMAT
    = "MMM d yyyy"; //Nov 9 2001

	static final String DEFAULT_RECENT_DATE_FORMAT
    = "MMM d HH:mm"; //Nov 9 20:06

	static final String NUMERIC_DATE_FORMAT
    = "yyyy-MM-dd HH:mm"; //2001-11-09 20:06
	
	private static final String REGEX =
	        "([bcdelfmpSs-])"
	        +"(((r|-)(w|-)([xsStTL-]))((r|-)(w|-)([xsStTL-]))((r|-)(w|-)([xsStTL-])))\\+?\\s*"
	        + "(\\d+)\\s+"                                  // link count
	        + "(?:(\\S+(?:\\s\\S+)*?)\\s+)?"                // owner name (optional spaces)
	        + "(?:(\\S+(?:\\s\\S+)*)\\s+)?"                 // group name (optional spaces)
	        + "(\\d+(?:,\\s*\\d+)?)\\s+"                    // size or n,m
	        /*
	         * numeric or standard format date:
	         *   yyyy-mm-dd (expecting hh:mm to follow)
	         *   MMM [d]d
	         *   [d]d MMM
	         *   N.B. use non-space for MMM to allow for languages such as German which use
	         *   diacritics (e.g. umlaut) in some abbreviations.
	        */
	        + "((?:\\d+[-/]\\d+[-/]\\d+)|(?:\\S{3}\\s+\\d{1,2})|(?:\\d{1,2}\\s+\\S{3}))\\s+"
	        /*
	           year (for non-recent standard format) - yyyy
	           or time (for numeric or recent standard format) [h]h:mm
	        */
	        + "(\\d+(?::\\d+)?)\\s+"

	        //+ "(\\S*)(\\s*.*)"
	        + "(\\S*)(\\s*.sql)"; // the rest

	
	public AcquireFTPEntryParser() {
		super(REGEX);
	}

	@Override
	public FTPFile parseFTPEntry(String listEntry) {
		
		FTPFile file = new FTPFile();
		String[] properties = listEntry.split("\\s+");
		if(matches(listEntry)){
			System.out.println(listEntry);
			return file;
		}
		if(properties.length !=9){
			return null;
		}

		System.out.println(properties[7]);
		
		return null;
	}

	@Override
	protected FTPClientConfig getDefaultConfiguration() {
		return new FTPClientConfig(
                FTPClientConfig.SYST_UNIX,
                DEFAULT_DATE_FORMAT,
                DEFAULT_RECENT_DATE_FORMAT,
                null, null, null);
		
	}
}
