package jstart;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.Locale;

public final class DateTime {
    LocalDateTime dt;
    /**
     * Minimum available DateTime
     */
    public static final DateTime MinValue;

    /**
     * Maximum available DateTime
     */
    public static final DateTime MaxValue;

    static {
        MinValue = new DateTime(LocalDateTime.MIN);
        MaxValue = new DateTime(LocalDateTime.MAX);
    }

    /**
     * DateTime default constructor
     */
    public DateTime() {
        dt = LocalDateTime.now();
    }

    /**
     * DateTime date parts constructor
     *
     * @param year  Year of date
     * @param month Month of date
     * @param day   Day of date (of month)
     */
    public DateTime(int year, int month, int day) {
        this(year, month, day, 0, 0, 0);
    }

    /**
     * DateTime date and time parts constructor
     *
     * @param year   Year of date
     * @param month  Month of date
     * @param day    Day of date (of month)
     * @param hour   Hour of date
     * @param minute Minute of date
     * @param second Second of date
     */
    public DateTime(int year, int month, int day, int hour, int minute, int second) {
        dt = LocalDateTime.of(year, month, day, hour, minute, second);
    }

    /**
     * DateTime constructor from Java's LocalDateTime
     *
     * @param localDateTime
     */
    public DateTime(LocalDateTime localDateTime) {
        dt = localDateTime;
    }

    /**
     * DateTime constructor from Java's LocalDate
     *
     * @param localDate
     */
    public DateTime(LocalDate localDate) {
        this(localDate.getYear(), localDate.getMonthValue(), localDate.getDayOfMonth(), 0, 0, 0);
    }

    /**
     * DateTime constructor from Java's LocalTime
     *
     * @param localTime
     */
    public DateTime(LocalTime localTime) {
        this();
        setHour(localTime.getHour());
        setMinute(localTime.getMinute());
        setSecond(localTime.getSecond());
        setNanosecond(localTime.getNano());
    }

    /**
     * Gets the local date and time into Datetime object
     * @return LocalDateTime value
     */
    public LocalDateTime getLocalDateTime() {
        return LocalDateTime.of(dt.toLocalDate(), dt.toLocalTime());
    }

    /**
     * Gets the actual date as DateTime object
     * @return DateTime the actual date
     */
    public DateTime getDate() {
        LocalDate date = dt.toLocalDate();
        return new DateTime(date.getYear(), date.getMonthValue(), date.getDayOfMonth());
    }

    /**
     * Gets the ordinal day of the month
     * @return integer ordinal: 1-31
     */
    public int getDay() {
        return dt.getDayOfMonth();
    }

    /**
     * Sets the ordinal day of the month
     * @param day ordinal: 1-31
     */
    public void setDay(int day) {
        dt = dt.withDayOfMonth(day);
    }

    /**
     * Gets the day of week
     * @return 1 (monday) to 7 (sunday)
     */
    public int getDayOfWeek() {
        return dt.getDayOfWeek().getValue();
    }

    /**
     * Gets the ordeinal day of year
     * @return 1 to 365/6
     */
    public int getDayOfYear() {
        return dt.getDayOfYear();
    }

    /**
     * Gets the ordenal hour of day
     * @return 0 to 23
     */
    public int getHour() {
        return dt.getHour();
    }

    /**
     * Sets the hour of day
     * @param hour 0 to 23
     */
    public void setHour(int hour) {
        dt = dt.withHour(hour);
    }

    public int getNanosecond() {
        return dt.getNano();
    }

    public void setNanosecond(int nanosecond) {
        dt = dt.withNano(nanosecond);
    }

    /**
     * Gets the minute of the hour
     * @return integer 0 to 59
     */
    public int getMinute() {
        return dt.getMinute();
    }

    /**
     * Sets the minute of the hour
     * @param minute integer 0 to 59
     */
    public void setMinute(int minute) {
        dt = dt.withMinute(minute);
    }

    /**
     * Gets month of year
     * @return integer 1 to 12
     */
    public int getMonth() {
        return dt.getMonthValue();
    }

    /**
     * Sets month of year
     * @param month integer 1 to 12
     */
    public void setMonth(int month) {
        dt = dt.withMonth(month);
    }

    /**
     * Gets second of hour
     * @return integer 0 to 59
     */
    public int getSecond() {
        return dt.getSecond();
    }

    /**
     * Set second of hour
     * @param second integer 0 to 59
     */
    public void setSecond(int second) {
        dt = dt.withSecond(second);
    }

    public int getYear() {
        return dt.getYear();
    }

    public void setYear(int year) {
        dt = dt.withYear(year);
    }

    /**
     * Duration of the day in hours, minutes and seconds
     *
     * @return a Durantion not null
     */
    public Duration timeOfDay() {
        return Duration.between(dt.toLocalDate(), dt);
    }

    /**
     * Adds a duration object to current date and time
     *
     * @param duration
     * @return new date and time object
     */
    public DateTime add(Duration duration) {
        return new DateTime(dt.plus(duration));
    }

    /**
     * Substracts a duration object to current date and time
     * 
     * @param duration
     * @return new date and time object
     */
    public DateTime substract(Duration duration) {
        return new DateTime(dt.minus(duration));
    }

    public Duration between(DateTime dateTime) {
        return Duration.between(dt, dateTime.dt);
    }

    /**
     * Adds a number of days to current date and time
     * 
     * @param days
     */
    public void addDays(long days) {
        dt = dt.plusDays(days);
    }

    /**
     * Substracts a number of days to current date and time
     * 
     * @param days
     */
    public void minusDays(long days) {
        dt = dt.minusDays(days);
    }

    /**
     * Adds a number of monts to current date and time
     * 
     * @param months
     */
    public void addMonths(long months) {
        dt = dt.plusMonths(months);
    }

    /**
     * Substracts a number of months to current date and time
     * 
     * @param months
     */
    public void minusMonths(long months) {
        dt = dt.minusMonths(months);
    }

    /**
     * Adds a number of years to current date and time
     * 
     * @param years
     */
    public void addYears(long years) {
        dt = dt.plusYears(years);
    }

    /**
     * Substracts a number of years to current date and time
     * 
     * @param years
     */
    public void minusYears(long years) {
        dt = dt.minusYears(years);
    }

    /**
     * Adds a number of hours to current date and time
     * 
     * @param hours
     */
    public void addHours(long hours) {
        dt = dt.plusHours(hours);
    }

    /**
     * Substracts a number of hours to current date and time
     * 
     * @param hours
     */
    public void minusHours(long hours) {
        dt = dt.minusHours(hours);
    }

    /**
     * Adds a number of minutes to current date and time
     * 
     * @param minutes
     */
    public void addMinutes(long minutes) {
        dt = dt.plusMinutes(minutes);
    }

    /**
     * Substracts a number of minutes to current date and time
     * 
     * @param minutes
     */
    public void minusMinutes(long minutes) {
        dt = dt.minusMinutes(minutes);
    }

    /**
     * Adds a number of seconds to current date and time
     * 
     * @param seconds
     */
    public void addSeconds(long seconds) {
        dt = dt.plusSeconds(seconds);
    }

    /**
     * Substracts a number of seconds to current date and time
     * 
     * @param seconds
     */
    public void minusSeconds(long seconds) {
        dt = dt.minusSeconds(seconds);
    }

    /**
     * Adds a number of nanoseconds to current date and time
     * 
     * @param nanoseconds
     */
    public void addNanoseconds(long nanoseconds) {
        dt = dt.plusNanos(nanoseconds);
    }

    /**
     * Substracts a number of nanoseconds to current date and time
     * 
     * @param nanoseconds
     */
    public void minusNanoseconds(long nanoseconds) {
        dt = dt.minusNanos(nanoseconds);
    }

    /**
     * Gets if the current date and time is previous to dateTime
     * 
     * @param dateTime any date and time object
     * @return
     */
    public boolean after(DateTime dateTime) {
        return dt.isAfter(dateTime.dt);
    }

    /**
     * Gets if the current date and time is later to dateTime
     * 
     * @param dateTime any date and time object
     * @return
     */
    public boolean before(DateTime dateTime) {
        return dt.isBefore(dateTime.dt);
    }

    /**
     * Gets if the current date and time is equals to dateTime
     * 
     * @param dateTime
     * @return
     */
    public boolean equals(DateTime dateTime) {
        return dt.isEqual(dateTime.dt);
    }

    /**
     * Compares the current date and time with dateTime
     * 
     * @param dateTime
     * @return 0 if equals, -1 if previous, 1 if later
     */
    public int compareTo(DateTime dateTime) {
        return dt.compareTo(dateTime.dt);
    }

    /**
     * Current number of days in the month
     * 
     * @return
     */
    public int daysOfMonth() {
        return dt.toLocalDate().lengthOfMonth();
    }

    /**
     * Current number of days in the year
     * 
     * @return
     */
    public int daysOfYear() {
        return dt.toLocalDate().lengthOfYear();
    }

    /**
     * Gets if current year is a leap year
     * 
     * @return
     */
    public boolean isLeapYear() {
        return dt.toLocalDate().isLeapYear();
    }

    /**
     * Gets the week of the current year
     * 
     * @return
     */
    public int weekOfYear()
    {
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        return dt.get(weekFields.weekOfYear());
    }

    /**
     * Gets the week of the current month
     * 
     * @return
     */
    public int weekOfMonth()
    {
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        return dt.get(weekFields.weekOfMonth());
    }

    /**
     * Parses a string date and time
     * 
     * @param datetime
     * @return a new date and time
     */
    public static DateTime parse(String datetime) {
        return new DateTime(LocalDateTime.parse(datetime));
    }

    /**
     * Parses a string date and time with a formatter
     * 
     * @param datetime
     * @param formatter
     * @return a new date and time
     */
    public static DateTime parse(String datetime, DateTimeFormatter formatter) {
        return new DateTime(LocalDateTime.parse(datetime, formatter));
    }

    /**
     * Gets the current date and time in ISO format
     */
    @Override
    public String toString() {
        return toString("uuuu-MM-dd'T'HH:mm:ss");
    }

    /**
     * Gets the current date and time in a given format
     * 
     * @param format
     * @return
     */
    public String toString(String format) {
        return dt.format(DateTimeFormatter.ofPattern(format));
    }
}
