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

    public LocalDateTime getLocalDateTime() {
        return LocalDateTime.of(dt.toLocalDate(), dt.toLocalTime());
    }

    public DateTime getDate() {
        LocalDate date = dt.toLocalDate();
        return new DateTime(date.getYear(), date.getMonthValue(), date.getDayOfMonth());
    }

    public int getDay() {
        return dt.getDayOfMonth();
    }

    public void setDay(int day) {
        dt = dt.withDayOfMonth(day);
    }

    public int getDayOfWeek() {
        return dt.getDayOfWeek().getValue();
    }

    public int getDayOfYear() {
        return dt.getDayOfYear();
    }

    public int getHour() {
        return dt.getHour();
    }

    public void setHour(int hour) {
        dt = dt.withHour(hour);
    }

    public int getNanosecond() {
        return dt.getNano();
    }

    public void setNanosecond(int nanosecond) {
        dt = dt.withNano(nanosecond);
    }

    public int getMinute() {
        return dt.getMinute();
    }

    public void setMinute(int minute) {
        dt = dt.withMinute(minute);
    }

    public int getMonth() {
        return dt.getMonthValue();
    }

    public void setMonth(int month) {
        dt = dt.withMonth(month);
    }

    public int getSecond() {
        return dt.getSecond();
    }

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
     * @return
     */
    public DateTime add(Duration duration) {
        return new DateTime(dt.plus(duration));
    }

    public DateTime substract(Duration duration) {
        return new DateTime(dt.minus(duration));
    }

    public Duration between(DateTime dateTime) {
        return Duration.between(dt, dateTime.dt);
    }

    public void addDays(long days) {
        dt = dt.plusDays(days);
    }

    public void minusDays(long days) {
        dt = dt.minusDays(days);
    }

    public void addMonths(long months) {
        dt = dt.plusMonths(months);
    }

    public void minusMonths(long months) {
        dt = dt.minusMonths(months);
    }

    public void addYears(long years) {
        dt = dt.plusYears(years);
    }

    public void minusYears(long years) {
        dt = dt.minusYears(years);
    }

    public void addHours(long hours) {
        dt = dt.plusHours(hours);
    }

    public void minusHours(long hours) {
        dt = dt.minusHours(hours);
    }

    public void addMinutes(long minutes) {
        dt = dt.plusMinutes(minutes);
    }

    public void minusMinutes(long minutes) {
        dt = dt.minusMinutes(minutes);
    }

    public void addSeconds(long seconds) {
        dt = dt.plusSeconds(seconds);
    }

    public void minusSeconds(long seconds) {
        dt = dt.minusSeconds(seconds);
    }

    public void addNanoseconds(long nanoseconds) {
        dt = dt.plusNanos(nanoseconds);
    }

    public void minusNanoseconds(long nanoseconds) {
        dt = dt.minusNanos(nanoseconds);
    }

    public boolean after(DateTime dateTime) {
        return dt.isAfter(dateTime.dt);
    }

    public boolean before(DateTime dateTime) {
        return dt.isBefore(dateTime.dt);
    }

    public boolean equals(DateTime dateTime) {
        return dt.isEqual(dateTime.dt);
    }

    public int compareTo(DateTime dateTime) {
        return dt.compareTo(dateTime.dt);
    }

    public int daysOfMonth() {
        return dt.toLocalDate().lengthOfMonth();
    }

    public int daysOfYear() {
        return dt.toLocalDate().lengthOfYear();
    }

    public boolean isLeapYear() {
        return dt.toLocalDate().isLeapYear();
    }

    public static DateTime parse(String datetime) {
        return new DateTime(LocalDateTime.parse(datetime));
    }

    public static DateTime parse(String datetime, DateTimeFormatter formatter) {
        return new DateTime(LocalDateTime.parse(datetime, formatter));
    }

    @Override
    public String toString() {
        return toString("uuuu-MM-dd'T'HH:mm:ss");
    }

    public String toString(String format) {
        return dt.format(DateTimeFormatter.ofPattern(format));
    }

    public int weekOfYear()
    {
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        return dt.get(weekFields.weekOfYear());
    }

    public int weekOfMonth()
    {
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        return dt.get(weekFields.weekOfMonth());
    }
}
