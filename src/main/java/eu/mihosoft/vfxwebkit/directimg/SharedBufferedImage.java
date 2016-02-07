/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.mihosoft.vfxwebkit.directimg;

/**
 * Copyright (c) 2007-2009, JAGaToo Project Group all rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * 
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * 
 * Neither the name of the 'Xith3D Project Group' nor the names of its
 * contributors may be used to endorse or promote products derived from this
 * software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) A
 * RISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE
 */

import java.awt.Transparency;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;

/**
 * This is a simple {@link BufferedImage} extension, that uses
 * a shared byte-array as data container.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public class SharedBufferedImage extends BufferedImage
{
    private final int pixelSize;
    private final int[] pixelOffsets;
    
    private final byte[] data;
    
    public final int getPixelSize()
    {
        return ( pixelSize );
    }
    
    public final int[] getPixelOffsets()
    {
        return ( pixelOffsets );
    }
    
    public final byte[] getSharedData()
    {
        return ( data );
    }
    
    protected SharedBufferedImage( int width, int height, ColorModel cm, DataBufferByte dbb, byte[] data, int scanlineStride, int pixelStride, int[] pixelOffsets )
    {
        super( cm, Raster.createInterleavedRaster( dbb, width, height, scanlineStride, pixelStride, pixelOffsets, new java.awt.Point( 0, 0 ) ), false, null );
        
        this.pixelSize = scanlineStride / width;
        this.pixelOffsets = pixelOffsets;
        
        this.data = data;
    }
    
    public static SharedBufferedImage create( int width, int height, int pixelSize, boolean hasAlpha, int[] pixelOffsets, byte[] data )
    {
        int scanlineStride;
        int pixelStride;
        int[] nBits;
        ColorSpace cs;
        ColorModel cm;
        
        switch ( pixelSize )
        {
            case 4:
                scanlineStride = width * 4;
                pixelStride = 4;
                if ( pixelOffsets == null )
                    pixelOffsets = new int[] { 3, 2, 1, 0 };
                nBits = new int[] { 8, 8, 8, 8 };
                cs = ColorSpace.getInstance( ColorSpace.CS_sRGB );
                cm = new ComponentColorModel( cs, nBits, true, false, Transparency.TRANSLUCENT, 0 );
                break;
            case 3:
                scanlineStride = width * 3;
                pixelStride = 3;
                if ( pixelOffsets == null )
                    pixelOffsets = new int[] { 2, 1, 0 };
                nBits = new int[] { 8, 8, 8 };
                cs = ColorSpace.getInstance( ColorSpace.CS_sRGB );
                cm = new ComponentColorModel( cs, nBits, false, false, Transparency.OPAQUE, 0 );
                break;
            case 2:
                scanlineStride = width * 2;
                pixelStride = 2;
                if ( pixelOffsets == null )
                    pixelOffsets = new int[] { 1, 0 };
                nBits = new int[] { 8, 8 };
                cs = ColorSpace.getInstance( ColorSpace.CS_sRGB );
                cm = new ComponentColorModel( cs, nBits, false, false, Transparency.TRANSLUCENT, 0 );
                break;
            case 1:
                scanlineStride = width * 1;
                pixelStride = 1;
                if ( pixelOffsets == null )
                    pixelOffsets = new int[] { 0 };
                nBits = new int[] { 8 };
                
                cs = ColorSpace.getInstance( ColorSpace.CS_GRAY );
                
                if ( hasAlpha )
                    cm = new ComponentColorModel( cs, nBits, false, false, Transparency.TRANSLUCENT, 0 );
                else
                    cm = new ComponentColorModel( cs, nBits, false, false, Transparency.OPAQUE, 0 );
                break;
            default:
                throw new IllegalArgumentException( "Unsupported pixelSize: " + pixelSize );
        }
        
        if ( data == null )
        {
            data = new byte[ width * height * pixelSize ];
        }
        
        DataBufferByte dbb = new DataBufferByte( data, data.length );
        
        return ( new SharedBufferedImage( width, height, cm, dbb, data, scanlineStride, pixelStride, pixelOffsets ) );
    }
    
    public static final SharedBufferedImage create( int width, int height, int pixelSize, int[] pixelOffsets, byte[] data )
    {
        Boolean hasAlpha = null;
        
        switch ( pixelSize )
        {
            case 4:
                hasAlpha = Boolean.TRUE;
                break;
            case 3:
                hasAlpha = Boolean.FALSE;
                break;
            case 2:
                hasAlpha = Boolean.TRUE;
                break;
            case 1:
                hasAlpha = Boolean.FALSE;
                break;
        }
        
        if ( hasAlpha == null )
            throw new IllegalArgumentException( "Unsupported pixelSize: " + pixelSize );
        
        return ( create( width, height, pixelSize, hasAlpha.booleanValue(), pixelOffsets, data ) );
    }
}