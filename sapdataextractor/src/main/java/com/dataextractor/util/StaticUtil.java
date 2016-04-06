package com.dataextractor.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.dataextractor.gen.dao.ExtractionProfileDao;
import com.dataextractor.gen.dao.ProfileTableDao;
import com.dataextractor.gen.dao.ProfileTableFieldDao;
import com.dataextractor.gen.dao.ProfileTableFieldFilterDao;
import com.dataextractor.gen.dto.ExtractionProfile;
import com.dataextractor.gen.dto.ExtractionProfilePk;
import com.dataextractor.gen.dto.ProfileTable;
import com.dataextractor.gen.dto.ProfileTableField;
import com.dataextractor.gen.dto.ProfileTableFieldFilter;
import com.dataextractor.gen.exceptions.DaoException;
import com.dataextractor.model.ExtractionProfileVO;
import com.dataextractor.model.ProfileTableVO;

public class StaticUtil {


	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static ExtractionProfileVO loadProfileFromDB(final Long id,
			ExtractionProfileDao profileDao,
			ProfileTableDao tableDao,
			ProfileTableFieldDao fieldDao,
			ProfileTableFieldFilterDao filterDao)
			throws DaoException {

		final ExtractionProfileVO currentProfile;
		final ExtractionProfile profileDTO = profileDao.findByPrimaryKey(new ExtractionProfilePk(id));

		currentProfile = new ExtractionProfileVO(profileDTO);
		final List<ProfileTable> tables = tableDao.findDynamic("profile_id="+ id);

		if (tables != null && tables.size() > 0) {

			final List<ProfileTableVO> tablevos = new ArrayList<ProfileTableVO>(tables.size());

			for (final ProfileTable t : tables) {

				final ProfileTableVO profileTableVO = new ProfileTableVO(t);
				final List<ProfileTableField> flds = fieldDao.findDynamic("table_id=" + t.getId()
						+ " order by position asc");
				final List<ProfileTableFieldFilter> fltrs = new ArrayList<ProfileTableFieldFilter>();
				if (flds.size() > 0) {
					for (final ProfileTableField fld : flds) {
						fltrs.addAll(filterDao.findDynamic("field_id="+ fld.getId() + " order by id"));
					}
				}
				Set fieldSet = new TreeSet(new Comparator<ProfileTableField>(){
					@Override
					public int compare(ProfileTableField o1,
							ProfileTableField o2) {
						return new Integer(o1.getPosition()).compareTo(o2.getPosition());
					}
				});

				fieldSet.addAll(flds);
				profileTableVO.setFields(fieldSet);
				profileTableVO.setFilters(fltrs);
				tablevos.add(profileTableVO);

				filterDao.findDynamic("");
			}
			currentProfile.setTables(tablevos);
		}
		return currentProfile;
	}



	public static String checkStringFilter(ProfileTableFieldFilter filter) {
		if(filter.getCriteria() == null || filter.getCriteria().isEmpty())
			return "Filter criteria is null or empty.";
		else if("Between".equalsIgnoreCase(filter.getOperator()) ||
				">".equals(filter.getOperator())	||
				"<".equals(filter.getOperator())  ||
				">=".equals(filter.getOperator()) ||
				"<=".equals(filter.getOperator()))
			return "Filter operator Between, >, < etc. are not allowed for Non numeric fields.";
		else return null;
	}

	public static String checkIntFilter(ProfileTableFieldFilter filter) {
		if (filter.getCriteria() == null || filter.getCriteria().isEmpty()) {
			return "Filter criteria is null or empty.";
		}
		try {
			Integer.parseInt(filter.getCriteria());
		} catch (NumberFormatException nfe) {
			return "Filter criteria is not a valid Integer number.";
		}
		if (filter.getCriteria2() != null && !filter.getCriteria2().isEmpty()) {
			try {
				Integer.parseInt(filter.getCriteria2());
			} catch (NumberFormatException nfe) {
				return "Filter criteria2 is not a valid Integer number.";
			}
		}
		return null;
	}

	public static String checkFloatFilter(ProfileTableFieldFilter filter) {
		if(filter.getCriteria() == null || filter.getCriteria().isEmpty()){
			return "Filter criteria is null or empty.";
		}
		try {
			Float.parseFloat(filter.getCriteria());
		}catch(NumberFormatException nfe){
			return "Filter criteria is not a valid floating point number.";
		}
		if (filter.getCriteria2() != null && !filter.getCriteria2().isEmpty()) {
			try {
				Float.parseFloat(filter.getCriteria2());
			} catch (NumberFormatException nfe) {
				return "Filter criteria2 is not a valid Integer number.";
			}
		}
		return null;
	}


	public static String checkDoubleFilter(ProfileTableFieldFilter filter) {
		if(filter.getCriteria() == null || filter.getCriteria().isEmpty()){
			return "Filter criteria is null or empty.";
		}
		try {
			Double.parseDouble(filter.getCriteria());
		}catch(NumberFormatException nfe){
			return "Filter criteria is not a valid floating point number.";
		}
		if (filter.getCriteria2() != null && !filter.getCriteria2().isEmpty()) {
			try {
				Double.parseDouble(filter.getCriteria());
			} catch (NumberFormatException nfe) {
				return "Filter criteria2 is not a valid Integer number.";
			}
		}
		return null;
	}

}
