package branch;

import control.ControlCatalogueBranch;
import dominio.CatalogueBranch;
import dominio.Message;
import negocio.exceptions.NonexistentEntityException;
import negocio.exceptions.PreexistingEntityException;

import java.util.ArrayList;
import java.util.List;

public class CatalogueBranchManager {

    private static final ControlCatalogueBranch controlCatalogueBranch = new ControlCatalogueBranch();

    public Message handleRequest(Message message) {

        System.out.println("CatalogueBranchManager received request of type: " + message.type);

        switch (message.type) {
            case ADD_CATALOGUE_BRANCH:
                return addCatalogueBranch(message);
            case EDIT_CATALOGUE_BRANCH:
                return editCatalogueBranch(message);
            case DELETE_CATALOGUE_BRANCH:
                return deleteCatalogueBranch(message);
            case GET_MANY_CATALOGUE_BRANCH:
                return getManyCatalogueBranch(message);
//            case GET_ONE_CATALOGUE_BRANCH:
//                return getOneCatalogueBranch(message);
            default:
                throw new IllegalStateException("Unexpected value: " + message.type);
        }
    }

    private Message addCatalogueBranch(Message message) {
        CatalogueBranch branch = (CatalogueBranch) message.getObject();

        List<CatalogueBranch> catalogueBranches = new ArrayList<>();

        try {
            controlCatalogueBranch.create(branch);
            catalogueBranches = controlCatalogueBranch.findCatalogueBranchEntities();
        } catch (PreexistingEntityException e) {
            System.out.println("El Punto de Atencion en el Catalago ya existe.");
        }

        message.setObject(catalogueBranches);

        return message;
    }

    private Message editCatalogueBranch(Message message) {
        CatalogueBranch branch = (CatalogueBranch) message.getObject();

        List<CatalogueBranch> branchs = new ArrayList<>();

        try {
            controlCatalogueBranch.edit(branch);
            branchs = controlCatalogueBranch.findCatalogueBranchEntities();
        } catch (NonexistentEntityException e) {
            e.printStackTrace();
        } catch (PreexistingEntityException e) {
            System.out.println("La sucursal en el Catalago ya existe.");
        } catch (Exception e) {
            e.printStackTrace();
        }

        message.setObject(branchs);

        return message;
    }

    private Message deleteCatalogueBranch(Message message) {
        Long idBranch = (Long) message.getObject();

        try {
            controlCatalogueBranch.destroy(idBranch);
        } catch (NonexistentEntityException e) {
            e.printStackTrace();
        }

        List<CatalogueBranch> branchs = controlCatalogueBranch.findCatalogueBranchEntities();
        message.setObject(branchs);

        return message;
    }

    private Message getManyCatalogueBranch(Message message) {
        List<CatalogueBranch> branchs = controlCatalogueBranch.findCatalogueBranchEntities();
        message.setObject(branchs);

        return message;
    }

//    private Message getOneCatalogueBranch(Message message) {
//    }

}
