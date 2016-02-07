/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.mihosoft.vfxwebkit;

import eu.mihosoft.vfxwebkit.directimg.BufferedImageFactory;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.color.ColorSpace;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelInterleavedSampleModel;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import javafx.animation.AnimationTimer;
import javafx.embed.swing.SwingNode;
import javafx.scene.Node;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author Michael Hoffer &lt;info@michaelhoffer.de&gt;
 */
class VFXWebNodeSwing extends SwingNode implements VFXWebNode {

    private final int key;
    private NativeBinding binding;

    public static VFXWebNodeSwing newNode() {
        int key = NativeBinding.INSTANCE.nodes.keySet().size();
        while (NativeBinding.INSTANCE.nodes.keySet().contains(key)) {
            key++;
        }
        return new VFXWebNodeSwing(key, NativeBinding.INSTANCE);
    }
//    private MemoryImageSource mis;
    private Image img;
    private JPanel p;
//    private final PixelInterleavedSampleModel cm;

    VFXWebNodeSwing(int key, NativeBinding binding) {
        this.key = key;
        this.binding = binding;
//        binding.newPage(key);
        binding.nodes.put(key, this);

////        mis = new MemoryImageSource(
////                1024, 768, ColorModel.getRGBdefault(),
////                this.binding.pageBuffer(key), 0, 1024);
//        img = Toolkit.getDefaultToolkit().createImage(mis);
//
//        cm = new PixelInterleavedSampleModel(
//                DataBuffer.TYPE_BYTE,
//                1024, 768,
//                4, 4 * (1024),
//                new int[]{2, 1, 0, 3});

        SwingUtilities.invokeLater(() -> {
            p = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
//                    super.paintComponent(g);
                    if (img != null) {
                        g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
                    }
                }
            };

            p.setSize(1024, 768);

            this.setContent(p);
        });

        AnimationTimer redrawTimer = new AnimationTimer() {

            @Override
            public void handle(long now) {
                redraw(binding.pageBufferDirect(key), 0, 0, 1024, 768);
            }
        };

        redrawTimer.start();

        boundsInLocalProperty().addListener((ov, oldV, newV) -> {
//            System.out.println("JVM: draw begin");
            if (newV == null) {
                return;
            }
            int w = (int) newV.getWidth();
            int h = (int) newV.getHeight();

            if (w < 1 || h < 1) {
                return;
            }

            if (binding.isDirty(key)) {
                redraw(binding.pageBufferDirect(key), 0, 0, 1024, 768);

                binding.setDirty(key, false);
            }

//            binding.resizePage(key, w, h);
//            resize(binding.pageBuffer(key), w, h);
//            System.out.println("JVM: draw end");
        });

        setStyle("-fx-border-color: red;");
    }

    @Override
    public void redraw(byte[] imgBuffer, int x1, int y1, int w, int h) {
        throw new UnsupportedOperationException(
                "Raw arrays not supported. Use byte buffers instead.");
////        System.out.println("JVM redraw: x1: " + x1 + ", y1: " + y1);
//
//        if (!binding.isDirty(key)) {
//            System.out.println("inactive: ");
//
//            return;
//        }
//
//        binding.setDirty(key, false);
//
////        IntBuffer intBuf
////                = ByteBuffer.wrap(imgBuffer)
////                .order(ByteOrder.LITTLE_ENDIAN)
////                .asIntBuffer();
////
////        int[] array = new int[intBuf.remaining()];
////        intBuf.get(array);
//
//        if (p == null) {
//            return;
//        }
//
////        mis = new MemoryImageSource(
////                1024, 768,
////                cm,
////                imgBuffer, 0, 1024*4);
//
//        DataBuffer dbuf = new DataBufferByte(imgBuffer, imgBuffer.length);
//
//        // we now construct an image raster with sample model (see above)
//        WritableRaster raster =
//                new ByteInterleavedRaster(cm, dbuf, new Point(0, 0));
//
//        img = img = new BufferedImage(createColorModel(), raster, false, null);
//
//        p.repaint();

    }

    private static ColorModel createColorModel() {
        ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_sRGB);
        int[] nBits = {8, 8, 8, 8};

        return new ComponentColorModel(cs, nBits, true, false,
                Transparency.TRANSLUCENT,
                DataBuffer.TYPE_BYTE);
    }

    @Override
    public void resize(byte[] imgBuf, int w, int h) {
        System.out.println("JVM resize: w: " + w + ", h: " + h);
//
//        if (w < 1 || h < 1) {
//            return;
//        }
//
//        img = new WritableImage(w, h);
//        view.setImage(img);
        redraw(imgBuf, 0, 0, w, h);
    }

    /**
     * @return the key
     */
    @Override
    public int getKey() {
        return key;
    }

    @Override
    public Node getNode() {
        return this;
    }

    @Override
    public void redraw(ByteBuffer imgBuffer, int x1, int y1, int w, int h) {
        if (img == null) {
            img = BufferedImageFactory.
                    createDirectBufferedImage(1024, 768, true, new int[]{2, 1, 0, 3},
                            imgBuffer);
        }
        if (p != null) {
            p.repaint();
        }
    }

}
