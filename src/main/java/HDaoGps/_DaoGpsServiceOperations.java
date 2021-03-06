// **********************************************************************
//
// Copyright (c) 2003-2013 ZeroC, Inc. All rights reserved.
//
// This copy of Ice is licensed to you under the terms described in the
// ICE_LICENSE file included in this distribution.
//
// **********************************************************************
//
// Ice version 3.5.1
//
// <auto-generated>
//
// Generated from file `HDao_gps.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package HDaoGps;

public interface _DaoGpsServiceOperations
{
    boolean getBsRecords(BsGetRequest req, BsGetResponseHolder res, Ice.Current __current);

    boolean getLatestBsRecord(LatestBsGetRequest req, LatestBsGetResponseHolder res, Ice.Current __current);

    boolean getRecentBsRecords(RecentBsGetRequest req, RecentBsGetResponseHolder res, Ice.Current __current);

    boolean getFirstBsRecord(BsGetRequest req, BsGetResponseHolder res, Ice.Current __current);

    boolean getFirstGpsRecord(GpsGetRequest req, GpsGetResponseHolder res, Ice.Current __current);

    boolean getLatestGpsRecord(LatestGpsGetRequest req, LatestGpsGetResponseHolder res, Ice.Current __current);

    boolean getRecentGpsRecords(RecentGpsGetRequest req, RecentGpsGetResponseHolder res, Ice.Current __current);

    boolean getGpsRecords(GpsGetRequest req, GpsGetResponseHolder res, Ice.Current __current);

    boolean getGpsRecordsEx(GpsGetRequest req, GpsGetExResponseHolder res, Ice.Current __current);

    boolean getLatestGpsRecordByImei(LatestGpsGetRequestWithBindTime req, LatestGpsGetResponseHolder res, Ice.Current __current);

    boolean getGpsRecordsByImei(GpsGetRequest req, GpsGetExResponseHolder res, Ice.Current __current);

    boolean getLatestGpsRecordByMapSn(LatestGpsGetRequest req, LatestGpsGetResponseHolder res, Ice.Current __current);

    boolean getGpsRecordsByMapSn(GpsGetExRequest req, GpsGetExResponseHolder res, Ice.Current __current);

    boolean getSnMapRecords(Sn2DdMapGetReq req, Sn2DdMapGetResponseHolder res, Ice.Current __current);

    boolean getLatestRgpsRecord(LatestRgpsGetRequest req, LatestRgpsGetResponseHolder res, Ice.Current __current);

    boolean getLatestLocationByLbsRecord(LatestGpsGetRequest req, LatestGpsGetResponseHolder res, Ice.Current __current);

    boolean getDayLastGpsRecord(DayLastGpsGetRequest req, DayLastGpsGetResponseHolder res, Ice.Current __current);

    boolean getGpsBsRecords(GpsGetRequest req, GpsBsGetResponseHolder res, Ice.Current __current);

    boolean getFirstGpsBsRecord(GpsGetRequest req, GpsBsGetResponseHolder res, Ice.Current __current);

    boolean getLatestVolRecord(LatestVoltageGetRequest req, LatestVoltageGetResponseHolder res, Ice.Current __current);

    boolean getMaxVolDiff(DiffVoltageGetRequest req, DiffVoltageGetResponseHolder res, Ice.Current __current);

    boolean getVoltageRecords(VoltageGetRequest req, VoltageGetResponseHolder res, Ice.Current __current);

    boolean getLatestBatteryRecord(LatestBatteryGetRequest req, LatestBatteryGetResponseHolder res, Ice.Current __current);

    boolean getDataCardInfo(DataCardGetRequest req, DataCardGetResponseHolder res, Ice.Current __current);

    boolean getGpsWorkModeRecords(GpsWorkModeGetRequest req, GpsWorkModeGetResponseHolder res, Ice.Current __current);

    boolean getBsWorkModeRecords(BsWorkModeGetRequest req, BsWorkModeGetResponseHolder res, Ice.Current __current);
}
