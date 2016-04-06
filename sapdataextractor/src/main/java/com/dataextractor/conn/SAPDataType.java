package com.dataextractor.conn;

import static com.dataextractor.util.StaticUtil.checkDoubleFilter;
import static com.dataextractor.util.StaticUtil.checkFloatFilter;
import static com.dataextractor.util.StaticUtil.checkIntFilter;
import static com.dataextractor.util.StaticUtil.checkStringFilter;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.dataextractor.gen.dto.ProfileTableField;
import com.dataextractor.gen.dto.ProfileTableFieldFilter;

public enum SAPDataType {


	ACCP {

		@Override
		public String toSQLColumn(ProfileTableField field) {
			return "VARCHAR(10)";
		}

		@Override
		public void updateSQLStatement(PreparedStatement pstmt, int index,
				String value) throws SQLException {
			pstmt.setString(index, value);
		}

		@Override
		public String isValidFilter(ProfileTableFieldFilter filter) {
			return checkStringFilter(filter);
		}

	},

	CHAR {

		@Override
		public String toSQLColumn(ProfileTableField field) {
			return "VARCHAR("+ field.getFieldLength() + ")" ;
		}

		@Override
		public void updateSQLStatement(PreparedStatement pstmt, int index,
				String value) throws SQLException  {
			pstmt.setString(index, value);
		}

		@Override
		public String isValidFilter(ProfileTableFieldFilter filter) {
			return checkStringFilter(filter);
		}

	},

	CLNT {
		@Override
		public String toSQLColumn(ProfileTableField field) {
			return "SMALLINT(3)";
		}

		@Override
		public void updateSQLStatement(PreparedStatement pstmt, int index, String value)
				throws SQLException {
			if(value != null && !value.trim().isEmpty())
				pstmt.setInt(index, Integer.parseInt(value));
			else
				pstmt.setNull(index, java.sql.Types.INTEGER);
		}

		@Override
		public String isValidFilter(ProfileTableFieldFilter filter) {
			return checkIntFilter(filter);
		}
	},

	CUKY {
		@Override
		public String toSQLColumn(ProfileTableField field) {
			return "VARCHAR(5)";
		}

		@Override
		public void updateSQLStatement(PreparedStatement pstmt, int index,
				String value) throws SQLException  {
			pstmt.setString(index, value);
		}

		@Override
		public String isValidFilter(ProfileTableFieldFilter filter) {
			return checkStringFilter(filter);
		}
	},

	CURR {
		@Override
		public String toSQLColumn(ProfileTableField field) {
			return "VARCHAR(20)";
		}

		@Override
		public void updateSQLStatement(PreparedStatement pstmt, int index,
				String value) throws SQLException  {
			pstmt.setString(index, value);
		}

		@Override
		public String isValidFilter(ProfileTableFieldFilter filter) {
			return checkStringFilter(filter);
		}
	},

	DF16_DEC {
		@Override
		public String toSQLColumn(ProfileTableField field) {
			return "DOUBLE("+ field.getFieldLength() + ", " + field.getDecimals() + ")";
		}

		@Override
		public void updateSQLStatement(PreparedStatement pstmt, int index,
				String value)  throws SQLException {
			if(value != null && !value.trim().isEmpty())
				pstmt.setDouble(index, Double.parseDouble(value));
			else
				pstmt.setNull(index, java.sql.Types.DOUBLE);
		}

		@Override
		public String isValidFilter(ProfileTableFieldFilter filter) {
			return checkDoubleFilter(filter);
		}
	},

	DF16_RAW {
		@Override
		public String toSQLColumn(ProfileTableField field) {
			return "DOUBLE("+ field.getFieldLength() + ", " + field.getDecimals() + ")";
		}

		@Override
		public void updateSQLStatement(PreparedStatement pstmt, int index,
				String value)  throws SQLException {
			if(value != null && !value.trim().isEmpty())
				pstmt.setDouble(index, Double.parseDouble(value));
			else
				pstmt.setNull(index, java.sql.Types.DOUBLE);
		}

		@Override
		public String isValidFilter(ProfileTableFieldFilter filter) {
			return checkDoubleFilter(filter);
		}
	},

	DF16_SCL {
		@Override
		public String toSQLColumn(ProfileTableField field) {
			return "DOUBLE("+ field.getFieldLength() + ", " + field.getDecimals() + ")";
		}

		@Override
		public void updateSQLStatement(PreparedStatement pstmt, int index,
				String value)  throws SQLException {
			if(value != null && !value.trim().isEmpty())
				pstmt.setDouble(index, Double.parseDouble(value));
			else
				pstmt.setNull(index, java.sql.Types.DOUBLE);
		}

		@Override
		public String isValidFilter(ProfileTableFieldFilter filter) {
			return checkDoubleFilter(filter);
		}
	},

	DF34_DEC {
		@Override
		public String toSQLColumn(ProfileTableField field) {
			return "DOUBLE("+ field.getFieldLength() + ", " + field.getDecimals() + ")";
		}

		@Override
		public void updateSQLStatement(PreparedStatement pstmt, int index,
				String value)  throws SQLException {
			if(value != null && !value.trim().isEmpty())
				pstmt.setDouble(index, Double.parseDouble(value));
			else
				pstmt.setNull(index, java.sql.Types.DOUBLE);
		}

		@Override
		public String isValidFilter(ProfileTableFieldFilter filter) {
			return checkDoubleFilter(filter);
		}
	},

	DF34_RAW {

		@Override
		public String toSQLColumn(ProfileTableField field) {
			return "DOUBLE("+ field.getFieldLength() + ", " + field.getDecimals() + ")";
		}

		@Override
		public void updateSQLStatement(PreparedStatement pstmt, int index,
				String value)  throws SQLException {
			if(value != null && !value.trim().isEmpty())
				pstmt.setDouble(index,  Double.parseDouble(value));
			else
				pstmt.setNull(index, java.sql.Types.DOUBLE);
		}

		@Override
		public String isValidFilter(ProfileTableFieldFilter filter) {
			return checkDoubleFilter(filter);
		}
	},

	DF34_SCL {

		@Override
		public String toSQLColumn(ProfileTableField field) {
			return "DOUBLE("+ field.getFieldLength() + ", " + field.getDecimals() + ")";
		}

		@Override
		public void updateSQLStatement(PreparedStatement pstmt, int index,
				String value)  throws SQLException {
			if(value != null && !value.trim().isEmpty())
				pstmt.setDouble(index, Double.parseDouble(value));
			else
				pstmt.setNull(index, java.sql.Types.DOUBLE);
		}

		@Override
		public String isValidFilter(ProfileTableFieldFilter filter) {
			return checkDoubleFilter(filter);
		}
	},

	DATS {

		@Override
		public String toSQLColumn(ProfileTableField field) {
			return "DATE";
		}
		final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");

		@Override
		public void updateSQLStatement(PreparedStatement pstmt, int index,
				String value)  throws SQLException {
			try {
				if(value != null && !value.trim().isEmpty())
					pstmt.setDate(index, new java.sql.Date(DATE_FORMAT.parse(value).getTime()));
				else
					pstmt.setNull(index, java.sql.Types.DATE);
			} catch (ParseException e) {
				throw new IllegalStateException(e.getMessage(), e);
			}
		}

		@Override
		public String isValidFilter(ProfileTableFieldFilter filter) {

			String value = filter.getCriteria();
			if(value == null || value.trim().isEmpty()){
				return "Filter criteria is null or empty.";
			}
			try {
				DATE_FORMAT.parse(value);
			} catch (ParseException e) {
				return "Filter criteria is not a valid date format : yyyyMMdd";
			}
			return null;
		}
	},

	DEC {

		@Override
		public String toSQLColumn(ProfileTableField field) {
			return "VARCHAR(32)";
		}

		@Override
		public void updateSQLStatement(PreparedStatement pstmt, int index,
				String value)  throws SQLException {
			pstmt.setString(index, value);
		}

		@Override
		public String isValidFilter(ProfileTableFieldFilter filter) {
			return checkStringFilter(filter);
		}
	},

	FLTP {

		@Override
		public String toSQLColumn(ProfileTableField field) {
			return "FLOAT("+ field.getFieldLength() + ", " + field.getDecimals() + ")";
		}

		@Override
		public void updateSQLStatement(PreparedStatement pstmt, int index,
				String value)  throws SQLException {
			if(value != null && !value.trim().isEmpty())
				pstmt.setFloat(index, Float.parseFloat(value));
			else
				pstmt.setNull(index, java.sql.Types.FLOAT);
		}

		@Override
		public String isValidFilter(ProfileTableFieldFilter filter) {
			return checkFloatFilter(filter);
		}

	},

	INT1 {

		@Override
		public String toSQLColumn(ProfileTableField field) {
			return "INT("+ field.getFieldLength()+ ")" ;
		}

		@Override
		public void updateSQLStatement(PreparedStatement pstmt, int index,
				String value)  throws SQLException {
			pstmt.setInt(index, Integer.valueOf(value));
		}

		@Override
		public String isValidFilter(ProfileTableFieldFilter filter) {
			return checkIntFilter(filter);
		}
	},

	INT2 {
		@Override
		public String toSQLColumn(ProfileTableField field) {
			return "INT("+ field.getFieldLength()+ ")" ;
		}

		@Override
		public void updateSQLStatement(PreparedStatement pstmt, int index,
				String value)  throws SQLException {
			pstmt.setInt(index, Integer.valueOf(value));
		}

		@Override
		public String isValidFilter(ProfileTableFieldFilter filter) {
			return checkIntFilter(filter);
		}
	},

	INT4 {
		@Override
		public String toSQLColumn(ProfileTableField field) {
			return "INT("+ field.getFieldLength()+ ")" ;
		}

		@Override
		public void updateSQLStatement(PreparedStatement pstmt, int index,
				String value)  throws SQLException {
			pstmt.setInt(index, Integer.valueOf(value));
		}

		@Override
		public String isValidFilter(ProfileTableFieldFilter filter) {
			return checkIntFilter(filter);
		}
	},

	LANG {
		@Override
		public String toSQLColumn(ProfileTableField field) {
			return "CHAR(1)";
		}

		@Override
		public void updateSQLStatement(PreparedStatement pstmt, int index,
				String value)  throws SQLException {
			pstmt.setString(index, value);
		}

		@Override
		public String isValidFilter(ProfileTableFieldFilter filter) {
			return checkStringFilter(filter);
		}
	},

	LCHR {
		@Override
		public String toSQLColumn(ProfileTableField field) {
			return "TEXT";
		}

		@Override
		public void updateSQLStatement(PreparedStatement pstmt, int index,
				String value)  throws SQLException  {
			pstmt.setString(index, value);
		}

		@Override
		public String isValidFilter(ProfileTableFieldFilter filter) {
			return checkStringFilter(filter);
		}
	},

	LRAW {
		@Override
		public String toSQLColumn(ProfileTableField field) {
			return "BLOB";
		}

		@Override
		public void updateSQLStatement(PreparedStatement pstmt, int index,
				String value)  throws SQLException {
			pstmt.setBytes(index, value.getBytes());
		}

		@Override
		public String isValidFilter(ProfileTableFieldFilter filter) {
			return checkStringFilter(filter);
		}
	},

	NUMC {
		@Override
		public String toSQLColumn(ProfileTableField field) {
			return "INT("+ field.getFieldLength()+ ")" ;
		}

		@Override
		public void updateSQLStatement(PreparedStatement pstmt, int index,
				String value)  throws SQLException {
			if(value != null && !value.trim().isEmpty())
				pstmt.setInt(index, Integer.parseInt(value));
			else
				pstmt.setNull(index, java.sql.Types.INTEGER);
		}

		@Override
		public String isValidFilter(ProfileTableFieldFilter filter) {
			return checkIntFilter(filter);
		}
	},

	PREC {
		@Override
		public String toSQLColumn(ProfileTableField field) {
			return "INT(16)" ;
		}

		@Override
		public void updateSQLStatement(PreparedStatement pstmt, int index,
				String value)  throws SQLException {
			if(value != null && !value.trim().isEmpty())
				pstmt.setInt(index, Integer.parseInt(value));
			else
				pstmt.setNull(index, java.sql.Types.INTEGER);
		}

		@Override
		public String isValidFilter(ProfileTableFieldFilter filter) {
			return checkIntFilter(filter);
		}
	},
	QUAN {
		@Override
		public String toSQLColumn(ProfileTableField field) {
			return "DOUBLE("+ field.getFieldLength() + ", " + field.getDecimals() + ")";
		}

		@Override
		public void updateSQLStatement(PreparedStatement pstmt, int index,
				String value)  throws SQLException {
			if(value != null && !value.trim().isEmpty())
				pstmt.setDouble(index, Double.parseDouble(value));
			else
				pstmt.setNull(index, java.sql.Types.DOUBLE);
		}

		@Override
		public String isValidFilter(ProfileTableFieldFilter filter) {
			return checkDoubleFilter(filter);
		}
	},

	RAW {

		@Override
		public String toSQLColumn(ProfileTableField field) {
			return "BLOB";
		}

		@Override
		public void updateSQLStatement(PreparedStatement pstmt, int index,
				String value)  throws SQLException {
			pstmt.setString(index, value);
		}

		@Override
		public String isValidFilter(ProfileTableFieldFilter filter) {
			return checkStringFilter(filter);
		}
	},

	RAWSTRING {

		@Override
		public String toSQLColumn(ProfileTableField field) {
			return "BLOB";
		}

		@Override
		public void updateSQLStatement(PreparedStatement pstmt, int index,
				String value)  throws SQLException {
			pstmt.setString(index, value);
		}

		@Override
		public String isValidFilter(ProfileTableFieldFilter filter) {
			return checkStringFilter(filter);
		}
	},

	SSTRING {
		@Override
		public String toSQLColumn(ProfileTableField field) {
			return "VARCHAR("+ field.getFieldLength() + ")" ;
		}

		@Override
		public void updateSQLStatement(PreparedStatement pstmt, int index,
				String value) throws SQLException  {
			pstmt.setString(index, value);
		}

		@Override
		public String isValidFilter(ProfileTableFieldFilter filter) {
			return checkStringFilter(filter);
		}
	},

	STRING {
		@Override
		public String toSQLColumn(ProfileTableField field) {
			return "VARCHAR("+ field.getFieldLength() + ")" ;
		}

		@Override
		public void updateSQLStatement(PreparedStatement pstmt, int index,
				String value)  throws SQLException {
			pstmt.setString(index, value);
		}

		@Override
		public String isValidFilter(ProfileTableFieldFilter filter) {
			return checkStringFilter(filter);
		}
	},

	TIMS {
		@Override
		public String toSQLColumn(ProfileTableField field) {
			return "TIME";
		}
		final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HHmmss");

		@Override
		public void updateSQLStatement(PreparedStatement pstmt, int index,
				String value)  throws SQLException {
			try {
				if(value != null && !value.trim().isEmpty())
					pstmt.setTime(index, new Time(TIME_FORMAT.parse(value).getTime()));
				else
					pstmt.setNull(index, java.sql.Types.TIME);
			} catch (ParseException e) {
				throw new IllegalStateException(e.getMessage(), e);
			}
		}

		@Override
		public String isValidFilter(ProfileTableFieldFilter filter) {
			String value = filter.getCriteria();
			if(value == null || value.trim().isEmpty()){
				return "Filter criteria is null or empty.";
			}
			try {
				TIME_FORMAT.parse(value);
			} catch (ParseException e) {
				return "Filter criteria is not a valid time format :HHmmss";
			}
			return null;
		}
	},

	UNIT {
		@Override
		public String toSQLColumn(ProfileTableField field) {
			return "VARCHAR(10)";
		}

		@Override
		public void updateSQLStatement(PreparedStatement pstmt, int index,
				String value) throws SQLException  {
			pstmt.setString(index, value);
		}

		@Override
		public String isValidFilter(ProfileTableFieldFilter filter) {
			return checkStringFilter(filter);
		}
	},

	STRU {
		@Override
		public String toSQLColumn(ProfileTableField field) {
			return "VARCHAR(100)";
		}

		@Override
		public void updateSQLStatement(PreparedStatement pstmt, int index,
				String value) throws SQLException  {
			pstmt.setString(index, value);
		}

		@Override
		public String isValidFilter(ProfileTableFieldFilter filter) {
			return checkStringFilter(filter);
		}
	};


	public abstract String toSQLColumn(ProfileTableField field);

	public abstract String isValidFilter(ProfileTableFieldFilter filter);

	public abstract void updateSQLStatement(PreparedStatement pstmt, int index, String value) throws SQLException;

}
