package attentionPoint;

import control.ControlCatalogueAttentionPoint;
import dominio.CatalogueAttentionPoint;
import dominio.Message;
import negocio.exceptions.NonexistentEntityException;
import negocio.exceptions.PreexistingEntityException;

import java.util.ArrayList;
import java.util.List;

public class CatalogueAttentionPointManager {

    private static final ControlCatalogueAttentionPoint controlCatalogueAttentionPoint = new ControlCatalogueAttentionPoint();

    public Message handleRequest(Message message) {

        System.out.println("CatalogueAttentionPointManager received request of type: " + message.type);

        switch (message.type) {
            case ADD_CATALOGUE_ATTENTION_POINT:
                return addCatalogueAttentionPoint(message);
            case EDIT_CATALOGUE_ATTENTION_POINT:
                return editCatalogueAttentionPoint(message);
            case DELETE_CATALOGUE_ATTENTION_POINT:
                return deleteCatalogueAttentionPoint(message);
            case GET_MANY_CATALOGUE_ATTENTION_POINT:
                return getManyCatalogueAttentionPoint(message);
//            case GET_ONE_CATALOGUE_ATTENTION_POINT:
//                return getOneAttentionPoint(message);
            default:
                throw new IllegalStateException("Unexpected value: " + message.type);
        }
    }

    private Message addCatalogueAttentionPoint(Message message) {
        CatalogueAttentionPoint catalogueAttentionPoint = (CatalogueAttentionPoint) message.getObject();

        List<CatalogueAttentionPoint> catalogueAttentionPoints = new ArrayList<>();

        try {
            controlCatalogueAttentionPoint.create(catalogueAttentionPoint);
            catalogueAttentionPoints = controlCatalogueAttentionPoint.findCatalogueAttentionPointEntities();

        } catch (PreexistingEntityException e) {
            System.out.println("El Punto de Atencion en el Catalago ya existe.");
        }

        message.setObject(catalogueAttentionPoints);

        return message;
    }

    private Message editCatalogueAttentionPoint(Message message) {
        CatalogueAttentionPoint catalogueAttentionPoint = (CatalogueAttentionPoint) message.getObject();

        List<CatalogueAttentionPoint> catalogueAttentionPoints = new ArrayList<>();

        try {
            controlCatalogueAttentionPoint.edit(catalogueAttentionPoint);
            catalogueAttentionPoints = controlCatalogueAttentionPoint.findCatalogueAttentionPointEntities();
        } catch (NonexistentEntityException e) {
            e.printStackTrace();
        } catch (PreexistingEntityException e) {
            System.out.println("El Punto de Atencion en el Catalago ya existe.");
        } catch (Exception e) {
            e.printStackTrace();
        }

        message.setObject(catalogueAttentionPoints);

        return message;
    }

    private Message deleteCatalogueAttentionPoint(Message message) {
        Long idCatalogueAttentionPoint = (Long) message.getObject();

        try {
            controlCatalogueAttentionPoint.destroy(idCatalogueAttentionPoint);
        } catch (NonexistentEntityException e) {
            e.printStackTrace();
        }

        List<CatalogueAttentionPoint> catalogueAttentionPoints = controlCatalogueAttentionPoint.findCatalogueAttentionPointEntities();
        message.setObject(catalogueAttentionPoints);

        return message;
    }

    private Message getManyCatalogueAttentionPoint(Message message) {
        List<CatalogueAttentionPoint> catalogueAttentionPoints = controlCatalogueAttentionPoint.findCatalogueAttentionPointEntities();
        message.setObject(catalogueAttentionPoints);

        return message;
    }

//    private Message getOneCatalogueAttentionPoint(Message message) {
//    }

}
