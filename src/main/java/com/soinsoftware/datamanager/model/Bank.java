package com.soinsoftware.datamanager.model;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Entity;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;
import org.hibernate.annotations.SelectBeforeUpdate;

/**
* @author Carlos Rodriguez
* @since 28/11/2018
*/
@Entity(name = "bank")
@OptimisticLocking(type = OptimisticLockType.DIRTY)
@DynamicUpdate
@SelectBeforeUpdate
public class Bank extends CommonData {

	private static final long serialVersionUID = 5807717858270495166L;

	@NaturalId
	private String name;

	public Bank() {
		super();
	}

	public Bank(Builder builder) {
		super(builder.id, builder.creationDate, builder.modifyDate, builder.archived);
		name = builder.name;
	}

	public String getName() {
		return name;
	}

	@Override
	public void validate() {
	}
		

	public static Builder builder() {
		return new Builder();
	}

	public static Builder builder(Bank person) {
		return new Builder(person);
	}

	public static class Builder {

		private BigInteger id;
		private Date creationDate;
		private Date modifyDate;
		private boolean archived;
		private String name;

		private Builder() {
		}

		private Builder(Bank person) {
			id(person.getId()).creationDate(person.getCreationDate()).modifyDate(person.getModifyDate())
					.archived(person.isArchived()).name(person.getName());
		}

		public Builder id(BigInteger id) {
			this.id = id;
			return this;
		}

		public Builder creationDate(Date creationDate) {
			this.creationDate = creationDate;
			return this;
		}

		public Builder modifyDate(Date modifyDate) {
			this.modifyDate = modifyDate;
			return this;
		}

		public Builder archived(boolean archived) {
			this.archived = archived;
			return this;
		}

		public Builder name(String name) {
			this.name = name;
			return this;
		}

		public Bank build() {
			return new Bank(this);
		}
	}
}