package com;

import model.Reader;
//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
//For JSON
import com.google.gson.*;
//For XML
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

@Path("/Reader")
public class ReaderDetails
{
		Reader readerObj = new Reader();
		
		@GET
		@Path("/")
		@Produces(MediaType.TEXT_HTML)
		public String readItems()
		{
				return readerObj.readReader();
		}

		@POST
		@Path("/")
		@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
		@Produces(MediaType.TEXT_PLAIN)
		public String insertReader(@FormParam("readerName") String readerName,
							@FormParam("readerEmail") String readerEmail,
							@FormParam("readerPhone") String readerPhone,
							@FormParam("readerPassword") String readerPassword)
		{
				String output = readerObj.insertReader(readerName, readerEmail, readerPhone, readerPassword);
				return output;
		}

		@PUT
		@Path("/")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.TEXT_PLAIN)
		public String updateReader(String readerData)
		{
				//Convert the input string to a JSON object
				JsonObject readerObject = new JsonParser().parse(readerData).getAsJsonObject();
				
				//Read the values from the JSON object
				String readerID = readerObject.get("readerID").getAsString();
				String readerName = readerObject.get("readerName").getAsString();
				String readerEmail = readerObject.get("readerEmail").getAsString();
				String readerPhone = readerObject.get("readerPhone").getAsString();
				String readerPassword = readerObject.get("readerPassword").getAsString();
				
				String output = readerObj.updateReader(readerID, readerName, readerEmail, readerPhone, readerPassword);
				
				return output;
		}

		@DELETE
		@Path("/")
		@Consumes(MediaType.APPLICATION_XML)
		@Produces(MediaType.TEXT_PLAIN)
		public String deleteReader(String readerData)
		{
				//Convert the input string to an XML document
				Document doc = Jsoup.parse(readerData, "", Parser.xmlParser());
				
				//Read the value from the element <readerID>
				String readerID = doc.select("readerID").text();
				
				String output = readerObj.deleteReader(readerID);
				
				return output;
		}

}
