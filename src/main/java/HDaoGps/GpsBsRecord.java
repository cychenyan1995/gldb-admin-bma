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

/**
 * Gps&BS Record
 **/
public class GpsBsRecord implements java.lang.Cloneable, java.io.Serializable
{
    public int gpsTime;

    public int storeTime;

    public boolean gpsValid;

    public int lng;

    public int lat;

    public int gLng;

    public int gLat;

    public int bLng;

    public int bLat;

    public int alt;

    public int speed;

    public int dir;

    public boolean bsValid;

    public int mcc;

    public int mnc;

    public BsInfo[] bsInfos;

    public GpsBsRecord()
    {
    }

    public GpsBsRecord(int gpsTime, int storeTime, boolean gpsValid, int lng, int lat, int gLng, int gLat, int bLng, int bLat, int alt, int speed, int dir, boolean bsValid, int mcc, int mnc, BsInfo[] bsInfos)
    {
        this.gpsTime = gpsTime;
        this.storeTime = storeTime;
        this.gpsValid = gpsValid;
        this.lng = lng;
        this.lat = lat;
        this.gLng = gLng;
        this.gLat = gLat;
        this.bLng = bLng;
        this.bLat = bLat;
        this.alt = alt;
        this.speed = speed;
        this.dir = dir;
        this.bsValid = bsValid;
        this.mcc = mcc;
        this.mnc = mnc;
        this.bsInfos = bsInfos;
    }

    public boolean
    equals(java.lang.Object rhs)
    {
        if(this == rhs)
        {
            return true;
        }
        GpsBsRecord _r = null;
        if(rhs instanceof GpsBsRecord)
        {
            _r = (GpsBsRecord)rhs;
        }

        if(_r != null)
        {
            if(gpsTime != _r.gpsTime)
            {
                return false;
            }
            if(storeTime != _r.storeTime)
            {
                return false;
            }
            if(gpsValid != _r.gpsValid)
            {
                return false;
            }
            if(lng != _r.lng)
            {
                return false;
            }
            if(lat != _r.lat)
            {
                return false;
            }
            if(gLng != _r.gLng)
            {
                return false;
            }
            if(gLat != _r.gLat)
            {
                return false;
            }
            if(bLng != _r.bLng)
            {
                return false;
            }
            if(bLat != _r.bLat)
            {
                return false;
            }
            if(alt != _r.alt)
            {
                return false;
            }
            if(speed != _r.speed)
            {
                return false;
            }
            if(dir != _r.dir)
            {
                return false;
            }
            if(bsValid != _r.bsValid)
            {
                return false;
            }
            if(mcc != _r.mcc)
            {
                return false;
            }
            if(mnc != _r.mnc)
            {
                return false;
            }
            if(!java.util.Arrays.equals(bsInfos, _r.bsInfos))
            {
                return false;
            }

            return true;
        }

        return false;
    }

    public int
    hashCode()
    {
        int __h = 5381;
        __h = IceInternal.HashUtil.hashAdd(__h, "::HDaoGps::GpsBsRecord");
        __h = IceInternal.HashUtil.hashAdd(__h, gpsTime);
        __h = IceInternal.HashUtil.hashAdd(__h, storeTime);
        __h = IceInternal.HashUtil.hashAdd(__h, gpsValid);
        __h = IceInternal.HashUtil.hashAdd(__h, lng);
        __h = IceInternal.HashUtil.hashAdd(__h, lat);
        __h = IceInternal.HashUtil.hashAdd(__h, gLng);
        __h = IceInternal.HashUtil.hashAdd(__h, gLat);
        __h = IceInternal.HashUtil.hashAdd(__h, bLng);
        __h = IceInternal.HashUtil.hashAdd(__h, bLat);
        __h = IceInternal.HashUtil.hashAdd(__h, alt);
        __h = IceInternal.HashUtil.hashAdd(__h, speed);
        __h = IceInternal.HashUtil.hashAdd(__h, dir);
        __h = IceInternal.HashUtil.hashAdd(__h, bsValid);
        __h = IceInternal.HashUtil.hashAdd(__h, mcc);
        __h = IceInternal.HashUtil.hashAdd(__h, mnc);
        __h = IceInternal.HashUtil.hashAdd(__h, bsInfos);
        return __h;
    }

    public java.lang.Object
    clone()
    {
        java.lang.Object o = null;
        try
        {
            o = super.clone();
        }
        catch(CloneNotSupportedException ex)
        {
            assert false; // impossible
        }
        return o;
    }

    public void
    __write(IceInternal.BasicStream __os)
    {
        __os.writeInt(gpsTime);
        __os.writeInt(storeTime);
        __os.writeBool(gpsValid);
        __os.writeInt(lng);
        __os.writeInt(lat);
        __os.writeInt(gLng);
        __os.writeInt(gLat);
        __os.writeInt(bLng);
        __os.writeInt(bLat);
        __os.writeInt(alt);
        __os.writeInt(speed);
        __os.writeInt(dir);
        __os.writeBool(bsValid);
        __os.writeInt(mcc);
        __os.writeInt(mnc);
        BsInfoArrayHelper.write(__os, bsInfos);
    }

    public void
    __read(IceInternal.BasicStream __is)
    {
        gpsTime = __is.readInt();
        storeTime = __is.readInt();
        gpsValid = __is.readBool();
        lng = __is.readInt();
        lat = __is.readInt();
        gLng = __is.readInt();
        gLat = __is.readInt();
        bLng = __is.readInt();
        bLat = __is.readInt();
        alt = __is.readInt();
        speed = __is.readInt();
        dir = __is.readInt();
        bsValid = __is.readBool();
        mcc = __is.readInt();
        mnc = __is.readInt();
        bsInfos = BsInfoArrayHelper.read(__is);
    }

    public static final long serialVersionUID = 815259239L;
}