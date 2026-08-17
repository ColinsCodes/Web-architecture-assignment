package mancala.api;

import jakarta.servlet.http.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import mancala.api.models.*;
import mancala.domain.Playable;

@Path("/play")
public class PlayMancala {
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response play(
            @Context HttpServletRequest request) {
        // Retrieve HTTP session.

        // Retrieve game.

        // Play a pit.

        // Use the game to create a DTO.
        MancalaDTO output = null;

        // Send DTO back in response.
        return Response.status(200).entity(output).build();
    }
}
