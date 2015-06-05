/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.mihosoft.vfxwebkit;

import java.io.File;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import jfxtras.scene.control.window.Window;

/**
 *
 * @author Michael Hoffer &lt;info@michaelhoffer.de&gt;
 */
public class DemoApplication extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        
        File libraryPath = new File(
                "src/main/resources/eu/mihosoft/vfxwebkit/native/osx/libvfxwebkit.1.0.0.dylib");
        
        System.load(libraryPath.getAbsolutePath());
        
//        new Thread(()->{NativeBinding.INSTANCE.init();}).start();
        
//        NativeBinding.INSTANCE.init();
        
        VFXWebNode webView = VFXWebNode.newNode();
//        webView.getEngine().load("http://learningwebgl.com/lessons/lesson04/index.html");
       
        Window w = new Window("Qt WebKit & WebGL");
        w.setPrefSize(600, 440);
        
        w.getContentPane().getChildren().add(webView);
        
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
