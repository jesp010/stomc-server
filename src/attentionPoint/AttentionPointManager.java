package attentionPoint;

import control.ControlAttentionPoint;
import dominio.AttentionPoint;
import dominio.Message;
import negocio.exceptions.NonexistentEntityException;

import java.util.List;

public class AttentionPointManager {

    private static final ControlAttentionPoint controlAttentionPoint = new ControlAttentionPoint();

    public Message handleRequest(Message message) {

        System.out.println("AttentionPointManager received request of type: " + message.type);

        switch (message.type) {
            case ADD_ATTENTION_POINT:
                return addAttentionPoint(message);
            case EDIT_ATTENTION_POINT:
                return editAttentionPoint(message);
            case DELETE_ATTENTION_POINT:
                return deleteAttentionPoint(message);
            case GET_MANY_ATTENTION_POINT:
                return getManyAttentionPoint(message);
//            case GET_ONE_ATTENTION_POINT:
//                return getOneAttentionPoint(message);
            default:
                throw new IllegalStateException("Unexpected value: " + message.type);
        }
    }

    private Message addAttentionPoint(Message message) {
        AttentionPoint attentionPoint = (AttentionPoint) message.getObject();

        controlAttentionPoint.create(attentionPoint);

        List<AttentionPoint> attentionPoints = controlAttentionPoint.findAttentionPointEntities();
        message.setObject(attentionPoints);

        return message;
    }

    private Message editAttentionPoint(Message message) {
        AttentionPoint attentionPoint = (AttentionPoint) message.getObject();

        try {
            controlAttentionPoint.edit(attentionPoint);
        } catch (NonexistentEntityException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<AttentionPoint> attentionPoints = controlAttentionPoint.findAttentionPointEntities();
        message.setObject(attentionPoints);

        return message;
    }

    private Message deleteAttentionPoint(Message message) {
        Long idAttentionPoint = (Long) message.getObject();

        try {
            controlAttentionPoint.destroy(idAttentionPoint);
        } catch (NonexistentEntityException e) {
            e.printStackTrace();
        }

        List<AttentionPoint> attentionPoints = controlAttentionPoint.findAttentionPointEntities();
        message.setObject(attentionPoints);

        return message;
    }

    private Message getManyAttentionPoint(Message message) {
        List<AttentionPoint> attentionPoints = controlAttentionPoint.findAttentionPointEntities();
        message.setObject(attentionPoints);

        return message;
    }

//    private Message getOneAttentionPoint(Message message) {
//    }

}
