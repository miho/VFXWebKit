package eu.mihosoft.vfxwebkit;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.image.PixelFormat;

/**
 *
 * @author Michael Hoffer &lt;info@michaelhoffer.de&gt;
 */
public enum NativeBinding {
    
    INSTANCE;
    
    final Map<Integer, VFXWebNode> nodes;

    private NativeBinding() {
        this.nodes = new HashMap<>();
    }
//
//    native void init();
//
//    native void newPage(int key);
//
//    native void deletePage(int key);

    native byte[] pageBuffer(int key);
    
//
//    native int pageBufferSize(int key);
//
//    native void setSize(int key, int w, int h);
//
//    native int getSizeX(int key);
//
//    native int getSizeW(int key);
//    
//    native void resizePage(int key, int w, int h);

    public void redraw(int key, int x1, int y1, int w, int h) {
        System.out.println("JVM: redraw x1: " + x1 + ", y1: " + y1);
        
        VFXWebNode node = nodes.get(key);
        
        byte[] buffer = pageBuffer(key);
        
        node.redraw(buffer, x1, y1, w, h);
        System.out.println("- JVM: end -");
    }

    native boolean isDirty(int key);
    native void setDirty(int key, boolean value);
    
    native void load(String url);

}
