package com.ca.data;

import java.sql.Date;

public class CreatedAt {
  //  public long $date;

	public Date $date;

	public Date get$date() {
		return $date;
	}

	public void set$date(Date $date) {
		this.$date = $date;
	}

	@Override
	public String toString() {
		return "CreatedAt [$date=" + $date + "]";
	}

	}
