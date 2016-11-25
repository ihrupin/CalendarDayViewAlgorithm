package com.hrupin.calendardayviewalgorithm.domain;

/**
 * Created by Igor Khrupin www.hrupin.com on 11/22/16.
 */

public class Event {
	private int mStartTimeInMinutes;
	private int mEndTimeInMinutes;
	private String mName;
	private float mStartTime;
	private float mEndTime;
	private int mId;

	public Event(int id, String mName, float mStartTime, float mEndTime) {
		this.mId = id;
		this.mName = mName;
		this.mStartTime = mStartTime;
		this.mEndTime = mEndTime;
		this.mStartTimeInMinutes = (int)(mStartTime * 60f);
		this.mEndTimeInMinutes = (int)(mEndTime * 60f);;
	}

	public String getName() {
		return mName;
	}

	public int getStartTimeInMinutes() {
		return mStartTimeInMinutes;
	}

	public int getEndTimeInMinutes() {
		return mEndTimeInMinutes;
	}

	public float getStartTime() {
		return mStartTime;
	}

	public float getEndTime() {
		return mEndTime;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Event event = (Event) o;

		return mId == event.mId;

	}

	@Override
	public int hashCode() {
		return mId;
	}
}
