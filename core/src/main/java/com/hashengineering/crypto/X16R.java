package com.hashengineering.crypto;

import org.bitcoinj.core.Sha256Hash;

import fr.cryptohash.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class X16R {
    private static final Logger log = LoggerFactory.getLogger(X16R.class);
    private static boolean native_library_loaded = false;

    static {
        try {
            log.info("Loading x16r native library...");
            System.loadLibrary("x16r");
            native_library_loaded = true;
            log.info("Loaded x16r successfully.");
        }
        catch(UnsatisfiedLinkError x)
	    {
		log.info("Loading x16r failed: " + x.getMessage());
	    }
        catch(Exception e)
	    {
		native_library_loaded = false;
		log.info("Loading x16r failed: " + e.getMessage());
	    }
    }

    static native byte [] x16r_native(byte [] input);

    public static byte[] x16rDigest(byte[] input, int offset, int length)
    {
        byte [] buf = new byte[length];
        for(int i = 0; i < length; ++i)
        {
            buf[i] = input[offset + i];
        }
        return x16rDigest(buf);
    }

    
    public static byte[] x16rDigest(byte[] input) {
        //long start = System.currentTimeMillis();
        try {
            return x16r_native(input);
        
	} catch (Exception e) {
            return null;
        }
    }
}
