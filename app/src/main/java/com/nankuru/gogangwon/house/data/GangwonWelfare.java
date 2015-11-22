package com.nankuru.gogangwon.house.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by chitacan on 2015. 11. 20..
 */
public class GangwonWelfare {
    @SerializedName("gangwon-welfare-child_welfare_institution") WelFareChild emptyHouse = new WelFareChild();

    public class WelFareChild
    {
        public int list_total_count;

        @SerializedName("RESULT")
        public ResultData resultData = new ResultData();

        @SerializedName("row")
        public WelFareChildRow welFareChildRow = new WelFareChildRow();

        public class WelFareChildRow
        {
            String ARTICLE_SEQ;
            String TITLE;
            String NAME;
            String TELNO;
            String ADDR;
            String MST_NM;

            public String getARTICLE_SEQ() {
                return ARTICLE_SEQ;
            }

            public String getTITLE() {
                return TITLE;
            }

            public String getNAME() {
                return NAME;
            }

            public String getTELNO() {
                return TELNO;
            }

            public String getADDR() {
                return ADDR;
            }

            public String getMST_NM() {
                return MST_NM;
            }
        }
    }
}
