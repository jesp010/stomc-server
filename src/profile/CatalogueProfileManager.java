package profile;

import control.ControlCatalogueProfile;
import dominio.CatalogueProfile;
import dominio.Message;
import negocio.exceptions.NonexistentEntityException;
import negocio.exceptions.PreexistingEntityException;

import java.util.ArrayList;
import java.util.List;

public class CatalogueProfileManager {

    private static final ControlCatalogueProfile controlCatalogueProfile = new ControlCatalogueProfile();

    public Message handleRequest(Message message) {

        System.out.println("CatalogueProfileManager received request of type: " + message.type);

        switch (message.type) {
            case ADD_CATALOGUE_PROFILE:
                return addCatalogueProfile(message);
            case EDIT_CATALOGUE_PROFILE:
                return editCatalogueProfile(message);
            case DELETE_CATALOGUE_PROFILE:
                return deleteCatalogueProfile(message);
            case GET_MANY_CATALOGUE_PROFILE:
                return getManyCatalogueProfile(message);
//            case GET_ONE_CATALOGUE_PROFILE:
//                return getOneCatalogueProfile(message);
            default:
                throw new IllegalStateException("Unexpected value: " + message.type);
        }
    }

    private Message addCatalogueProfile(Message message) {
        CatalogueProfile catalogueProfile = (CatalogueProfile) message.getObject();

        List<CatalogueProfile> catalogueProfiles = new ArrayList<>();

        try {
            controlCatalogueProfile.create(catalogueProfile);
            catalogueProfiles = controlCatalogueProfile.findCatalogueProfileEntities();
        } catch (PreexistingEntityException e) {
            System.out.println("El Perfil en el Catalago ya existe.");
        }

        message.setObject(catalogueProfiles);

        return message;
    }

    private Message editCatalogueProfile(Message message) {
        CatalogueProfile catalogueProfile = (CatalogueProfile) message.getObject();

        List<CatalogueProfile> catalogueProfiles = new ArrayList<>();

        try {
            controlCatalogueProfile.edit(catalogueProfile);
            catalogueProfiles = controlCatalogueProfile.findCatalogueProfileEntities();
        } catch (NonexistentEntityException e) {
            e.printStackTrace();
        } catch (PreexistingEntityException e) {
            System.out.println("El Punto de Atencion en el Catalago ya existe.");
        } catch (Exception e) {
            e.printStackTrace();
        }

        message.setObject(catalogueProfiles);

        return message;
    }

    private Message deleteCatalogueProfile(Message message) {
        Long idCatalogueProfile = (Long) message.getObject();

        try {
            controlCatalogueProfile.destroy(idCatalogueProfile);
        } catch (NonexistentEntityException e) {
            e.printStackTrace();
        }

        List<CatalogueProfile> catalogueProfiles = controlCatalogueProfile.findCatalogueProfileEntities();
        message.setObject(catalogueProfiles);

        return message;
    }

    private Message getManyCatalogueProfile(Message message) {
        List<CatalogueProfile> catalogueProfiles = controlCatalogueProfile.findCatalogueProfileEntities();
        message.setObject(catalogueProfiles);

        return message;
    }

//    private Message getOneCatalogueProfile(Message message) {
//    }

}
