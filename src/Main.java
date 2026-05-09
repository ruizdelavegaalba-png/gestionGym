import dao.GimnasioDAO;
import dao.SocioDAO;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import modelo.Gimnasio;
import modelo.Socio;

import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("gimnasios.odb");

        GimnasioDAO gymDAO = new GimnasioDAO(emf);
        SocioDAO socioDAO = new SocioDAO(emf);

        gymDAO.insertarGimnasio(new Gimnasio("Iron Temple", "Madrid", 45.99));
        gymDAO.insertarGimnasio(new Gimnasio("Sparta Fitness", "Barcelona", 29.90));
        gymDAO.insertarGimnasio(new Gimnasio("Yoga & Flow", "Valencia", 60.00));
        gymDAO.insertarGimnasio(new Gimnasio("LowCost Gym", "Sevilla", 19.95));
        gymDAO.insertarGimnasio(new Gimnasio("Elite Performance", "Madrid", 85.00));
        gymDAO.insertarGimnasio(new Gimnasio("CrossFit Box 33", "Bilbao", 70.00));
        gymDAO.insertarGimnasio(new Gimnasio("Padel & Gym", "Málaga", 35.50));
        gymDAO.insertarGimnasio(new Gimnasio("Wellness Center", "Zaragoza", 55.00));
        gymDAO.insertarGimnasio(new Gimnasio("Heavy Metal Lifting", "Vigo", 25.00));
        gymDAO.insertarGimnasio(new Gimnasio("Zumba Party", "Alicante", 32.00));

        socioDAO.insertarSocio(new Socio("Ana García", 28, true));
        socioDAO.insertarSocio(new Socio("Carlos Pérez", 45, false));
        socioDAO.insertarSocio(new Socio("Lucía Fernández", 19, false));
        socioDAO.insertarSocio(new Socio("Marcos Ruiz", 34, true));
        socioDAO.insertarSocio(new Socio("Elena Sanz", 52, false));
        socioDAO.insertarSocio(new Socio("David León", 23, true));
        socioDAO.insertarSocio(new Socio("Sara Cano", 31, false));
        socioDAO.insertarSocio(new Socio("Roberto Gómez", 60, true));
        socioDAO.insertarSocio(new Socio("Irene Molina", 26, false));
        socioDAO.insertarSocio(new Socio("Javier Ortiz", 40, false));

        socioDAO.asignarSocio(11, 1);
        socioDAO.asignarSocio(11, 3);
        socioDAO.asignarSocio(11, 5);
        socioDAO.asignarSocio(11, 8);
        socioDAO.asignarSocio(11, 10);
        socioDAO.asignarSocio(12, 2);
        socioDAO.asignarSocio(12, 4);
        socioDAO.asignarSocio(12, 9);
        socioDAO.asignarSocio(13, 2);
        socioDAO.asignarSocio(13, 4);
        socioDAO.asignarSocio(13, 10);
        socioDAO.asignarSocio(14, 1);
        socioDAO.asignarSocio(14, 2);
        socioDAO.asignarSocio(14, 3);
        socioDAO.asignarSocio(14, 4);
        socioDAO.asignarSocio(14, 5);
        socioDAO.asignarSocio(15, 6);
        socioDAO.asignarSocio(15, 7);
        socioDAO.asignarSocio(15, 8);
        socioDAO.asignarSocio(16, 1);
        socioDAO.asignarSocio(16, 6);
        socioDAO.asignarSocio(16, 9);
        socioDAO.asignarSocio(17, 2);
        socioDAO.asignarSocio(17, 4);
        socioDAO.asignarSocio(17, 7);
        socioDAO.asignarSocio(18, 1);
        socioDAO.asignarSocio(18, 3);
        socioDAO.asignarSocio(18, 5);
        socioDAO.asignarSocio(19, 6);
        socioDAO.asignarSocio(19, 10);

        // OPERACIONES DE GIMNASIO DAO
        // b. Actualización de un gimnasio
        Gimnasio gymPrueba = new Gimnasio("Gym Huelva", "Huelva", 15.00);
        gymDAO.insertarGimnasio(gymPrueba);
        int idGymPrueba = gymPrueba.getId();
        gymDAO.actualizarGimnasio(idGymPrueba, "Gym Huelva Actualizado", "Huelva", 20.00);
        System.out.println("Gimnasio actualizado correctamente.");

        // c. Borrado de un gimnasio
        gymDAO.borrarGimnasio(idGymPrueba);
        System.out.println("Gimnasio borrado correctamente.");

        // d. Obtener socios de un gimnasio concreto
        System.out.println("\n--- Socios del gimnasio 1 (Iron Temple) ---");
        List<Socio> sociosGym1 = gymDAO.obtenerSocios(1);
        System.out.println(sociosGym1);

        // e. Número de socios inscritos a cada gimnasio
        System.out.println("\n--- Número de socios por gimnasio ---");
        Map<String, Long> sociosPorGim = gymDAO.obtenerNumeroSociosPorGimnasio();
        for (Map.Entry<String, Long> entry : sociosPorGim.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " socios");
        }

        // f. Gimnasios con menos de 10 socios
        System.out.println("\n--- Gimnasios con menos de 10 socios ---");
        List<Gimnasio> gymMenos10 = gymDAO.obtenerGimnasiosConMenosDe10Socios();
        System.out.println(gymMenos10);

        // g. Top 5 gimnasios con la cuota más alta
        System.out.println("\n--- Top 5 gimnasios con cuota más alta ---");
        List<Gimnasio> top5 = gymDAO.obtenerTop5CuotasMasAltas();
        System.out.println(top5);

        // h. Gimnasio más barato de Madrid
        System.out.println("\n--- Gimnasio más barato de Madrid ---");
        Gimnasio masBaratoMadrid = gymDAO.obtenerGimnasioMasBaratoPorCiudad("Madrid");
        System.out.println(masBaratoMadrid);

        // OPERACIONES DE SOCIO DAO

        // b. Actualización de un socio
        Socio socioPrueba = new Socio("Socio Cristóbal", 25, false);
        socioDAO.insertarSocio(socioPrueba);
        int idSocioPrueba = socioPrueba.getId();
        socioDAO.actualizarSocio(idSocioPrueba, "Socio Cristóbal Actualizado", 26, true);
        System.out.println("\nSocio actualizado correctamente.");

        // c. Borrado de un socio
        socioDAO.borrarSocio(idSocioPrueba);
        System.out.println("Socio borrado correctamente.");

        // d. Asignar socio a gimnasio (usamos socio 20 y gimnasio 7 como combinación de prueba)
        socioDAO.asignarSocio(20, 7);
        System.out.println("\nSocio 20 asignado al gimnasio 7.");

        // e. Borrar socio de un gimnasio (la misma combinación)
        socioDAO.borrarSocioDeGimnasio(20, 7);
        System.out.println("Socio 20 eliminado del gimnasio 7.");

        // f. Obtener los gimnasios de un socio concreto (socio 11: Ana García)
        System.out.println("\n--- Gimnasios de la socio 11 (Ana García) ---");
        List<Gimnasio> gimnasiosSocio11 = socioDAO.obtenerGimnasios(11);
        System.out.println(gimnasiosSocio11);

        // g. Media de edad de todos los socios
        System.out.println("\n--- Media de edad de todos los socios ---");
        double mediaEdad = socioDAO.obtenerMediaEdad();
        System.out.println("Media de edad: " + mediaEdad);

        // h. Socios sin ningún gimnasio
        System.out.println("\n--- Socios sin ningún gimnasio ---");
        List<Socio> sociosSinGym = socioDAO.obtenerSociosSinGimnasio();
        System.out.println(sociosSinGym);

        emf.close();
    }
}
