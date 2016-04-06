/**
 *
 */
package com.dataextractor.model;

import java.util.List;

public class JqGridData<T> {

    /** Total number of pages */
    private final int total;
    /** The current page number */
    private final int page;
    /** Total number of records */
    private final int records;
    /** The actual data */
    private final List<T> rows;

    public JqGridData(final int total, final int page, final int records,
            final List<T> rows) {
        this.total = total;
        this.page = page;
        this.records = records;
        this.rows = rows;
    }

    public int getPage() {
        return page;
    }

    public int getRecords() {
        return records;
    }

    public List<T> getRows() {
        return rows;
    }

    public int getTotal() {
        return total;
    }
}
