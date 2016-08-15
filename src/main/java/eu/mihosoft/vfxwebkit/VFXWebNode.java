/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.mihosoft.vfxwebkit;

import java.nio.ByteBuffer;
import javafx.scene.Node;


/**
 *
 * @author Michael Hoffer &lt;info@michaelhoffer.de&gt;
 */
public interface VFXWebNode {
    
    static VFXWebNode newInstance(NodeType type) {
        switch(type) {
            case JFX_COPY:
                return VFXWebNodeFX.newNode(false);
            case JFX_DIRECT_BUFFER:
                return VFXWebNodeFX.newNode(true);
            case SWING:
                return VFXWebNodeSwing.newNode();
            default: throw new RuntimeException("Type unsupported: " + type);
        }
    }

    /**
     * @return the key
     */
    int getKey();

    void redraw(byte[] imgBuffer, int x1, int y1, int w, int h);
    void redraw(ByteBuffer imgBuffer, int x1, int y1, int w, int h);

    void resize(byte[] imgBuf, int w, int h);
    
    Node getNode();
    
    enum NodeType {
        JFX_COPY,
        JFX_DIRECT_BUFFER,
        SWING
    }
    
}
