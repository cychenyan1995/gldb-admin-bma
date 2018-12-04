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

public class BatteryRecord implements java.lang.Cloneable, java.io.Serializable
{
    public int gpsTime;

    public int battery;

    public int storeTime;

    public BatteryRecord()
    {
    }

    public BatteryRecord(int gpsTime, int battery, int storeTime)
    {
        this.gpsTime = gpsTime;
        this.battery = battery;
        this.storeTime = storeTime;
    }

    public boolean
    equals(java.lang.Object rhs)
    {
        if(this == rhs)
        {
            return true;
        }
        BatteryRecord _r = null;
        if(rhs instanceof BatteryRecord)
        {
            _r = (BatteryRecord)rhs;
        }

        if(_r != null)
        {
            if(gpsTime != _r.gpsTime)
            {
                return false;
            }
            if(battery != _r.battery)
            {
                return false;
            }
            if(storeTime != _r.storeTime)
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
        __h = IceInternal.HashUtil.hashAdd(__h, "::HDaoGps::BatteryRecord");
        __h = IceInternal.HashUtil.hashAdd(__h, gpsTime);
        __h = IceInternal.HashUtil.hashAdd(__h, battery);
        __h = IceInternal.HashUtil.hashAdd(__h, storeTime);
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
        __os.writeInt(battery);
        __os.writeInt(storeTime);
    }

    public void
    __read(IceInternal.BasicStream __is)
    {
        gpsTime = __is.readInt();
        battery = __is.readInt();
        storeTime = __is.readInt();
    }

    public static final long serialVersionUID = 1356062620L;
}
