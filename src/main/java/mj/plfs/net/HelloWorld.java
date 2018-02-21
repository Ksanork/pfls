package mj.plfs.net;

import mj.plfs.entities.Note;

import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

@Path("/")
public class HelloWorld {
    private boolean isDebug = true;

    protected EntityManager getEntityManager() throws NamingException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("plfs_unit");
        return emf.createEntityManager();
    }


    @GET
    @Path("/hello")
    @Produces("text/html")
    public String getIndex() {
        return "Hello World  - improved ?";
    }


    @GET
    @Path("/showAll")
    @Produces("text/html")
    public String getNotes() {
        String notes = "";
        EntityManager em = null;
        try {
            em = getEntityManager();
            TypedQuery<Note> query = em.createNamedQuery("Note.showAll", Note.class);
            List<Note> results = query.getResultList();
            for(Note n : results) {
                notes += "<b>" + n.getText() + "</b> " +
                        "<i>" + n.getDate() + "</i><br />";
            }
        } catch (NamingException e) {
            e.printStackTrace();
        }

        return notes;
    }

    @GET
    @Path("/add/{text}")
    @Produces("text/plain")
    public String addNote(@PathParam("text") String text) {
        Date date = new Date();
        Note note = new Note();
        note.setDate(date);
        note.setText(text);

        try {
            EntityManager em = getEntityManager();
            em.getTransaction().begin();
            em.persist(note);
            em.getTransaction().commit();
        } catch (NamingException e) {
            e.printStackTrace();
        }

        return "Dodano notkę - " + date + ", " + text;
    }
}