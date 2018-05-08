package com.mylove.baselib.activity.permission;

import android.Manifest;

/**
 * @author myLove
 * @time 2017/11/24 16:51
 * @e-mail mylove.520.y@gmail.com
 * @overview 危险权限
 */

final class PermissionBean {
    /*
   *   permission group : PHONE
   * 	READ_PHONE_STATE
   *   CALL_PHONE
   *   READ_CALL_LOG
   *   WRITE_CALL_LOG
   *   ADD_VOICEMAIL
   *   USE_SIP
   *   PROCESS_OUTGOING_CALLS
   */
    static final String PHONE = Manifest.permission.READ_PHONE_STATE;

    /**
     * permission group : CALENDAR
     * READ_CALENDAR
     * WRITE_CALENDAR
     */
    static final String CALENDAR = Manifest.permission.READ_CALENDAR;

    /**
     * permission group : CAMERA
     * CAMERA
     */
    static final String CAMERA = Manifest.permission.CAMERA;

    /**
     * permission group : CONTACTS
     * READ_CONTACTS
     * WRITE_CONTACTS
     * GET_ACCOUNTS
     */
    static final String CONTACTS = Manifest.permission.READ_CONTACTS;

    /**
     * permission group : LOCATION
     * ACCESS_FINE_LOCATION
     * ACCESS_COARSE_LOCATION
     */
    static final String LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;

    /**
     * permission group : MICROPHONE
     * RECORD_AUDIO
     */
    static final String MICROPHONE = Manifest.permission.RECORD_AUDIO;

    /**
     * permission group : SENSORS
     * BODY_SENSORS
     */
    static final String SENSORS = Manifest.permission.BODY_SENSORS;

    /**
     * permission group : SMS
     * SEND_SMS
     * RECEIVE_SMS
     * READ_SMS
     * RECEIVE_WAP_PUSH
     * RECEIVE_MMS
     */
    static final String SMS = Manifest.permission.SEND_SMS;

    /**
     * permission group : STORAGE
     * READ_EXTERNAL_STORAGE
     * WRITE_EXTERNAL_STORAGE
     */
    static final String STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
}
