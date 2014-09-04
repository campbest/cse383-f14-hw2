import java.text.SimpleDateFormat;

import java.util.Date;

import java.util.logging.Formatter;

import java.util.logging.Handler;

import java.util.logging.Level;

import java.util.logging.LogRecord;

/*
Scott campbell

cse383 - f14
Homework 2- simple AWS Dynamo DB server

aLOG Formatter - create format for log
adapted from http://www.vogella.com/tutorials/Logging/article.html
*/


// this custom formatter formats parts of a log record to a single line

class LoggerFormatter extends Formatter {

	// this method is called for every log records

	public String format(LogRecord rec) {

		StringBuffer buf = new StringBuffer(1000);

		buf.append(calcDate(rec.getMillis()));
		buf.append(" ");
		buf.append(rec.getLevel());

		buf.append(" ");

		buf.append(formatMessage(rec));
		buf.append("\n");

		return buf.toString();

	}



	private String calcDate(long millisecs) {

		SimpleDateFormat date_format = new SimpleDateFormat("MMM dd,yyyy HH:mm");

		Date resultdate = new Date(millisecs);

		return date_format.format(resultdate);

	}



} 
