package com.dataextractor.gen.dto;

import java.io.Serializable;
import java.util.HashMap;

import com.google.common.collect.ImmutableMap;

@SuppressWarnings("all")
public class DbConnection implements Serializable {

    /** This attribute maps to the column id in the db_connection table. */
    protected Long id;

    /** This attribute maps to the column name in the db_connection table. */
    protected String name;

    /** This attribute maps to the column description in the db_connection table. */
    protected String description;

    /** This attribute maps to the column driverClassName in the db_connection table. */
    protected String driverClassName;

    /** This attribute maps to the column url in the db_connection table. */
    protected String url;

    /** This attribute maps to the column userName in the db_connection table. */
    protected String userName;

    /** This attribute maps to the column password in the db_connection table. */
    protected String password;

    /** The field to column map. */
    public static final ImmutableMap<String, String> fieldMap;

    static {
        final HashMap<String, String> mp = new HashMap<String, String>();
        mp.put("id", "id");
        mp.put("driverClassName", "driverClassName");
        mp.put("url", "url");
        mp.put("userName", "userName");
        mp.put("password", "password");
        fieldMap = ImmutableMap.copyOf(mp);
    }

    /**
     * Method 'createPk'
     *
     * @return DbConnectionPk
     */
    public DbConnectionPk createPk() {
        return new DbConnectionPk(id);
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DbConnection other = (DbConnection) obj;
        if (description == null) {
            if (other.description != null) {
                return false;
            }
        } else if (!description.equals(other.description)) {
            return false;
        }
        if (driverClassName == null) {
            if (other.driverClassName != null) {
                return false;
            }
        } else if (!driverClassName.equals(other.driverClassName)) {
            return false;
        }
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        if (password == null) {
            if (other.password != null) {
                return false;
            }
        } else if (!password.equals(other.password)) {
            return false;
        }
        if (url == null) {
            if (other.url != null) {
                return false;
            }
        } else if (!url.equals(other.url)) {
            return false;
        }
        if (userName == null) {
            if (other.userName != null) {
                return false;
            }
        } else if (!userName.equals(other.userName)) {
            return false;
        }
        return true;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Method 'getDriverClassName'
     *
     * @return String
     */
    public String getDriverClassName() {
        return driverClassName;
    }

    /**
     * Method 'getId'
     *
     * @return Long
     */
    public Long getId() {
        return id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Method 'getPassword'
     *
     * @return String
     */
    public String getPassword() {
        return password;
    }

    /**
     * Method 'getUrl'
     *
     * @return String
     */
    public String getUrl() {
        return url;
    }

    /**
     * Method 'getUserName'
     *
     * @return String
     */
    public String getUserName() {
        return userName;
    }

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + (description == null ? 0 : description.hashCode());
        result = prime * result
                + (driverClassName == null ? 0 : driverClassName.hashCode());
        result = prime * result + (id == null ? 0 : id.hashCode());
        result = prime * result + (name == null ? 0 : name.hashCode());
        result = prime * result + (password == null ? 0 : password.hashCode());
        result = prime * result + (url == null ? 0 : url.hashCode());
        result = prime * result + (userName == null ? 0 : userName.hashCode());
        return result;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     * Method 'setDriverClassName'
     *
     * @param driverClassName
     */
    public void setDriverClassName(final String driverClassName) {
        this.driverClassName = driverClassName;
    }

    /**
     * Method 'setId'
     *
     * @param id
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * @param name the name to set
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Method 'setPassword'
     *
     * @param password
     */
    public void setPassword(final String password) {
        this.password = password;
    }

    /**
     * Method 'setUrl'
     *
     * @param url
     */
    public void setUrl(final String url) {
        this.url = url;
    }

    /**
     * Method 'setUserName'
     *
     * @param userName
     */
    public void setUserName(final String userName) {
        this.userName = userName;
    }

    /**
     * Method 'toString'
     *
     * @return String
     */
    public String toString() {
        final StringBuffer ret = new StringBuffer();
        ret.append("com.dataextractor.gen.dto.DbConnection: ");
        ret.append("id=" + id);
        ret.append(", name=" + name);
        ret.append(", description=" + description);
        ret.append(", driverClassName=" + driverClassName);
        ret.append(", url=" + url);
        ret.append(", userName=" + userName);
        ret.append(", password=" + password);
        return ret.toString();
    }

}
