/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.rest.waveteam.mocks;

import co.edu.uniandes.rest.waveteam.dtos.CitaDTO;
import co.edu.uniandes.rest.waveteam.dtos.ConsultorioDTO;
import co.edu.uniandes.rest.waveteam.dtos.MedicoDTO;
import co.edu.uniandes.rest.waveteam.exceptions.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ConsultorioLogicMock. Mock del servicio REST del recurso Consultorio.
 *
 * @author Rogelio García
 */
public class ConsultorioLogicMock {

    // objeto para imprimir logs de las operaciones
    private final static Logger logger = Logger.getLogger(ConsultorioLogicMock.class.getName());

    private static ArrayList<ConsultorioDTO> consultorios;
    private MedicoLogicMock ml = new MedicoLogicMock();
    private CitaLogicMock cl = new CitaLogicMock();

    /**
     * Constructor de los consultorios de prueba
     */
    public ConsultorioLogicMock() {
        if (consultorios == null) {
            consultorios = new ArrayList<ConsultorioDTO>();
            consultorios.add(new ConsultorioDTO(1L, "P202", "8:00am-6:00pm", false, true));
            consultorios.add(new ConsultorioDTO(2L, "P315", "8:00am-6:00pm", false, false));
            consultorios.add(new ConsultorioDTO(3L, "U100", "24 horas", true, false));
            try {
                consultorios.get(0).agregarDoctorAsignado(ml.getDoctors().get(0));
                consultorios.get(1).agregarDoctorAsignado(ml.getDoctors().get(1));
                consultorios.get(2).agregarDoctorAsignado(ml.getDoctors().get(2));
            } catch (MedicoLogicException e) {
                logger.severe("Se produjo un error agregando médicos a los consultorios en el logic mock");
                e.printStackTrace();
            }

        }

        logger.setLevel(Level.INFO);
        logger.info("Inicializa la lista de consultorios");
        logger.info("Consultorios: " + consultorios);
    }

    /**
     * Devuelve el listado de consultorios.
     *
     * @return List de consultorios
     * @throws ConsultorioLogicException
     */
    public List<ConsultorioDTO> getConsultorios() throws ConsultorioLogicException {
        if (consultorios == null) {
            logger.severe("Error interno: la lista de consultorios no existe.");
            throw new ConsultorioLogicException("Error interno: lista de consultorios no existe.");
        }
        logger.info("Retornando la lista de consultorios");
        return consultorios;
    }

    /**
     * Devuelve el consultorio con el id dado
     *
     * @return ConsultorioDTO
     * @throws ConsultorioLogicException
     */
    public ConsultorioDTO getConsultorio(Long id) throws ConsultorioLogicException {
        if (consultorios == null) {
            logger.severe("Error interno: la lista de consultorios no existe.");
            throw new ConsultorioLogicException("Error interno: lista de consultorios no existe.");
        }
        for (ConsultorioDTO consultorio : consultorios) {
            if (Objects.equals(id, consultorio.getId())) {
                logger.info("Retornando el consultorio con ID " + id);
                return consultorio;
            }
        }
        logger.info("No se encontro consultorio con ese ID");
        throw new ConsultorioLogicException("No existe consultorio con ese ID");
    }

    /**
     * Actualiza un consultorio con el id dado, con la información dada
     *
     * @param Long id
     * @param ConsultorioDTO updatedConsultorio
     * @return ConsultorioDTO consultorio actualizado
     * @throws ConsultorioLogicException
     */
    public ConsultorioDTO updateConsultorio(Long id, ConsultorioDTO updatedConsultorio) throws ConsultorioLogicException {
        if (consultorios == null) {
            logger.severe("Error interno: la lista de consultorios no existe.");
            throw new ConsultorioLogicException("Error interno: lista de consultorios no existe.");
        }
        for (ConsultorioDTO consultorio : consultorios) {
            if (Objects.equals(id, consultorio.getId())) {
                consultorio.setId(updatedConsultorio.getId());
                consultorio.setNombre(updatedConsultorio.getNombre());
                consultorio.setHorario(updatedConsultorio.getHorario());
                consultorio.setAtencionUrgencias(updatedConsultorio.getAtencionUrgencias());
                consultorio.setUnidadCuidadosIntensivos(updatedConsultorio.getUnidadCuidadosIntensivos());

                logger.info("Retornando el consultorio actualizado con ID " + id);
                return consultorio;
            }
        }
        logger.info("No se encontro consultorio con ese ID");
        throw new ConsultorioLogicException("No existe consultorio con ese ID");
    }

    /**
     * Crea el consultorio con la información dada
     *
     * @param Consultorio DTO newConsultorio
     * @return ConsultorioDTO newConsultorio
     * @throws ConsultorioLogicException
     */
    public ConsultorioDTO createConsultorio(ConsultorioDTO newConsultorio) throws ConsultorioLogicException {
        logger.info("Creando un consultorio nuevo");
        if (consultorios == null) {
            logger.severe("Error interno: la lista de consultorios no existe.");
            throw new ConsultorioLogicException("Error interno: lista de consultorios no existe.");
        } else if (newConsultorio.getId() != null) {
            for (ConsultorioDTO consultorio : consultorios) {
                if (Objects.equals(newConsultorio.getId(), consultorio.getId())) {
                    logger.info("Ya hay un consultorio con ID " + newConsultorio.getId());
                    throw new ConsultorioLogicException("Debe ingresar un id válido.");
                }
            }
            logger.info("Agregando el consultorio " + newConsultorio);
            consultorios.add(newConsultorio);
            return newConsultorio;
        } else {
            logger.severe("El ID suministrado es nulo");
            long idCons = 1;
            for (ConsultorioDTO consultorio : consultorios) {
                if (Objects.equals(idCons, consultorio.getId())) {
                    idCons++;
                } else {
                    break;
                }
            }
            newConsultorio.setId(idCons);
            logger.info("Agregando el consultorio " + newConsultorio);
            consultorios.add(newConsultorio);
            return newConsultorio;
        }
    }

    /**
     * Elimina el consultorio con el id dado
     *
     * @param id del consultorio
     * @throws ConsultorioLogicException
     */
    public void deleteConsultorio(long id) throws ConsultorioLogicException {
        logger.info("Eliminando consultorio");
        if (consultorios == null) {
            logger.severe("Error interno: la lista de consultorios no existe.");
            throw new ConsultorioLogicException("Error interno: lista de consultorios no existe.");
        }

        for (ConsultorioDTO consultorio : consultorios) {
            if (Objects.equals(id, consultorio.getId())) {
                logger.info("Se encontró consultorio con ID " + id + ", eliminando.");
                consultorios.remove(consultorio);
                return;
            }
        }

        logger.info("No se encontró un consultorio con el ID " + id);
        throw new ConsultorioLogicException("Ingrese un consultorio con un id correcto");
    }

    //REQUERIMIENTO R8 - ASIGNAR DOCTORES Y CONSULTORIOS
    /**
     * Asigna un doctor a un consultorio con sus respectivos ids
     *
     * @param idConsultorio
     * @param idDoctor
     * @throws ConsultorioLogicException
     */
    public ConsultorioDTO asignDoctor(Long idConsultorio, MedicoDTO doc) throws ConsultorioLogicException {
        logger.info("Agregando un doctor a un consultorio");
        if (consultorios == null) {
            logger.severe("Error interno: la lista de consultorios no existe.");
            throw new ConsultorioLogicException("Error interno: lista de consultorios no existe.");
        }

        for (ConsultorioDTO consultorio : consultorios) {
            if (Objects.equals(idConsultorio, consultorio.getId())) {
                logger.info("Se encontró consultorio con ID " + idConsultorio + ", agregando idDoctor.");
                consultorio.agregarDoctorAsignado(doc);
                return consultorio;
            }
        }

        logger.info("No se encontró un consultorio con el ID " + idConsultorio);
        throw new ConsultorioLogicException("Ingrese un consultorio con un id correcto");
    }

    /**
     * Asigna un doctor a un consultorio con sus respectivos ids
     *
     * @param idConsultorio
     * @param idDoctor
     * @throws ConsultorioLogicException
     */
    public ConsultorioDTO asignDoctors(Long idConsultorio, List<MedicoDTO> nuevaLista) throws ConsultorioLogicException {
        logger.info("Agregando doctores a un consultorio");
        if (consultorios == null) {
            logger.severe("Error interno: la lista de consultorios no existe.");
            throw new ConsultorioLogicException("Error interno: lista de consultorios no existe.");
        }

        for (ConsultorioDTO consultorio : consultorios) {
            if (Objects.equals(idConsultorio, consultorio.getId())) {
                logger.info("Se encontró consultorio con ID " + idConsultorio + ", agregando lista de doctores.");
                consultorio.setDoctoresAsignados(nuevaLista);
                return consultorio;
            }
        }

        logger.info("No se encontró un consultorio con el ID " + idConsultorio);
        throw new ConsultorioLogicException("Ingrese un consultorio con un id correcto");
    }

    /**
     * Elimina un doctor asignado de consultorio con sus respectivos ids
     *
     * @param idConsultorio
     * @param idDoctor
     * @throws ConsultorioLogicException
     */
    public ConsultorioDTO unasignDoctor(Long idConsultorio, Long idDoctor) throws ConsultorioLogicException {
        logger.info("Eliminando un doctor asignado de un consultorio");
        if (consultorios == null) {
            logger.severe("Error interno: la lista de consultorios no existe.");
            throw new ConsultorioLogicException("Error interno: lista de consultorios no existe.");
        }

        for (ConsultorioDTO consultorio : consultorios) {
            if (Objects.equals(idConsultorio, consultorio.getId())) {
                logger.info("Se encontró consultorio con ID " + idConsultorio + ", eliminando idDoctor.");

                boolean seElimino = consultorio.eliminarDoctorAsignado(idDoctor);
                if (!seElimino) {
                    logger.info("No se encontró un doctor asignado con el ID " + idDoctor);
                    throw new ConsultorioLogicException("Ingrese un doctor con un id correcto");
                }
                return consultorio;
            }
        }

        logger.info("No se encontró un consultorio con el ID " + idConsultorio);
        throw new ConsultorioLogicException("Ingrese un consultorio con un id correcto");
    }

    public ConsultorioDTO unasignDoctors(Long idConsultorio) throws ConsultorioLogicException {
        logger.info("Eliminando un doctor asignado de un consultorio");
        if (consultorios == null) {
            logger.severe("Error interno: la lista de consultorios no existe.");
            throw new ConsultorioLogicException("Error interno: lista de consultorios no existe.");
        }

        for (ConsultorioDTO consultorio : consultorios) {
            if (Objects.equals(idConsultorio, consultorio.getId())) {
                logger.info("Se encontró consultorio con ID " + idConsultorio + ", eliminando todos los doctores asignados.");
                consultorio.setDoctoresAsignados(new ArrayList<MedicoDTO>());
                return consultorio;
            }
        }

        logger.info("No se encontró un consultorio con el ID " + idConsultorio);
        throw new ConsultorioLogicException("Ingrese un consultorio con un id correcto");
    }
}
