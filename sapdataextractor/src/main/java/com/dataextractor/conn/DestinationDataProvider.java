/**
 *
 */
package com.dataextractor.conn;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.dataextractor.gen.dao.SapSystemDao;
import com.dataextractor.gen.dto.SapSystem;
import com.dataextractor.gen.exceptions.DaoException;
import com.sap.conn.jco.ext.DestinationDataEventListener;

public class DestinationDataProvider implements
        com.sap.conn.jco.ext.DestinationDataProvider {

    /** The temp map. */
    private Map<String, SapSystem> tempMap = Collections.synchronizedMap(new HashMap<String, SapSystem>());

    /** The instance. */
    private static DestinationDataProvider instance = null;

    /** The sap system dao. */
    private final SapSystemDao sapSystemDao;

    /** The event listener. */
    private DestinationDataEventListener eventListener;

    /**
     * Instantiates a new destination data provider.
     *
     * @param sapSystem the sap system
     */
    private DestinationDataProvider(final SapSystemDao sapSystem) {
    	sapSystemDao = sapSystem;
    }

    /**
     * Gets the single instance of DestinationDataProvider.
     *
     * @param sapSystem the sap system
     * @return single instance of DestinationDataProvider
     */
    public static DestinationDataProvider getInstance(final SapSystemDao sapSystem){
    	if(instance == null){
    		instance = new DestinationDataProvider(sapSystem);
    	}
    	return instance;
    }

    /**
     * Adds a temp destination.
     *
     * @param sapSystem the sap system
     */
    public void addTempDestination(SapSystem sapSystem){
    	String destinationName = sapSystem.getDestinationName();
		if(tempMap.containsKey(destinationName))
    		eventListener.updated(destinationName);
    	tempMap.put(destinationName, sapSystem);
    }

    /**
     * Removes the temp destination.
     *
     * @param destinationName the destination name
     */
    public void removeTempDestination(String destinationName){
    	if(tempMap.containsKey(destinationName))
    		tempMap.remove(destinationName);
    	eventListener.deleted(destinationName);
    }

    /**
     * Updated destination.
     *
     * @param destinationName the destination name
     */
    public void updatedDestination(String destinationName){
    	eventListener.deleted(destinationName);
    }


    /* (non-Javadoc)
     * @see com.sap.conn.jco.ext.DestinationDataProvider#getDestinationProperties(java.lang.String)
     */
    @Override
    public Properties getDestinationProperties(final String destinationName) {

    	SapSystem sap = tempMap.get(destinationName);
    	if(sap != null){
    		return sap.getProps();
    	}
    	List<SapSystem> dests;
        try {
            dests = sapSystemDao.findAll();
            for (final SapSystem dest : dests) {
            	tempMap.put(dest.getDestinationName(), dest);
                if (dest.getDestinationName().equals(destinationName)) {
                    return dest.getProps();
                }
            }
        } catch (final DaoException e) {
            throw new RuntimeException("JCo destination not found: " + e.getMessage(), e);
        }
        throw new RuntimeException("JCo destination not found: " + destinationName);
    }

    /* (non-Javadoc)
     * @see com.sap.conn.jco.ext.DestinationDataProvider#setDestinationDataEventListener(com.sap.conn.jco.ext.DestinationDataEventListener)
     */
    @Override
    public void setDestinationDataEventListener(final DestinationDataEventListener eventListener) {
    	this.eventListener = eventListener;
    }

    /* (non-Javadoc)
     * @see com.sap.conn.jco.ext.DestinationDataProvider#supportsEvents()
     */
    @Override
    public boolean supportsEvents() {
        return true;
    }

}
