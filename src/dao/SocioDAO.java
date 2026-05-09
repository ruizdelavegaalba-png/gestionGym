package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import modelo.Gimnasio;
import modelo.Socio;

import java.util.List;

public class SocioDAO {

    private EntityManagerFactory emf;

    public SocioDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    // a. Inserción de un socio
    public void insertarSocio(Socio socio) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(socio);
        em.getTransaction().commit();
        em.close();
    }

    // b. Actualización de cualquier campo de un socio (salvo ID), según su ID
    public void actualizarSocio(int id, String nombreCompleto, int edad, boolean vip) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Socio socio = em.find(Socio.class, id);
        if (socio != null) {
            socio.setNombreCompleto(nombreCompleto);
            socio.setEdad(edad);
            socio.setVip(vip);
        }
        em.getTransaction().commit();
        em.close();
    }

    // c. Borrado de un socio, según su ID
    public void borrarSocio(int id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Socio socio = em.find(Socio.class, id);
        if (socio != null) {
            // Desvinculamos el socio de todos sus gimnasios antes de borrar
            for (Gimnasio gimnasio : socio.getGimnasios()) {
                gimnasio.getSocios().remove(socio);
            }
            em.remove(socio);
        }
        em.getTransaction().commit();
        em.close();
    }

    // d. Asignar un Socio a un Gimnasio, según sus IDs
    public void asignarSocio(int idSocio, int idGimnasio) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Socio socio = em.find(Socio.class, idSocio);
        Gimnasio gimnasio = em.find(Gimnasio.class, idGimnasio);
        if (socio != null && gimnasio != null) {
            socio.getGimnasios().add(gimnasio);
            gimnasio.getSocios().add(socio);
        }
        em.getTransaction().commit();
        em.close();
    }

    // e. Borrar a un Socio de un Gimnasio, según sus IDs
    public void borrarSocioDeGimnasio(int idSocio, int idGimnasio) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Socio socio = em.find(Socio.class, idSocio);
        Gimnasio gimnasio = em.find(Gimnasio.class, idGimnasio);
        if (socio != null && gimnasio != null) {
            socio.getGimnasios().remove(gimnasio);
            gimnasio.getSocios().remove(socio);
        }
        em.getTransaction().commit();
        em.close();
    }

    // f. Obtener los Gimnasio a los que está apuntado un socio concreto, según su ID
    public List<Gimnasio> obtenerGimnasios(int id) {
        EntityManager em = emf.createEntityManager();
        Socio socio = em.find(Socio.class, id);
        List<Gimnasio> gimnasios = socio.getGimnasios();
        gimnasios.toString(); // fuerza la carga lazy
        em.close();
        return gimnasios;
    }

    // g. Obtener la media de edad de todos los socios
    public double obtenerMediaEdad() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Double> query = em.createQuery(
                "select avg(s.edad) from Socio s",
                Double.class);
        double media = query.getSingleResult();
        em.close();
        return media;
    }

    // h. Obtener los Socio que no están inscritos en ningún gimnasio
    public List<Socio> obtenerSociosSinGimnasio() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Socio> query = em.createQuery(
                "select s from Socio s left join s.gimnasios g group by s having count(g) = 0",
                Socio.class);
        List<Socio> socios = query.getResultList();
        em.close();
        return socios;
    }
}