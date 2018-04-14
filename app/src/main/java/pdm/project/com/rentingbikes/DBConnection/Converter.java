package pdm.project.com.rentingbikes.DBConnection;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * Created by Claudia on 14-Apr-18.
 */

public class Converter {

    @TypeConverter
    public static Date fromTimeStamp(Long value) {return value == null ? null : new Date(value);}

    @TypeConverter
    public static Long dateToTimestamp(Date date) { return date == null ? null : date.getTime();}
}
