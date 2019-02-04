/** Soin Software, 2018 */
package com.soinsoftware.datamanager.model;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;
import org.hibernate.annotations.SelectBeforeUpdate;

/**
 * @author Carlos Rodriguez
 * @since 13/08/2018
 */
@Entity(name = "company")
@OptimisticLocking(type = OptimisticLockType.DIRTY)
@DynamicUpdate
@SelectBeforeUpdate
public class Company extends CommonData {

	private static final long serialVersionUID = -3794288197177180090L;
	
	@NaturalId
	private String document;
	private String name;
	private boolean paid;
	private String photo;
	@Column(name = "initialcustomid")
	private BigInteger initialCustomId;
	@Column(name = "actualcustomid")
	private BigInteger actualCustomId;
	private byte[] photoBlob;
	
	public Company() {
		super();
	}
	
	public Company(Builder builder) {
		super(builder.id, builder.creation, builder.enabled);
		this.document = builder.document;
		this.name = builder.name;
		this.paid = builder.paid;
		this.photo = builder.photo;
		this.initialCustomId = builder.initialCustomId;
		this.actualCustomId = builder.actualCustomId;
		this.photoBlob = builder.photoBlob;
	}
	
	public String getDocument() {
		return document;
	}

	public String getName() {
		return name;
	}

	public boolean isPaid() {
		return paid;
	}

	public String getPhoto() {
		return photo;
	}

	public BigInteger getInitialCustomId() {
		return initialCustomId;
	}

	public BigInteger getActualCustomId() {
		return actualCustomId;
	}
	
	public byte[] getPhotoBlob() {
        return photoBlob;
    }
	
	@Override
	public void validate() {
	}
	
	@Override
	public String getTitle() {
		return name;
	}
	
	public static Builder builder() {
		return new Builder();
	}
	
	public static Builder builder(Company company) {
		return new Builder(company);
	}

	public static class Builder {
		
		private BigInteger id;
		private String document;
		private String name;
		private boolean paid;
		private String photo;
		private BigInteger initialCustomId;
		private BigInteger actualCustomId;
		private Date creation;
		private boolean enabled;
		private byte[] photoBlob;
		
		private Builder() {}
		
		private Builder(Company company) {
			id(company.getId())
				.document(company.document)
				.name(company.name)
				.paid(company.paid)
				.photo(company.photo)
				.initialCustomId(company.initialCustomId)
				.actualCustomId(company.actualCustomId)
				.creation(company.getCreation())
				.enabled(company.isEnabled())
				.photoBlob(company.photoBlob);
		}
		public Builder id(BigInteger id) {
			this.id = id;
			return this;
		}

		public Builder document(String document) {
			this.document = document;
			return this;
		}

		public Builder name(String name) {
			this.name = name;
			return this;
		}

		public Builder paid(boolean paid) {
			this.paid = paid;
			return this;
		}

		public Builder photo(String photo) {
			this.photo = photo;
			return this;
		}

		public Builder initialCustomId(BigInteger initialCustomId) {
			this.initialCustomId = initialCustomId;
			return this;
		}

		public Builder actualCustomId(BigInteger actualCustomId) {
			this.actualCustomId = actualCustomId;
			return this;
		}

		public Builder creation(Date creation) {
			this.creation = creation;
			return this;
		}

		public Builder enabled(boolean enabled) {
			this.enabled = enabled;
			return this;
		}
		
		public Builder photoBlob(byte[] photoBlob) {
			this.photoBlob = photoBlob;
			return this;
		}
		
		public Company build() {
			return new Company(this);
		}
	}
}