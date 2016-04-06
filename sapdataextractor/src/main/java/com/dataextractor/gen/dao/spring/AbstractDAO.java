package com.dataextractor.gen.dao.spring;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 * Generic Base class for DAO classes.
 *
 * This is a customizable template within FireStorm/DAO.
 */
@SuppressWarnings("all")
public class AbstractDAO {

    public byte[] getBlobColumn(final ResultSet rs, final int columnIndex)
            throws SQLException {
        try {
            final Blob blob = rs.getBlob(columnIndex);
            if (blob == null) {
                return null;
            }

            final InputStream is = blob.getBinaryStream();
            final ByteArrayOutputStream bos = new ByteArrayOutputStream();

            if (is == null) {
                return null;
            } else {
                final byte buffer[] = new byte[64];
                int c = is.read(buffer);
                while (c > 0) {
                    bos.write(buffer, 0, c);
                    c = is.read(buffer);
                }
                return bos.toByteArray();
            }
        } catch (final IOException e) {
            throw new SQLException(
                    "Failed to read BLOB column due to IOException: "
                            + e.getMessage());
        }
    }

    public String getClobColumn(final ResultSet rs, final int columnIndex)
            throws SQLException {
        try {
            final Clob clob = rs.getClob(columnIndex);
            if (clob == null) {
                return null;
            }

            final StringBuffer ret = new StringBuffer();
            final InputStream is = clob.getAsciiStream();

            if (is == null) {
                return null;
            } else {
                final byte buffer[] = new byte[64];
                int c = is.read(buffer);
                while (c > 0) {
                    ret.append(new String(buffer, 0, c));
                    c = is.read(buffer);
                }
                return ret.toString();
            }
        } catch (final IOException e) {
            throw new SQLException(
                    "Failed to read CLOB column due to IOException: "
                            + e.getMessage());
        }
    }

    /**
     * Sets the blob column.
     *
     * @param stmt the statement.
     * @param parameterIndex the parameter index
     * @param value the value
     * @throws SQLException the sQL exception
     */
    public void setBlobColumn(final PreparedStatement stmt,
            final int parameterIndex, final byte[] value) throws SQLException {
        if (value == null) {
            stmt.setNull(parameterIndex, Types.BLOB);
        } else {
            stmt.setBinaryStream(parameterIndex,
                    new ByteArrayInputStream(value), value.length);
        }
    }

    /**
     * Sets the CLOB column.
     *
     * @param stmt the statement
     * @param parameterIndex the parameter index
     * @param value the value
     * @throws SQLException the sQL exception
     */
    public void setClobColumn(final PreparedStatement stmt,
            final int parameterIndex, final String value) throws SQLException {
        if (value == null) {
            stmt.setNull(parameterIndex, Types.CLOB);
        } else {
            stmt.setAsciiStream(parameterIndex,
                    new ByteArrayInputStream(value.getBytes()), value.length());
        }
    }
}
