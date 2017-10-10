package mj.plfs.net;

import mj.plfs.entities.Note;

import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.Date;

@Path("/")
public class HelloWorld {

    protected EntityManager getEntityManager() throws NamingException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("plfs_unit");
        return emf.createEntityManager();
    }

    @GET
    @Produces("text/plain")
    public String getClichedMessage() {
        return "Hello World  - improved ?";
    }

    @GET
    @Path("/add")
    @Produces("text/plain")
    public String addNote() {
        Note note = new Note();
        note.setText("tekst notki");
        note.setDate(new Date());

        try {
            EntityManager em = getEntityManager();
            em.getTransaction().begin();
            em.persist(note);
            em.getTransaction().commit();
        } catch (NamingException e) {
            e.printStackTrace();
        }

        return "Dodano notkę";
    }
}