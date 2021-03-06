package org.efix.message.field;


public class ClearingInstruction {

    public static final int PROCESS_NORMALLY = 0;
    public static final int EXCLUDE_FROM_ALL_NETTING = 1;
    public static final int BILATERAL_NETTING_ONLY = 2;
    public static final int EX_CLEARING = 3;
    public static final int SPECIAL_TRADE = 4;
    public static final int MULTILATERAL_NETTING = 5;
    public static final int CLEAR_AGAINST_CENTRAL_COUNTERPARTY = 6;
    public static final int EXCLUDE_FROM_CENTRAL_COUNTERPARTY = 7;
    public static final int MANUAL_MODE = 8;
    public static final int AUTOMATIC_POSTING_MODE = 9;
    public static final int AUTOMATIC_GIVE_UP_MODE = 10;
    public static final int QUALIFIED_SERVICE_REPRESENTATIVE_QSR = 11;
    public static final int CUSTOMER_TRADE = 12;
    public static final int SELF_CLEARING = 13;

}