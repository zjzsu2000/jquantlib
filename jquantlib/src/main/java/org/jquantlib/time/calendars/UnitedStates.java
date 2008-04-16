/*
 Copyright (C) 2008 Srinivas Hasti

 This file is part of JQuantLib, a free-software/open-source library
 for financial quantitative analysts and developers - http://jquantlib.org/

 JQuantLib is free software: you can redistribute it and/or modify it
 under the terms of the QuantLib license.  You should have received a
 copy of the license along with this program; if not, please email
 <jquantlib-dev@lists.sf.net>. The license is also available online at
 <http://jquantlib.org/license.shtml>.

 This program is distributed in the hope that it will be useful, but WITHOUT
 ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 FOR A PARTICULAR PURPOSE.  See the license for more details.
 
 JQuantLib is based on QuantLib. http://quantlib.org/
 When applicable, the originating copyright notice follows below.
 */
package org.jquantlib.time.calendars;

import org.jquantlib.time.BusinessDayConvention;
import org.jquantlib.time.Calendar;
import org.jquantlib.time.Period;
import org.jquantlib.time.TimeUnit;
import org.jquantlib.time.Weekday;
import org.jquantlib.time.WesternCalendar;
import org.jquantlib.util.Date;
import org.jquantlib.util.Date.Month;

/*
 * United States calendars <br> Public holidays (see:
 * http://www.opm.gov/fedhol/): <ul> <li>Saturdays</li> <li>Sundays</li>
 * <li>New Year's Day, January 1st (possibly moved to Monday if actually on
 * Sunday, or to Friday if on Saturday)</li> <li>Martin Luther King's
 * birthday, third Monday in January</li> <li>Presidents' Day (a.k.a.
 * Washington's birthday), third Monday in February</li> <li>Memorial Day,
 * last Monday in May</li> <li>Independence Day, July 4th (moved to Monday if
 * Sunday or Friday if Saturday)</li> <li>Labor Day, first Monday in September</li>
 * <li>Columbus Day, second Monday in October</li> <li>Veterans' Day,
 * November 11th (moved to Monday if Sunday or Friday if Saturday)</li> <li>Thanksgiving
 * Day, fourth Thursday in November</li> <li>Christmas, December 25th (moved
 * to Monday if Sunday or Friday if Saturday)</li> </ul>
 * 
 * Holidays for the stock exchange (data from http://www.nyse.com): <ul> <li>Saturdays</li>
 * <li>Sundays</li> <li>New Year's Day, January 1st (possibly moved to Monday
 * if actually on Sunday)</li> <li>Martin Luther King's birthday, third Monday
 * in January (since 1998)</li> <li>Presidents' Day (a.k.a. Washington's
 * birthday), third Monday in February</li> <li>Good Friday</li> <li>Memorial
 * Day, last Monday in May</li> <li>Independence Day, July 4th (moved to
 * Monday if Sunday or Friday if Saturday)</li> <li>Labor Day, first Monday in
 * September</li> <li>Thanksgiving Day, fourth Thursday in November</li> <li>Presidential
 * election day, first Tuesday in November of election years (until 1980)</li>
 * <li>Christmas, December 25th (moved to Monday if Sunday or Friday if
 * Saturday)</li> <li>Special historic closings (see
 * http://www.nyse.com/pdfs/closings.pdf)</li> </ul>
 * 
 * Holidays for the government bond market (data from
 * http://www.bondmarkets.com): <ul> <li>Saturdays</li> <li>Sundays</li>
 * <li>New Year's Day, January 1st (possibly moved to Monday if actually on
 * Sunday)</li> <li>Martin Luther King's birthday, third Monday in January</li>
 * <li>Presidents' Day (a.k.a. Washington's birthday), third Monday in February</li>
 * <li>Good Friday</li> <li>Memorial Day, last Monday in May</li> <li>Independence
 * Day, July 4th (moved to Monday if Sunday or Friday if Saturday)</li> <li>Labor
 * Day, first Monday in September</li> <li>Columbus Day, second Monday in
 * October</li> <li>Veterans' Day, November 11th (moved to Monday if Sunday or
 * Friday if Saturday)</li> <li>Thanksgiving Day, fourth Thursday in November</li>
 * <li>Christmas, December 25th (moved to Monday if Sunday or Friday if
 * Saturday)</li> </ul>
 * 
 * Holidays for the North American Energy Reliability Council (data from
 * http://www.nerc.com/~oc/offpeaks.html): <ul> <li>Saturdays</li> <li>Sundays</li>
 * <li>New Year's Day, January 1st (possibly moved to Monday if actually on
 * Sunday)</li> <li>Memorial Day, last Monday in May</li> <li>Independence
 * Day, July 4th (moved to Monday if Sunday)</li> <li>Labor Day, first Monday
 * in September</li> <li>Thanksgiving Day, fourth Thursday in November</li>
 * <li>Christmas, December 25th (moved to Monday if Sunday)</li> </ul>
 * 
 * @author Srinivas Hasti
 */

public class UnitedStates implements Calendar {
	public static enum Market {
		SETTLEMENT,      // !< generic settlement calendar
		NYSE,           // !< New York stock exchange calendar
		GOVERNMENTBOND, // !< government-bond calendar
		NERC            // !< off-peak days for NERC
	};

	private final static Calendar SETTLEMENT_CALENDAR = new SettlementCalendar();
	private final static Calendar NYSE_CALENDAR = new NyseCalendar();
	private final static Calendar GOVBOND_CALENDAR = new GovernmentBondCalendar();
	private final static Calendar NERC_CALENDAR = new NercCalendar();

	private Calendar marketCalendar;

	public UnitedStates(Market market) {
		switch (market) {
		case SETTLEMENT:
			marketCalendar = SETTLEMENT_CALENDAR;
			break;
		case NYSE:
			marketCalendar = NYSE_CALENDAR;
			break;
		case GOVERNMENTBOND:
			marketCalendar = GOVBOND_CALENDAR;
			break;
		case NERC:
			marketCalendar = NERC_CALENDAR;
			break;
		default:
			throw new IllegalArgumentException("unknown market");
		}
	}

	@Override
	public Date advance(Date d, int n, TimeUnit unit) {
		return marketCalendar.advance(d, n, unit);
	}

	@Override
	public Date advance(Date d, int n, TimeUnit unit,
			BusinessDayConvention convention, boolean endOfMonth) {
		return marketCalendar.advance(d, n, unit, convention, endOfMonth);
	}

	@Override
	public Date advance(Date date, Period period,
			BusinessDayConvention convention, boolean endOfMonth) {
		return marketCalendar.advance(date, period, convention, endOfMonth);
	}

	@Override
	public long businessDaysBetween(Date from, Date to, boolean includeFirst,
			boolean includeLast) {
		return marketCalendar.businessDaysBetween(from, to, includeFirst,
				includeLast);
	}

	@Override
	public Date getEndOfMonth(Date d) {
		return marketCalendar.getEndOfMonth(d);
	}

	@Override
	public String getName() {
		return marketCalendar.getName();
	}

	@Override
	public boolean isBusinessDay(Date d) {
		return marketCalendar.isBusinessDay(d);
	}

	@Override
	public boolean isEndOfMonth(Date d) {
		return marketCalendar.isEndOfMonth(d);
	}

	@Override
	public boolean isHoliday(Date d) {
		return marketCalendar.isHoliday(d);
	}

	@Override
	public boolean isWeekend(Weekday w) {
		return marketCalendar.isWeekend(w);
	}

	private static class SettlementCalendar extends WesternCalendar {

		@Override
		public String getName() {
			return "US settlement";
		}

		@Override
		public boolean isBusinessDay(Date date) {
			Weekday w = date.getWeekday();
			int d = date.getDayOfMonth();
			int m = date.getMonth();
			if (isWeekend(w)
			// New Year's Day (possibly moved to Monday if on Sunday)
					|| ((d == 1 || (d == 2 && w == Weekday.Monday)) && m == Month.January
							.toInteger())
					// (or to Friday if on Saturday)
					|| (d == 31 && w == Weekday.Friday && m == Month.December
							.toInteger())
					// Martin Luther King's birthday (third Monday in January)
					|| ((d >= 15 && d <= 21) && w == Weekday.Monday && m == Month.January
							.toInteger())
					// Washington's birthday (third Monday in February)
					|| ((d >= 15 && d <= 21) && w == Weekday.Monday && m == Month.February
							.toInteger())
					// Memorial Day (last Monday in May)
					|| (d >= 25 && w == Weekday.Monday && m == Month.May
							.toInteger())
					// Independence Day (Monday if Sunday or Friday if Saturday)
					|| ((d == 4 || (d == 5 && w == Weekday.Monday) || (d == 3 && w == Weekday.Friday)) && m == Month.July
							.toInteger())
					// Labor Day (first Monday in September)
					|| (d <= 7 && w == Weekday.Monday && m == Month.September
							.toInteger())
					// Columbus Day (second Monday in October)
					|| ((d >= 8 && d <= 14) && w == Weekday.Monday && m == Month.October
							.toInteger())
					// Veteran's Day (Monday if Sunday or Friday if Saturday)
					|| ((d == 11 || (d == 12 && w == Weekday.Monday) || (d == 10 && w == Weekday.Friday)) && m == Month.November
							.toInteger())
					// Thanksgiving Day (fourth Thursday in November)
					|| ((d >= 22 && d <= 28) && w == Weekday.Thursday && m == Month.November
							.toInteger())
					// Christmas (Monday if Sunday or Friday if Saturday)
					|| ((d == 25 || (d == 26 && w == Weekday.Monday) || (d == 24 && w == Weekday.Friday)) && m == Month.December
							.toInteger()))
				return false;
			return true;
		}
	}

	private static class NyseCalendar extends WesternCalendar {
		@Override
		public String getName() {
			return "New York stock exchange";
		}

		@Override
		public boolean isBusinessDay(Date date) {
			Weekday w = date.getWeekday();
			int d = date.getDayOfMonth(), dd = date.getDayOfYear();
			int m = date.getMonth();
			int y = date.getYear();
			int em = easterMonday(y);
			if (isWeekend(w)
			// New Year's Day (possibly moved to Monday if on Sunday)
					|| ((d == 1 || (d == 2 && w == Weekday.Monday)) && m == Month.January
							.toInteger())
					// Washington's birthday (third Monday in February)
					|| ((d >= 15 && d <= 21) && w == Weekday.Monday && m == Month.February
							.toInteger())
					// Good Friday
					|| (dd == em - 3)
					// Memorial Day (last Monday in May)
					|| (d >= 25 && w == Weekday.Monday && m == Month.May
							.toInteger())
					// Independence Day (Monday if Sunday or Friday if Saturday)
					|| ((d == 4 || (d == 5 && w == Weekday.Monday) || (d == 3 && w == Weekday.Friday)) && m == Month.July
							.toInteger())
					// Labor Day (first Monday in September)
					|| (d <= 7 && w == Weekday.Monday && m == Month.September
							.toInteger())
					// Thanksgiving Day (fourth Thursday in November)
					|| ((d >= 22 && d <= 28) && w == Weekday.Thursday && m == Month.November
							.toInteger())
					// Christmas (Monday if Sunday or Friday if Saturday)
					|| ((d == 25 || (d == 26 && w == Weekday.Monday) || (d == 24 && w == Weekday.Friday)) && m == Month.December
							.toInteger()))
				return false;

			if (y >= 1998) {
				if (// Martin Luther King's birthday (third Monday in January)
				((d >= 15 && d <= 21) && w == Weekday.Monday && m == Month.January
						.toInteger())
						// President Reagan's funeral
						|| (y == 2004 && m == Month.June.toInteger() && d == 11)
						// September 11, 2001
						|| (y == 2001 && m == Month.September.toInteger() && (11 <= d && d <= 14))
						// President Ford's funeral
						|| (y == 2007 && m == Month.January.toInteger() && d == 2))
					return false;
			} else if (y <= 1980) {
				if (// Presidential election days
				((y % 4 == 0) && m == Month.November.toInteger() && d <= 7 && w == Weekday.Tuesday)
						// 1977 Blackout
						|| (y == 1977 && m == Month.July.toInteger() && d == 14)
						// Funeral of former President Lyndon B. Johnson.
						|| (y == 1973 && m == Month.January.toInteger() && d == 25)
						// Funeral of former President Harry S. Truman
						|| (y == 1972 && m == Month.December.toInteger() && d == 28)
						// National Day of Participation for the lunar
						// exploration.
						|| (y == 1969 && m == Month.July.toInteger() && d == 21)
						// Funeral of former President Eisenhower.
						|| (y == 1969 && m == Month.March.toInteger() && d == 31)
						// Closed all day - heavy snow.
						|| (y == 1969 && m == Month.February.toInteger() && d == 10)
						// Day after Independence Day.
						|| (y == 1968 && m == Month.July.toInteger() && d == 5)
						// June 12-Dec. 31, 1968
						// Four day week (closed on Wednesdays) - Paperwork
						// Crisis
						|| (y == 1968 && dd >= 163 && w == Weekday.Wednesday))
					return false;
			} else {
				if (// Nixon's funeral
				(y == 1994 && m == Month.April.toInteger() && d == 27))
					return false;
			}
			return true;
		}
	}

	private static class GovernmentBondCalendar extends WesternCalendar {

		@Override
		public String getName() {
			return "US government bond market";
		}

		@Override
		public boolean isBusinessDay(Date date) {
			Weekday w = date.getWeekday();
			int d = date.getDayOfMonth(), dd = date.getDayOfYear();
			int m = date.getMonth();
			int y = date.getYear();
			int em = easterMonday(y);
			if (isWeekend(w)
			// New Year's Day (possibly moved to Monday if on Sunday)
					|| ((d == 1 || (d == 2 && w == Weekday.Monday)) && m == Month.January
							.toInteger())
					// Martin Luther King's birthday (third Monday in January)
					|| ((d >= 15 && d <= 21) && w == Weekday.Monday && m == Month.January
							.toInteger())
					// Washington's birthday (third Monday in February)
					|| ((d >= 15 && d <= 21) && w == Weekday.Monday && m == Month.February
							.toInteger())
					// Good Friday
					|| (dd == em - 3)
					// Memorial Day (last Monday in May)
					|| (d >= 25 && w == Weekday.Monday && m == Month.May
							.toInteger())
					// Independence Day (Monday if Sunday or Friday if Saturday)
					|| ((d == 4 || (d == 5 && w == Weekday.Monday) || (d == 3 && w == Weekday.Friday)) && m == Month.July
							.toInteger())
					// Labor Day (first Monday in September)
					|| (d <= 7 && w == Weekday.Monday && m == Month.September
							.toInteger())
					// Columbus Day (second Monday in October)
					|| ((d >= 8 && d <= 14) && w == Weekday.Monday && m == Month.October
							.toInteger())
					// Veteran's Day (Monday if Sunday or Friday if Saturday)
					|| ((d == 11 || (d == 12 && w == Weekday.Monday) || (d == 10 && w == Weekday.Friday)) && m == Month.November
							.toInteger())
					// Thanksgiving Day (fourth Thursday in November)
					|| ((d >= 22 && d <= 28) && w == Weekday.Thursday && m == Month.November
							.toInteger())
					// Christmas (Monday if Sunday or Friday if Saturday)
					|| ((d == 25 || (d == 26 && w == Weekday.Monday) || (d == 24 && w == Weekday.Friday)) && m == Month.December
							.toInteger()))
				return false;
			return true;
		}
	}

	private static class NercCalendar extends WesternCalendar {

		@Override
		public String getName() {
			return "North American Energy Reliability Council";
		}

		@Override
		public boolean isBusinessDay(Date date) {
			Weekday w = date.getWeekday();
			int d = date.getDayOfMonth();
			int m = date.getMonth();
			if (isWeekend(w)
			// New Year's Day (possibly moved to Monday if on Sunday)
					|| ((d == 1 || (d == 2 && w == Weekday.Monday)) && m == Month.January
							.toInteger())
					// Memorial Day (last Monday in May)
					|| (d >= 25 && w == Weekday.Monday && m == Month.May
							.toInteger())
					// Independence Day (Monday if Sunday)
					|| ((d == 4 || (d == 5 && w == Weekday.Monday)) && m == Month.July
							.toInteger())
					// Labor Day (first Monday in September)
					|| (d <= 7 && w == Weekday.Monday && m == Month.September
							.toInteger())
					// Thanksgiving Day (fourth Thursday in November)
					|| ((d >= 22 && d <= 28) && w == Weekday.Thursday && m == Month.November
							.toInteger())
					// Christmas (Monday if Sunday)
					|| ((d == 25 || (d == 26 && w == Weekday.Monday)) && m == Month.December
							.toInteger()))
				return false;
			return true;
		}

	}

}
