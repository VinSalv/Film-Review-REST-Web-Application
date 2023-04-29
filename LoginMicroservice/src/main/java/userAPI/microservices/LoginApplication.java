package userAPI.microservices;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * Server Main process.
 * Exposes the Resources, making them accessible via the specified path
 */
@ApplicationPath("/api-login")
public class LoginApplication extends Application {
}
