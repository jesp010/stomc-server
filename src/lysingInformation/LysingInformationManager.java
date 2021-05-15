package lysingInformation;

import control.ControlLysingInformation;
import dominio.LysingInformation;
import dominio.Message;
import negocio.exceptions.NonexistentEntityException;

import java.util.List;

public class LysingInformationManager {

    private static final ControlLysingInformation controlLysingInformation = new ControlLysingInformation();

    public Message handleRequest(Message message) {

        System.out.println("LysingInformationManager received request of type: " + message.type);

        switch (message.type) {
            case ADD_LYSING_INFORMATION:
                return addLysingInformation(message);
            case EDIT_LYSING_INFORMATION:
                return editLysingInformation(message);
            case DELETE_LYSING_INFORMATION:
                return deleteLysingInformation(message);
            case GET_MANY_LYSING_INFORMATION:
                return getManyLysingInformation(message);
//            case GET_MANY_LYSING_INFORMATION:
//                return getOneLysingInformation(message);
            default:
                throw new IllegalStateException("Unexpected value: " + message.type);
        }
    }

    private Message addLysingInformation(Message message) {
        LysingInformation lysingInformation = (LysingInformation) message.getObject();

        controlLysingInformation.create(lysingInformation);

        List<LysingInformation> lysingInformations = controlLysingInformation.findLysingInformationEntities();
        message.setObject(lysingInformations);

        return message;
    }

    private Message editLysingInformation(Message message) {
        LysingInformation lysingInformation = (LysingInformation) message.getObject();

        try {
            controlLysingInformation.edit(lysingInformation);
        } catch (NonexistentEntityException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<LysingInformation> lysingInformations = controlLysingInformation.findLysingInformationEntities();
        message.setObject(lysingInformations);

        return message;
    }

    private Message deleteLysingInformation(Message message) {
        Long idLysingInformation = (Long) message.getObject();

        try {
            controlLysingInformation.destroy(idLysingInformation);
        } catch (NonexistentEntityException e) {
            e.printStackTrace();
        }

        List<LysingInformation> lysingInformations = controlLysingInformation.findLysingInformationEntities();
        message.setObject(lysingInformations);

        return message;
    }

    private Message getManyLysingInformation(Message message) {
        List<LysingInformation> lysingInformations = controlLysingInformation.findLysingInformationEntities();
        message.setObject(lysingInformations);

        return message;
    }

//    private Message getOneLysingInformation(Message message) {
//    }

}
