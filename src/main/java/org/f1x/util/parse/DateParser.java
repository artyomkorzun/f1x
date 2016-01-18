package org.f1x.util.parse;

import org.f1x.util.MutableInt;
import org.f1x.util.buffer.Buffer;
import org.f1x.util.type.DateType;

import static org.f1x.util.parse.ParserUtil.checkByte;
import static org.f1x.util.parse.ParserUtil.checkFreeSpace;

public class DateParser {

    protected static final int DAYS_TO_EPOCH = 1969 * 365 + 1969 / 4 - 1969 / 100 + 1969 / 400;
    protected static final long DAY_IN_MILLIS = 24 * 60 * 60 * 1000;
    protected static final int DAYS_IN_YEAR = 365;

    private static final byte[] MONTH_TO_DAYS = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private static final byte[] MONTH_TO_DAYS_LEAP = {0, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    private static final short[] DAYS_TO_NEW_YEAR = {0, 365, 334, 306, 275, 245, 214, 184, 153, 122, 92, 61, 31};
    private static final short[] DAYS_TO_NEW_YEAR_LEAP = {0, 366, 335, 306, 275, 245, 214, 184, 153, 122, 92, 61, 31};

    public static long parseDate(byte separator, Buffer buffer, MutableInt offset, int end) {
        int off = offset.value();

        checkFreeSpace(end - off, DateType.LENGTH + 1);
        long time = parseDate(buffer, off);

        checkByte(buffer.getByte(off + DateType.LENGTH), separator);
        offset.value(off + DateType.LENGTH + 1);

        return time;
    }

    protected static long parseDate(Buffer buffer, int offset) {
        int year = IntParser.parse4DigitUInt(buffer, offset + DateType.YEAR_OFFSET);
        int month = IntParser.parse2DigitUInt(buffer, offset + DateType.MONTH_OFFSET);
        int day = IntParser.parse2DigitUInt(buffer, offset + DateType.DAY_OFFSET);

        checkMonth(month);

        int cycles4 = year >> 2;          // year / 4
        int cycles100 = cycles4 / 25;      // year / 100
        int cycles400 = cycles100 >> 2;    // year / 400

        int days = DAYS_IN_YEAR * year + cycles4 - cycles100 + cycles400;   // 365 * year + year / 4 - year / 100 + year / 400
        boolean leapYear = (year & 0b11) == 0 && (year - ((cycles100 << 6) + (cycles100 << 5) + (cycles100 << 2)) != 0 || (year & 0b1111) == 0); // year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)

        int daysInMonth;
        int daysToNewYear;
        if (leapYear) {
            daysInMonth = MONTH_TO_DAYS_LEAP[month];
            daysToNewYear = DAYS_TO_NEW_YEAR_LEAP[month];
        } else {
            daysInMonth = MONTH_TO_DAYS[month];
            daysToNewYear = DAYS_TO_NEW_YEAR[month];
        }

        checkDay(day, daysInMonth);
        days += day - 1 - daysToNewYear - DAYS_TO_EPOCH;

        return days * DAY_IN_MILLIS; // TODO: optimize multiplication
    }

    private static int checkDay(int day, int daysInMonth) {
        if (day == 0 || day > daysInMonth)
            throw new ParserException("invalid day " + day);

        return day;
    }

    protected static int checkMonth(int month) {
        if (month == 0 || month > 12)
            throw new ParserException("invalid month " + month);

        return month;
    }

}
