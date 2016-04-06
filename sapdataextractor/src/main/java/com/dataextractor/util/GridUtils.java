/**
 *
 */
package com.dataextractor.util;

import java.util.ArrayList;
import java.util.List;

/** The Grid Utils. */
@SuppressWarnings("all")
public class GridUtils {

    /**
     * Data for page.
     *
     * @param <T> the generic type
     * @param dataSet the data set
     * @param pageNumber the page number
     * @param itemsPerPage the items per page
     * @return the list< t>
     */
    public static <T> List<T> dataForPage(final List<T> dataSet,
            final int pageNumber, final int itemsPerPage) {
        List<T> result = null;

        int fromIndex = 0;
        int sz = dataSet.size();
		if (pageNumber > 1) {
            fromIndex = (pageNumber - 1) * itemsPerPage;
        }
        else if(pageNumber == -1){
        	// last page.
        	if(sz >0 ){
        		int numberofPages = (int) Math.ceil(sz/itemsPerPage);
        		if(numberofPages > 1){
        			fromIndex = (numberofPages - 1) * itemsPerPage;
        		}
        	}
        }
        if (fromIndex >= sz) {
            result = new ArrayList<T>();
        }

        final int endIndex = fromIndex + itemsPerPage;
        if (endIndex > sz) {
            result = dataSet.subList(fromIndex, sz);
        } else {
            result = dataSet.subList(fromIndex, endIndex);
        }

        return result;
    }

    /**
     * Page number.
     *
     * @param dataSet the data set
     * @param pageNumber the page number
     * @param rowsPerPage the rows per page
     * @return the int
     */
    public static int pageNumber(final List<?> dataSet, int pageNumber,
            final int rowsPerPage) {
    	if(pageNumber == -1){
    		// last page.
    		totalNumberOfPages(dataSet,rowsPerPage);
    	}
        return pageNumber++;
    }

    /**
     * Total number of pages.
     *
     * @param dataSet the data set
     * @param rowsPerPage the rows per page
     * @return the int
     */
    public static int totalNumberOfPages(final List<?> dataSet,
            final int rowsPerPage) {
        final int totalNumberOfPages = (int) Math.ceil((double) dataSet.size()
                / rowsPerPage);
        return totalNumberOfPages;
    }
}
