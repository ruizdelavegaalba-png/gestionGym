package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import modelo.Gimnasio;
import modelo.Socio;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GimnasioDAO {

    private EntityManagerFactory emf;

    public GimnasioDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    // a. Inserción de un gimnasio
    public void insertarGimnasio(Gimnasio gimnasio) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(gimnasio);
        em.getTransaction().commit();
        em.close();
    }

    // b. Actualización de cualquier campo de un gimnasio (salvo ID), según su ID
    public void actualizarGimnasio(int id, String nombre, String ciudad, double cuotaMensual) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Gimnasio gimnasio = em.find(Gimnasio.class, id);
        if (gimnasio != null) {
            gimnasio.setNombre(nombre);
            gimnasio.setCiudad(ciudad);
            gimnasio.setCuotaMensual(cuotaMensual);
        }
        em.getTransaction().commit();
        em.close();
    }

    // c. Borrado de un gimnasio, según su ID
    public void borrarGimnasio(int id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Gimnasio gimnasio = em.find(Gimnasio.class, id);
        if (gimnasio != null) {
            // Desvinculamos el gimnasio de todos sus socios antes de borrar
            for (Socio socio : gimnasio.getSocios()) {
                socio.getGimnasios().remove(gimnasio);
            }
            em.remove(gimnasio);
        }
        em.getTransaction().commit();
        em.close();
    }

    // d. Obtener los Socio de un gimnasio concreto, según su ID
    public List<Socio> obtenerSocios(int id) {
        EntityManager em = emf.createEntityManager();
        Gimnasio gimnasio = em.find(Gimnasio.class, id);
        List<Socio> socios = gimnasio.getSocios();
        socios.toString(); // fuerza la carga lazy
        em.close();
        return socios;
    }

    // e. Obtener el número de socios inscritos a cada gimnasio
    public Map<String, Long> obtenerNumeroSociosPorGimnasio() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Object[]> query = em.createQuery(
                "select g.nombre, count(s) from Gimnasio g join g.socios s group by g.nombre",
                Object[].class);
        List<Object[]> resultados = query.getResultList();
        Map<String, Long> res = new HashMap<>();
        for (Object[] fila : resultados) {
            String nombre = (String) fila[0];
            Long cantidad = (Long) fila[1];
            res.put(nombre, cantidad);
        }
        em.close();
        return res;
    }

    // f. Obtener los Gimnasio con menos de 10 socios
    public List<Gimnasio> obtenerGimnasiosConMenosDe10Socios() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Gimnasio> query = em.createQuery(
                "select g from Gimnasio g left join g.socios s group by g having count(s) < 10",
                Gimnasio.class);
        List<Gimnasio> gimnasios = query.getResultList();
        em.close();
        return gimnasios;
    }

    // g. Obtener los 5 Gimnasio con la cuota más alta
    public List<Gimnasio> obtenerTop5CuotasMasAltas() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Gimnasio> query = em.createQuery(
                "select g from Gimnasio g order by g.cuotaMensual desc",
                Gimnasio.class);
        query.setMaxResults(5);
        List<Gimnasio> gimnasios = query.getResultList();
        em.close();
        return gimnasios;
    }

    // h. Obtener el Gimnasio de una ciudad (dada por parámetro) con la cuota más barata
    public Gimnasio obtenerGimnasioMasBaratoPorCiudad(String ciudad) {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Gimnasio> query = em.createQuery(
                "select g from Gimnasio g where g.ciudad = :ciudad order by g.cuotaMensual asc",
                Gimnasio.class);
        query.setParameter("ciudad", ciudad);
        query.setMaxResults(1);
        Gimnasio gimnasio = query.getSingleResult();
        em.close();
        return gimnasio;
    }
}