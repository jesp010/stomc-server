package branch;

import control.ControlBranch;
import dominio.Branch;
import dominio.Message;
import negocio.exceptions.NonexistentEntityException;

import java.util.List;

public class BranchManager {

    private static final ControlBranch controlBranch = new ControlBranch();

    public Message handleRequest(Message message) {

        System.out.println("BranchManager received request of type: " + message.type);

        switch (message.type) {
            case ADD_BRANCH:
                return addBranch(message);
            case EDIT_BRANCH:
                return editBranch(message);
            case DELETE_BRANCH:
                return deleteBranch(message);
            case GET_MANY_BRANCH:
                return getManyBranch(message);
//            case GET_ONE_BRANCH:
//                return getOneBranch(message);
            default:
                throw new IllegalStateException("Unexpected value: " + message.type);
        }
    }

    private Message addBranch(Message message) {
        Branch branch = (Branch) message.getObject();

        controlBranch.create(branch);

        List<Branch> branchs = controlBranch.findBranchEntities();
        message.setObject(branchs);

        return message;
    }

    private Message editBranch(Message message) {
        Branch branch = (Branch) message.getObject();

        try {
            controlBranch.edit(branch);
        } catch (NonexistentEntityException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<Branch> branchs = controlBranch.findBranchEntities();
        message.setObject(branchs);

        return message;
    }

    private Message deleteBranch(Message message) {
        Long idBranch = (Long) message.getObject();

        try {
            controlBranch.destroy(idBranch);
        } catch (NonexistentEntityException e) {
            e.printStackTrace();
        }

        List<Branch> branchs = controlBranch.findBranchEntities();
        message.setObject(branchs);

        return message;
    }

    private Message getManyBranch(Message message) {
        List<Branch> branchs = controlBranch.findBranchEntities();
        message.setObject(branchs);

        return message;
    }

//    private Message getOneBranch(Message message) {
//    }

}
