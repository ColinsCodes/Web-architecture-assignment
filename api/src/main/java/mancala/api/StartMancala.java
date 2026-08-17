package mancala.api;

import jakarta.servlet.http.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import mancala.api.models.*;
import mancala.domain.Playable;

@Path("/start")
public class StartMancala {
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response start(
			@Context HttpServletRequest request,
			PlayerInputDTO players) {
		// Create HTTP session.
		HttpSession session = request.getSession(true);

		// Initialize game.
		Playable mancala = null;

		// Attach game to session.
		session.setAttribute("mancala", mancala);

		// Use the game to create a DTO.
		var output = new MancalaDTO(mancala);

		// Send DTO back in response.
		return Response.status(200).entity(output).build();
	}
}
