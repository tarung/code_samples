import static com.sap.conn.jco.ext.DestinationDataProvider.JCO_ASHOST;
import static com.sap.conn.jco.ext.DestinationDataProvider.JCO_CLIENT;
import static com.sap.conn.jco.ext.DestinationDataProvider.JCO_LANG;
import static com.sap.conn.jco.ext.DestinationDataProvider.JCO_PASSWD;
import static com.sap.conn.jco.ext.DestinationDataProvider.JCO_PEAK_LIMIT;
import static com.sap.conn.jco.ext.DestinationDataProvider.JCO_POOL_CAPACITY;
import static com.sap.conn.jco.ext.DestinationDataProvider.JCO_SYSNR;
import static com.sap.conn.jco.ext.DestinationDataProvider.JCO_USER;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import com.sap.conn.jco.AbapException;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoField;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoStructure;
import com.sap.conn.jco.JCoTable;

public class Client {
    static String DESTINATION_NAME1 = "ABAP_AS_WITHOUT_POOL";
    static String DESTINATION_NAME2 = "ABAP_AS_WITH_POOL";
    static {
        final Properties connectProperties = new Properties();

        connectProperties.setProperty(JCO_ASHOST, "122.166.238.39");
        connectProperties.setProperty(JCO_SYSNR, "24");
        connectProperties.setProperty(JCO_CLIENT, "800");
        connectProperties.setProperty(JCO_USER, "USER05");
        connectProperties.setProperty(JCO_PASSWD, "india1234");
        connectProperties.setProperty(JCO_LANG, "en");
        createDestinationDataFile(DESTINATION_NAME1, connectProperties);
        connectProperties.setProperty(JCO_POOL_CAPACITY, "3");
        connectProperties.setProperty(JCO_PEAK_LIMIT, "10");
        createDestinationDataFile(DESTINATION_NAME2, connectProperties);

    }

    static void createDestinationDataFile(final String destinationName,
            final Properties connectProperties) {
        final File destCfg = new File(destinationName + ".jcoDestination");
        try {
            final FileOutputStream fos = new FileOutputStream(destCfg, false);
            connectProperties.store(fos, "for tests only !");
            fos.close();
        } catch (final Exception e) {
            throw new RuntimeException(
                    "Unable to create the destination files", e);
        }
    }

    public static void main(final String[] args) throws JCoException,
            IOException {
    	simpleCall();
    }

    public static void step1Connect() throws JCoException {
        final JCoDestination destination = JCoDestinationManager
                .getDestination(DESTINATION_NAME1);
        System.out.println("Attributes:");
        System.out.println(destination.getAttributes());
        System.out.println();
    }

    public static void step2ConnectUsingPool() throws JCoException {
        final JCoDestination destination = JCoDestinationManager
                .getDestination(DESTINATION_NAME2);
        destination.ping();
        System.out.println("Attributes:");
        System.out.println(destination.getAttributes());
        System.out.println();
    }

    public static void step3SimpleCall() throws JCoException {
        final JCoDestination destination = JCoDestinationManager
                .getDestination(DESTINATION_NAME2);
        final JCoFunction function = destination.getRepository().getFunction(
                "STFC_CONNECTION");
        if (function == null) {
            throw new RuntimeException("STFC_CONNECTION not found in SAP.");
        }
        function.getImportParameterList().setValue("REQUTEXT", "Hello SAP");

        try {
            function.execute(destination);
        } catch (final AbapException e) {
            System.out.println(e.toString());
            return;
        }

        System.out.println("STFC_CONNECTION finished:");
        System.out.println(" Echo: "
                + function.getExportParameterList().getString("ECHOTEXT"));
        System.out.println(" Response: "
                + function.getExportParameterList().getString("RESPTEXT"));
        System.out.println();
    }


    public static void simpleCall() throws JCoException {
        final JCoDestination destination = JCoDestinationManager
                .getDestination(DESTINATION_NAME2);
        final JCoFunction function = destination.getRepository().getFunction(
                "RFC_GET_TABLE_ENTRIES");
        if (function == null) {
            throw new RuntimeException("RFC_GET_TABLE_ENTRIES not found in SAP.");
        }
        function.getImportParameterList().setValue("TABLE_NAME", "AGR_TEXTS");
        function.getImportParameterList().setValue("MAX_ENTRIES", "0");
        function.getImportParameterList().setValue("BYPASS_BUFFER", "X");

        try {
            function.execute(destination);
        } catch (final AbapException e) {
            System.out.println(e.toString());
            return;
        }

        System.out.println("RFC_GET_TABLE_ENTRIES finished:");
        System.out.println(" Entries: "
                + function.getExportParameterList().getString("NUMBER_OF_ENTRIES"));
    }


    public static void step3WorkWithStructure() throws JCoException {
        final JCoDestination destination = JCoDestinationManager
                .getDestination(DESTINATION_NAME2);
        final JCoFunction function = destination.getRepository().getFunction(
                "RFC_SYSTEM_INFO");
        if (function == null) {
            throw new RuntimeException(
                    "BAPI_COMPANYCODE_GETLIST not found in SAP.");
        }
        try {
            function.execute(destination);
        } catch (final AbapException e) {
            System.out.println(e.toString());
            return;
        }

        final JCoStructure exportStructure = function.getExportParameterList()
                .getStructure("RFCSI_EXPORT");
        System.out.println("System info for "
                + destination.getAttributes().getSystemID() + ":\n");
        for (int i = 0; i < exportStructure.getMetaData().getFieldCount(); i++) {
            System.out.println(exportStructure.getMetaData().getName(i) + ":\t"
                    + exportStructure.getString(i));
        }
        System.out.println();

        // JCo still supports the JCoFields, but direct access via getXX is more
        // efficient as field iterator
        System.out.println("The same using field iterator: \nSystem info for "
                + destination.getAttributes().getSystemID() + ":\n");
        for (final JCoField field : exportStructure) {
            System.out.println(field.getName() + ":\t" + field.getString());
        }
        System.out.println();
    }

    public static void step4WorkWithTable() throws JCoException {
        final JCoDestination destination = JCoDestinationManager
                .getDestination(DESTINATION_NAME2);
        JCoFunction function = destination.getRepository().getFunction(
                "BAPI_COMPANYCODE_GETLIST");
        if (function == null) {
            throw new RuntimeException(
                    "BAPI_COMPANYCODE_GETLIST not found in SAP.");
        }
        try {
            function.execute(destination);
        } catch (final AbapException e) {
            System.out.println(e.toString());
            return;
        }

        JCoStructure returnStructure = function.getExportParameterList()
                .getStructure("RETURN");

        if (!(returnStructure.getString("TYPE").equals("") || returnStructure
                .getString("TYPE").equals("S"))) {
            throw new RuntimeException(returnStructure.getString("MESSAGE"));
        }

        final JCoTable codes = function.getTableParameterList().getTable(
                "COMPANYCODE_LIST");

        for (int i = 0; i < codes.getNumRows(); i++) {
            codes.setRow(i);
            System.out.println(codes.getString("COMP_CODE") + '\t'
                    + codes.getString("COMP_NAME"));
        }
        codes.firstRow();
        for (int i = 0; i < codes.getNumRows(); i++, codes.nextRow()) {
            function = destination.getRepository().getFunction(
                    "BAPI_COMPANYCODE_GETDETAIL");
            if (function == null) {
                throw new RuntimeException(
                        "BAPI_COMPANYCODE_GETDETAIL not found in SAP.");
            }
            function.getImportParameterList().setValue("COMPANYCODEID",
                    codes.getString("COMP_CODE"));
            function.getExportParameterList().setActive("COMPANYCODE_ADDRESS",
                    false);

            try {
                function.execute(destination);
            } catch (final AbapException e) {
                System.out.println(e.toString());
                return;
            }
            returnStructure = function.getExportParameterList().getStructure(
                    "RETURN");
            if (!(returnStructure.getString("TYPE").equals("")
                    || returnStructure.getString("TYPE").equals("S") || returnStructure
                    .getString("TYPE").equals("W"))) {
                throw new RuntimeException(returnStructure.getString("MESSAGE"));
            }

            final JCoStructure detail = function.getExportParameterList()
                    .getStructure("COMPANYCODE_DETAIL");

            System.out.println(detail.getString("COMP_CODE") + '\t'
                    + detail.getString("COUNTRY") + '\t'
                    + detail.getString("CITY"));
        }
    }

    public static void step5WorkWithTable() throws JCoException, IOException {
        System.out.println(new Date());

        final JCoDestination destination = JCoDestinationManager
                .getDestination(DESTINATION_NAME2);
        final JCoFunction function = destination.getRepository().getFunction(
                "RFC_READ_TABLE");
        if (function == null) {
            throw new RuntimeException("BAPI RFC_READ_TABLE not found in SAP.");
        }

        function.getImportParameterList().setValue("QUERY_TABLE", "DD03L");
        function.getImportParameterList().setValue("DELIMITER", ",");
        function.getImportParameterList().setValue("ROWCOUNT", "2");
        function.getImportParameterList().setValue("ROWSKIPS", "2");

        final JCoTable table = function.getTableParameterList().getTable(
                "FIELDS");


        table.appendRow();
        table.setValue("FIELDNAME", "FIELDNAME");
        table.appendRow();
        table.setValue("FIELDNAME", "DATATYPE");
        table.appendRow();
        table.setValue("FIELDNAME", "LENG");


        final JCoTable options = function.getTableParameterList().getTable(
                "OPTIONS");
        options.appendRow();
        options.setValue("TEXT", "TABNAME = 'USR07'");


        try {
            function.execute(destination);
        } catch (final AbapException e) {
            System.out.println(e.toString());
            return;
        }

        final JCoTable codes = function.getTableParameterList().getTable(
                "FIELDS");
        String header = "SN";
        for (int i = 0; i < codes.getNumRows(); i++) {
            codes.setRow(i);
            header += "," + codes.getString("FIELDNAME");
        }

        System.out.println(header);
        // FileWriter outFile = new FileWriter("out.csv");
        // outFile.write(header + "\n");
        final JCoTable rows = function.getTableParameterList().getTable("DATA");

        // System.out.println("" + rows.getNumRows());
        for (int i = 0; i < rows.getNumRows(); i++) {
            rows.setRow(i);
            System.out.println(rows.getString("WA"));
            // outFile.write(i + "," + rows.getString("WA") + "\n");
            // outFile.flush();
        }
        // outFile.close();
        System.out.println(new Date());

    }

    public static void step6WorkWithTable() throws JCoException, IOException {

        final JCoDestination destination = JCoDestinationManager
                .getDestination(DESTINATION_NAME2);
        final JCoFunction function = destination.getRepository().getFunction(
                "RFC_READ_TABLE");

        if (function == null) {
            throw new RuntimeException("BAPI RFC_READ_TABLE not found in SAP.");
        }

        JCoParameterList importParameterList = function.getImportParameterList();
		importParameterList.setValue("QUERY_TABLE", "T2702");
        importParameterList.setValue("DELIMITER", ",");
        importParameterList.setValue("NO_DATA", "Yes");

       /* final JCoTable table = function.getTableParameterList().getTable(
                "FIELDS");
        table.appendRow();
        table.setValue("FIELDNAME", "%1");*/
/*
        final JCoTable options = function.getTableParameterList().getTable("OPTIONS");
        options.appendRow();
        options.setValue("TEXT", "ADRNR = '1' AND BAPOVAR = '2' AND BUKRS = '3' OR BUKRS_GLOB = '4'");
*/
        try {
            function.execute(destination);
        } catch (final AbapException e) {
            System.out.println(e.toString());
            return;
        }

        final JCoTable codes = function.getTableParameterList().getTable(
                "FIELDS");

        String header = "";

        for (int i = 0; i < codes.getNumRows(); i++) {
            codes.setRow(i);
            header += codes.getString("FIELDNAME") + ",";
        }

        System.out.println(header);

        final JCoTable rows = function.getTableParameterList().getTable("DATA");
        System.out.println("number of rows >>" +  rows.getNumRows());
/*        for (int i = 0; i < rows.getNumRows(); i++) {
            rows.setRow(i);
            System.out.println(rows.getString("WA"));
        }
        System.out.println(new Date());
*/
    }
}