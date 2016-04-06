package com.dataextractor.gen.dto;

import static com.sap.conn.jco.ext.DestinationDataProvider.JCO_ASHOST;
import static com.sap.conn.jco.ext.DestinationDataProvider.JCO_CLIENT;
import static com.sap.conn.jco.ext.DestinationDataProvider.JCO_LANG;
import static com.sap.conn.jco.ext.DestinationDataProvider.JCO_PASSWD;
import static com.sap.conn.jco.ext.DestinationDataProvider.JCO_SYSNR;
import static com.sap.conn.jco.ext.DestinationDataProvider.JCO_USER;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Properties;

import com.google.common.collect.ImmutableMap;

/** The Sap System. */
@SuppressWarnings("all")
public class SapSystem implements Serializable {

    /** This attribute maps to the column id in the sap_system table. */
    protected Long id;

    /** This attribute maps to the column destination_name in the sap_system table. */
    protected String destinationName;

    /** This attribute maps to the column description in the sap_system table. */
    protected String description;

    /** This attribute maps to the column host_name in the sap_system table. */
    protected String hostName;

    /** This attribute maps to the column sys_nr in the sap_system table. */
    protected String sysNr;

    /** This attribute maps to the column user_name in the sap_system table. */
    protected String userName;

    /** This attribute maps to the column password in the sap_system table. */
    protected String password;

    /** This attribute maps to the column language_code in the sap_system table. */
    protected String languageCode;

    /** This attribute maps to the column is_pooled in the sap_system table. */
    protected boolean isPooled;

    /** This attribute maps to the column pool_capacity in the sap_system table. */
    protected Integer poolCapacity;

    /** This attribute maps to the column peak_limit in the sap_system table. */
    protected Integer peakLimit;

    /** The client number. */
    protected int clientNumber;

    /** The field to column map. */
    public static final ImmutableMap<String, String> fieldMap;

    static {

        final HashMap<String, String> mp = new HashMap<String, String>();
        mp.put("id", "id");
        mp.put("destinationName", "destination_name");
        mp.put("hostName", "host_name");
        mp.put("sysNr", "sys_nr");
        mp.put("userName", "user_name");
        mp.put("password", "password");
        mp.put("languageCode", "language_code");
        mp.put("isPooled", "is_pooled");
        mp.put("poolCapacity", "pool_capacity");
        mp.put("peakLimit", "peak_limit");
        mp.put("clientNumber", "client_number");
        fieldMap = ImmutableMap.copyOf(mp);
    }

    public SapSystemPk createPk() {
        return new SapSystemPk(id);
    }

    public String getColumn(final String field) {
        return fieldMap.get(field);
    }

    public String getDescription() {
        return description;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public String getHostName() {
        return hostName;
    }

    public Long getId() {
        return id;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public String getPassword() {
        return password;
    }

    public Integer getPeakLimit() {
        return peakLimit;
    }

    public Integer getPoolCapacity() {
        return poolCapacity;
    }

    public String getSysNr() {
        return sysNr;
    }

    public String getUserName() {
        return userName;
    }

    public int getClientNumber() {
		return clientNumber;
	}

	public void setClientNumber(int clientNumber) {
		this.clientNumber = clientNumber;
	}

    public boolean isIsPooled() {
        return isPooled;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setDestinationName(final String destinationName) {
        this.destinationName = destinationName;
    }

    public void setHostName(final String hostName) {
        this.hostName = hostName;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setIsPooled(final boolean isPooled) {
        this.isPooled = isPooled;
    }

    public void setLanguageCode(final String languageCode) {
        this.languageCode = languageCode;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public void setPeakLimit(final Integer peakLimit) {
        this.peakLimit = peakLimit;
    }

    public void setPoolCapacity(final Integer poolCapacity) {
        this.poolCapacity = poolCapacity;
    }

    public void setSysNr(final String sysNr) {
        this.sysNr = sysNr;
    }

    public void setUserName(final String userName) {
        this.userName = userName;
    }

    public Properties getProps() {
        final Properties props = new Properties();
        props.setProperty(JCO_ASHOST, hostName);
        props.setProperty(JCO_SYSNR, sysNr);
        props.setProperty(JCO_CLIENT, ""+clientNumber);
        props.setProperty(JCO_USER, userName);
        props.setProperty(JCO_PASSWD, password);
        props.setProperty(JCO_LANG, languageCode);
        return props;
    }
}
