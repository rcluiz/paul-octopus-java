package com.ciandt.paul.dao;

public class DataNotAvailableException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = -5175932398197355385L;

	/**
     * Constructor
     */
    public DataNotAvailableException(String dataType, Integer year) {
        super("Data [" + dataType + "] not available for " + year);
    }
}
