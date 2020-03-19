package org.acme;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
public class ExampleResource {

	@Path("hello")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "hello";
    }
    
	@Path("errors")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Error> getError() {
		return Error.listAll();
	}
    
}