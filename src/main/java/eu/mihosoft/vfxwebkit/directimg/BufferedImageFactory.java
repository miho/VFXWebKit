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
import java.awt.image.DataBuffer;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.nio.ByteBuffer;

/**
 * Provides static factory methods to create {@link BufferedImage}s.
 * 
 * @author David Yazel
 * @author Marvin Froehlich (aka Qudus)
 */
public class BufferedImageFactory
{
    public static BufferedImage createCustomRGB( int width, int height )
    {
        ColorSpace cs = ColorSpace.getInstance( ColorSpace.CS_sRGB );
        int[] nBits = { 8, 8, 8 };
        ColorModel cm = new ComponentColorModel( cs, nBits, false, false, Transparency.OPAQUE, 0 );
        int[] bandOffset = { 0, 1, 2 };
        
        WritableRaster newRaster = Raster.createInterleavedRaster( DataBuffer.TYPE_BYTE, width, height, width * 3, 3, bandOffset, null );
        BufferedImage newImage = new BufferedImage( cm, newRaster, false, null );
        
        return ( newImage );
    }
    
    public static BufferedImage createCustomRGBA( int width, int height )
    {
        ColorSpace cs = ColorSpace.getInstance( ColorSpace.CS_sRGB );
        int[] nBits = { 8, 8, 8, 8 };
        ColorModel cm = new ComponentColorModel( cs, nBits, true, false, Transparency.OPAQUE, 0 );
        int[] bandOffset = { 0, 1, 2, 3 };
        
        WritableRaster newRaster = Raster.createInterleavedRaster( DataBuffer.TYPE_BYTE, width, height, width * 4, 4, bandOffset, null );
        BufferedImage newImage = new BufferedImage( cm, newRaster, false, null );
        
        return ( newImage );
    }
    
    public static SharedBufferedImage createSharedBufferedImage( int width, int height, int pixelSize, boolean hasAlpha, int[] pixelOffsets, byte[] data )
    {
        return ( SharedBufferedImage.create( width, height, pixelSize, hasAlpha, pixelOffsets, data ) );
    }
    
    public static final SharedBufferedImage createSharedBufferedImage( int width, int height, int pixelSize, int[] pixelOffsets, byte[] data )
    {
        return ( SharedBufferedImage.create( width, height, pixelSize, pixelOffsets, data ) );
    }
    
    public static DirectBufferedImage createDirectBufferedImage( int width, int height, boolean hasAlpha, int[] pixelOffsets, ByteBuffer bb )
    {
        if ( hasAlpha )
            return ( DirectBufferedImage.makeDirectImageRGBA( width, height, pixelOffsets, bb ) );
        
        return ( DirectBufferedImage.makeDirectImageRGB( width, height, pixelOffsets, bb ) );
    }
}