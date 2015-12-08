package se.acrend.akkahttp;

import akka.actor.ActorSystem;
import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.model.ContentTypes;
import akka.http.javadsl.server.*;
import akka.http.javadsl.server.values.Parameters;
import org.flywaydb.core.Flyway;

import java.io.IOException;

/**
 *
 */
public class AkkaHttpServer extends HttpApp {

    public static void main(String[] args) throws IOException {
        // boot up server using the route as defined below
        ActorSystem system = ActorSystem.create();

        // HttpApp.bindRoute expects a route being provided by HttpApp.createRoute
        new AkkaHttpServer().bindRoute("0.0.0.0", 8080, system);
        System.out.println("System started");

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Flyway flyway = new Flyway();
        flyway.setDataSource("jdbc:mysql://db:3306/test_db", "test_user", "test_pass");

        final int result = flyway.migrate();
        System.out.println("Applied migrations: " + result);

    }

    // A RequestVal is a type-safe representation of some aspect of the request.
    // In this case it represents the `name` URI parameter of type String.
    private RequestVal<String> name = Parameters.stringValue("name").withDefault("Mister X");

    @Override
    public Route createRoute() {

        return
                // here the complete behavior for this server is defined
                route(
                        // only handle GET requests
                        get(
                                // matches the empty path
                                pathSingleSlash().route(
                                        // return a constant string with a certain content type
                                        complete(ContentTypes.TEXT_HTML,
                                                "<html><body>Hello world!</body></html>")
                                ),
                                path("ping").route(
                                        // return a simple `text/plain` response
                                        complete("PONG!")
                                ),
                                path("hello").route(
                                        handleWith1(name, (ctx, value) -> ctx.complete("Hello " + value + "!"))
                                ),
                                path("json").route(
                                        handleWith(ctx -> {

                                                    TestObject o = new TestObject();
                                                    o.name = "Brand";
                                                    o.value = "Volvo";
                                                    return ctx.completeAs(Jackson.json(), o);
                                                }
                                        )
                                )
                        ));
    }

    class TestObject {
        String name;
        String value;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

}
