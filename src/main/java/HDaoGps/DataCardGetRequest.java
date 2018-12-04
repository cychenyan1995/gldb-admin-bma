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
 * SIM card's related record
 **/
public class DataCardGetRequest implements java.lang.Cloneable, java.io.Serializable
{
    public long userID;

    public int apType;

    public DataCardGetRequest()
    {
    }

    public DataCardGetRequest(long userID, int apType)
    {
        this.userID = userID;
        this.apType = apType;
    }

    public boolean
    equals(java.lang.Object rhs)
    {
        if(this == rhs)
        {
            return true;
        }
        DataCardGetRequest _r = null;
        if(rhs instanceof DataCardGetRequest)
        {
            _r = (DataCardGetRequest)rhs;
        }

        if(_r != null)
        {
            if(userID != _r.userID)
            {
                return false;
            }
            if(apType != _r.apType)
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
        __h = IceInternal.HashUtil.hashAdd(__h, "::HDaoGps::DataCardGetRequest");
        __h = IceInternal.HashUtil.hashAdd(__h, userID);
        __h = IceInternal.HashUtil.hashAdd(__h, apType);
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
        __os.writeLong(userID);
        __os.writeInt(apType);
    }

    public void
    __read(IceInternal.BasicStream __is)
    {
        userID = __is.readLong();
        apType = __is.readInt();
    }

    public static final long serialVersionUID = -129758277L;
}
