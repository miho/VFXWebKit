/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.mihosoft.vfxwebkit;

import com.sun.javafx.sg.prism.NGExternalNode;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.WritableImage;
import javafx.scene.image.WritablePixelFormat;
import javafx.scene.layout.Region;

/**
 *
 * @author Michael Hoffer &lt;info@michaelhoffer.de&gt;
 */
class VFXWebNodeFX extends Region implements VFXWebNode {

    private final WritablePixelFormat<IntBuffer> format
            = PixelFormat.getIntArgbPreInstance();
    private WritableImage img;
    private final ImageView view = new ImageView();
    NGExternalNode node = new NGExternalNode();
    private final int key;
    private NativeBinding binding;

    public static VFXWebNodeFX newNode() {
        int key = NativeBinding.INSTANCE.nodes.keySet().size();
        while (NativeBinding.INSTANCE.nodes.keySet().contains(key)) {
            key++;
        }
        return new VFXWebNodeFX(key, NativeBinding.INSTANCE);
    }

    VFXWebNodeFX(int key, NativeBinding binding) {
        this.key = key;
        this.binding = binding;
//        binding.newPage(key);
        binding.nodes.put(key, this);
        img = new WritableImage(1024, 768);
        view.setImage(img);
        view.setFitWidth(1024);
        view.setFitHeight(768);
        getChildren().add(view);
        view.fitWidthProperty().bind(widthProperty());
        view.fitHeightProperty().bind(heightProperty());

        view.setStyle("-fx-border-color: green;");

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
//        System.out.println("JVM redraw: x1: " + x1 + ", y1: " + y1);

        
        if (!binding.isDirty(key)) {
            System.out.println("inactive: ");
            
            return;
        }
        
        binding.setDirty(key, false);
        
        IntBuffer intBuf
                = ByteBuffer.wrap(imgBuffer)
                .order(ByteOrder.LITTLE_ENDIAN)
                .asIntBuffer();
        
        int[] array = new int[intBuf.remaining()];
        intBuf.get(array);

        img.getPixelWriter().setPixels(
                0, 0, (int) img.getWidth(), (int) img.getHeight(),
                format, array, 0, (int) img.getWidth());
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
        
        
        if (!binding.isDirty(key)) {
            System.out.println("inactive: ");
            
            return;
        }
        
        binding.setDirty(key, false);
        
        IntBuffer intBuf
                = imgBuffer
                .order(ByteOrder.LITTLE_ENDIAN)
                .asIntBuffer();
        
        int[] array = new int[intBuf.remaining()];
        intBuf.get(array);

        img.getPixelWriter().setPixels(
                0, 0, (int) img.getWidth(), (int) img.getHeight(),
                format, array, 0, (int) img.getWidth());
    }

}
