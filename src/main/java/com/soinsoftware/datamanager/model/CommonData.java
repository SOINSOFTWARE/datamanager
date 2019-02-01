// Soin Software, 2018
package com.soinsoftware.datamanager.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author Carlos Rodriguez
 * @since 27/11/2018
 */
@MappedSuperclass
public abstract class CommonData implements Serializable {

	private static final long serialVersionUID = 5530514093455467768L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private BigInteger id;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "creation_date", insertable = false)
	private Date creationDate;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modify_date", insertable = false)
	private Date modifyDate;
	private boolean archived;

	public CommonData() {
		super();
	}

	public CommonData(final BigInteger id, final Date creationDate, final Date modifyDate, final boolean archived) {
		super();
		this.id = id;
		this.creationDate = creationDate;
		this.modifyDate = modifyDate;
		this.archived = archived;
	}

	public BigInteger getId() {
		return id;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public boolean isArchived() {
		return archived;
	}

	public boolean isNew() {
		return (id == null);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		CommonData other = (CommonData) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

	public abstract void validate();
}