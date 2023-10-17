package com.fidelity.investmonkey.restcontroller;

import java.util.Objects;

public class Status {
	private String status;

	public Status(String status) {
		super();
		this.status = status;
	}

	public Status() {
		super();
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		return Objects.hash(status);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Status other = (Status) obj;
		return Objects.equals(status, other.status);
	}

	@Override
	public String toString() {
		return "Status [status=" + status + "]";
	}
	

}
