package mj.plfs;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.sun.grizzly.http.SelectorThread;
import com.sun.jersey.api.container.grizzly.GrizzlyServerFactory;
import com.sun.jersey.api.container.grizzly.GrizzlyWebContainerFactory;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.net.httpserver.HttpServer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

import javax.ws.rs.core.UriBuilder;

public class AppMain {

    public static void main(String[] args) throws IOException {
//        final String baseUri = "http://localhost:" + (System.getenv("PORT") != null ? System.getenv("PORT") : "9998")+"/";
//        final Map<String, String> initParams = new HashMap<String, String>();
////        initParams.put("com.sun.jersey.config.property.packages", "mj.plfs.net");
//
//        System.out.println("Starting grizzly...");
//        SelectorThread threadSelector = GrizzlyWebContainerFactory.create(baseUri, initParams);
//        System.out.println(String.format("Jersey started with %s.", baseUri));

        String webappDirLocation = "src/main/webapp/";

        // The port that we should run on can be set into an environment variable
        // Look for that variable and default to 8080 if it isn't there.
        String webPort = System.getenv("PORT");
        if (webPort == null || webPort.isEmpty()) {
            webPort = "8080";
        }

        Server server = new Server(Integer.valueOf(webPort));
        WebAppContext root = new WebAppContext();

        root.setContextPath("/");
//        root.setDescriptor(webappDirLocation + "/WEB-INF/web.xml");
        root.setResourceBase(webappDirLocation);

        // Parent loader priority is a class loader setting that Jetty accepts.
        // By default Jetty will behave like most web containers in that it will
        // allow your application to replace non-server libraries that are part of the
        // container. Setting parent loader priority to true changes this behavior.
        // Read more here: http://wiki.eclipse.org/Jetty/Reference/Jetty_Classloading
        root.setParentLoaderPriority(true);

        server.setHandler(root);

        try {
            server.start();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}