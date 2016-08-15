/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.mihosoft.vfxwebkit;

import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.sg.prism.NGExternalNode;
import com.sun.javafx.sg.prism.NGNode;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.embed.swing.SwingNode;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import jfxtras.scene.control.window.Window;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Michael Hoffer &lt;info@michaelhoffer.de&gt;
 */
public class DemoApplication extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        Path tmpFile = null;
        try {
            tmpFile = Files.createTempDirectory("eu.mihosoft.vfxwebkit");
        } catch (IOException ex) {
            Logger.getLogger(DemoApplication.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }
        
        try {
            FileUtils.copyDirectory(
                    new File("src/main/resources/eu/mihosoft/vfxwebkit/"),
                    tmpFile.toFile());
//            FileUtils.copyDirectory(
//                    new File("/Users/miho/Qt/qt/5.5/clang_64/lib/"),
//                    new File(tmpFile.toFile(),"native/osx/"));
        } catch (IOException ex) {
            Logger.getLogger(DemoApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        File libraryPath = new File(tmpFile.toFile(),
                "native/osx/libvfxwebkit.1.0.0.dylib");
        
        System.load(libraryPath.getAbsolutePath());
        
//        new Thread(()->{NativeBinding.INSTANCE.init();}).start();
        
//        NativeBinding.INSTANCE.init();
 
        VFXWebNode webView = VFXWebNode.newInstance(VFXWebNode.NodeType.JFX_DIRECT_BUFFER);
//        webView.getEngine().load("http://learningwebgl.com/lessons/lesson04/index.html");

        Window w = new Window("Qt WebKit & WebGL inside JavaFX");
        w.setPrefSize(600, 440);
        
        w.getContentPane().getChildren().add(webView.getNode());
        
//        Window w2 = new Window("JavaFX WebView");
//        w2.setLayoutX(620);
//        w2.setPrefSize(600, 440);
//        
//        WebView fxview = new WebView();
//        fxview.getEngine().load("http://learningwebgl.com/lessons/lesson04/index.html");
//        
//        w2.getContentPane().getChildren().add(fxview);
        
        Pane root = new Pane();
        root.getChildren().add(w);
        
        
        
        Scene scene = new Scene(root, 1280, 1024);
        
        primaryStage.setTitle("Hello Native Qt, hello WebGL!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
