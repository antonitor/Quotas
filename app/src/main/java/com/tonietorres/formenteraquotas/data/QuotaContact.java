package com.tonietorres.formenteraquotas.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class QuotaContact {

    public static final class DaysTable implements BaseColumns {

        public static final String TABLE_NAME = "days";
        public static final String DATE_COLUMN = "date";
        public static final String QUOTA_HALF_NINE_COLUMN = "quota_half_nine";
        public static final String QUOTA_HALF_TEN_COLUMN = "quota_half_ten";
        public static final String QUOTA_QUARTER_FIVE_COLUMN = "quota_quarter_five";
        public static final String QUOTA_HALF_SIX_COLUMN = "quota_half_six";
        public static final String PAX_HALF_NINE_COLUMN = "pax_half_nine";
        public static final String PAX_HALF_TEN_COLUMN = "pax_half_ten";
        public static final String PAX_QUARTER_FIVE_COLUMN = "pax_quarter_five";
        public static final String PAX_HALF_SIX_COLUMN = "pax_half_six";
    }
}
