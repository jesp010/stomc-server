package turn;

import control.ControlTurn;
import dominio.Message;
import dominio.Turn;

import java.util.*;


public class TurnManager {

    private static final ControlTurn controlTurn = new ControlTurn();

//    static int cajaTurnNumberFloor = 0;
//    static int moduloTurnNumberFloor = 0;
//    static int genericTurnNumberFloor = 0;
//
//    static int currentCajaTurnNumber = cajaTurnNumberFloor;
//    static int currentModuloTurnNumber = moduloTurnNumberFloor;
//    static int currentGenericTurnNumber = genericTurnNumberFloor;
//
//    static List<Turn> cajaTurnList = new ArrayList<Turn>();
//    static List<Turn> moduloTurnList = new ArrayList<Turn>();
//    static List<Turn> genericTurnList = new ArrayList<Turn>();

    private static final Map<String, Message.MessageType> messageMap = new HashMap<String, Message.MessageType>();

    public Message handleRequest(Message message) {

        System.out.println("TurnManager received request of type: " + message.type);
        messageMap.put(message.getUuid(), message.getType());

        switch (message.type) {
            case NEW_TURN_CAJA:
                return createNewCajaTurn(message);
            case NEW_TURN_MODULO:
                return createNewModuloTurn(message);
            case NEW_TURN_GENERIC:
                return createNewGenericTurn(message);
            case CALL_NEXT_CAJA:
                return cajaCallNextTurn(message);
            case CALL_NEXT_MODULO:
                return moduloCallNextTurn(message);
            case CALL_NEXT_GENERIC:
                return genericCallNextTurn(message);
            case RELEASE_TURN:
                return releaseTurn(message);
            case GET_TURNS_STATUS:
                return getTurnsStatus(message);
            case GET_TURNS_START_DATE:
                return getTurnsStartDate(message);
            case GET_TURNS_END_DATE:
                return getTurnsEndDate(message);
            case GET_TURNS_STATUS_START_DATE:
                return getTurnsStatusStartDate(message);
            case GET_TURNS_STATUS_END_DATE:
                return getTurnsStatusEndDate(message);
            case GET_TURNS_START_AND_END_DATE:
                return getTurnsStartEndDate(message);
            case GET_TURNS_STATUS_START_AND_END_DATE:
                return getTurnsStatusStartEndDate(message);
            case GET_MANY_TURN:
                return getTurns(message);
            default:
                throw new IllegalStateException("Unexpected value: " + message.type);
        }
    }

    private Message getTurnsStatus(Message message) {
        ArrayList<Object> contenido = (ArrayList<Object>) message.getObject();

        String estado = (String) contenido.get(2);

        List<Turn> turns = null;
        try {
            turns = controlTurn.findTurnByStatus(estado);
        } catch (Exception e) {
            e.printStackTrace();
        }
        message.setObject(turns);

        return message;
    }

    private Message getTurnsStartDate(Message message) {
        ArrayList<Object> contenido = (ArrayList<Object>) message.getObject();

        Date fechaInicio = (Date) contenido.get(0);

        List<Turn> turns = null;
        try {
            turns = controlTurn.Query_FechaInicio(fechaInicio);
        } catch (Exception e) {
            e.printStackTrace();
        }
        message.setObject(turns);

        return message;
    }

    private Message getTurnsEndDate(Message message) {
        ArrayList<Object> contenido = (ArrayList<Object>) message.getObject();

        Date fechaFin = (Date) contenido.get(1);

        List<Turn> turns = null;
        try {
            turns = controlTurn.Query_FechaFin(fechaFin);
        } catch (Exception e) {
            e.printStackTrace();
        }
        message.setObject(turns);

        return message;
    }

    private Message getTurnsStatusStartDate(Message message) {
        ArrayList<Object> contenido = (ArrayList<Object>) message.getObject();

        Date fechaInicio = (Date) contenido.get(0);
        String estado = (String) contenido.get(2);

        List<Turn> turns = null;
        try {
            turns = controlTurn.Query_FechaInicioYEstado(estado, fechaInicio);
        } catch (Exception e) {
            e.printStackTrace();
        }
        message.setObject(turns);

        return message;
    }

    private Message getTurnsStatusEndDate(Message message) {
        ArrayList<Object> contenido = (ArrayList<Object>) message.getObject();

        Date fechaFin = (Date) contenido.get(1);
        String estado = (String) contenido.get(2);

        List<Turn> turns = null;
        try {
            turns = controlTurn.Query_FechaFinYEstado(estado, fechaFin);
        } catch (Exception e) {
            e.printStackTrace();
        }
        message.setObject(turns);

        return message;
    }

    private Message getTurnsStartEndDate(Message message) {
        ArrayList<Object> contenido = (ArrayList<Object>) message.getObject();

        Date fechaInicio = (Date) contenido.get(0);
        Date fechaFin = (Date) contenido.get(1);

        List<Turn> turns = null;
        try {
            turns = controlTurn.findTurnByDate(fechaInicio, fechaFin);
        } catch (Exception e) {
            e.printStackTrace();
        }
        message.setObject(turns);

        return message;
    }

    private Message getTurnsStatusStartEndDate(Message message) {
        ArrayList<Object> contenido = (ArrayList<Object>) message.getObject();

        Date fechaInicio = (Date) contenido.get(0);
        Date fechaFin = (Date) contenido.get(1);
        String estado = (String) contenido.get(2);

        List<Turn> turns = null;
        try {
            turns = controlTurn.Query_EntreFechasYEstado(estado, fechaInicio, fechaFin);
        } catch (Exception e) {
            e.printStackTrace();
        }
        message.setObject(turns);

        return message;
    }

    private Message getTurns(Message message) {
        List<Turn> turnos = controlTurn.findTurnEntities();
        message.setObject(turnos);

        return message;
    }

    public static Message createNewCajaTurn(Message message){
        Turn turn = (Turn) message.getObject();

//        Turn newCajaTurn = new Turn();
//        newCajaTurn.setTurnNumber(currentCajaTurnNumber + 1);
//
//        newCajaTurn.setType(Turn.Type.CAJA);
//        newCajaTurn.setIsActive(true);
//        newCajaTurn.setStatus("Esperando");
//        newCajaTurn.setDateTimeCreated(new Date());
//
//        currentCajaTurnNumber += 1;

        try {
            controlTurn.create(turn);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        cajaTurnList.add(newCajaTurn);
        System.out.println("TurnManager created turn: " + turn.toString());

        message.setObject(null);

        return message;
    }

    public static Message createNewModuloTurn(Message message){
        Turn turn = (Turn) message.getObject();

//        Turn newModuloTurn = new Turn();
//        newModuloTurn.setTurnNumber(currentModuloTurnNumber + 1);
//
//        newModuloTurn.setType(Turn.Type.MODULO);
//        newModuloTurn.setIsActive(true);
//        newModuloTurn.setStatus("Esperando");
//        newModuloTurn.setDateTimeCreated(new Date());
//
//        currentModuloTurnNumber += 1;

        try {
            controlTurn.create(turn);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        moduloTurnList.add(newModuloTurn);
        System.out.println("TurnManager created turn: " + turn.toString());

        message.setObject(null);

        return message;
    }

    public static Message createNewGenericTurn(Message message){
        Turn turn = (Turn) message.getObject();

//        Turn newGenericTurn = new Turn();
//        newGenericTurn.setTurnNumber(currentGenericTurnNumber + 1);
//
//        newGenericTurn.setType(Turn.Type.GENERIC);
//        newGenericTurn.setIsActive(true);
//        newGenericTurn.setStatus("Esperando");
//        newGenericTurn.setDateTimeCreated(new Date());
//
//        currentGenericTurnNumber += 1;

        try {
            controlTurn.create(turn);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        genericTurnList.add(newGenericTurn);
        System.out.println("TurnManager created turn: " + turn.toString());

        message.setObject(null);

        return message;
    }

    public static Message cajaCallNextTurn(Message message){
        Turn turn = (Turn) message.getObject();

//        if (!cajaTurnList.isEmpty()) {
//            Turn nextTurn = cajaTurnList.get(0);
//
//            nextTurn.setStatus("Atendiendo");
//            nextTurn.setDateTimeAssigned(new Date());

            System.out.println("TurnManager Next turn is: " + turn.toString());

            try {
                controlTurn.edit(turn);
            } catch (Exception e) {
                e.printStackTrace();
            }

//            cajaTurnList.remove(0);

            message.setObject(null);

//            System.out.println("TurnManager Remaining Caja turns: " + cajaTurnList.size());

            return message;
//        } else {
//            System.out.println("TurnManager No remaining Turns in Caja");
//        }
//
//        message.setObject(null);
//        return message;
    }

    public static Message moduloCallNextTurn(Message message){
        Turn turn = (Turn) message.getObject();

//        if (!moduloTurnList.isEmpty()) {
//            Turn temp = moduloTurnList.get(0);
//
//            temp.setStatus("Atendiendo");
//            temp.setDateTimeAssigned(new Date());

            try {
                controlTurn.edit(turn);
            } catch (Exception e) {
                e.printStackTrace();
            }

//            moduloTurnList.remove(0);

            message.setObject(null);

//            System.out.println("TurnManager Remaining Modulo turns: " + moduloTurnList.size());

            return message;
//        } else {
//            System.out.println("TurnManager No remaining Turns in Modulo");
//        }
//
//        message.setObject(null);
//
//        return message;
    }

    public static Message genericCallNextTurn(Message message){
        Turn turn = (Turn) message.getObject();

//        if (!genericTurnList.isEmpty()) {
//            Turn temp = genericTurnList.get(0);
//
//            temp.setStatus("Atendiendo");
//            temp.setDateTimeAssigned(new Date());

            try {
                controlTurn.edit(turn);
            } catch (Exception e) {
                e.printStackTrace();
            }

//            genericTurnList.remove(0);

            message.setObject(null);

//            System.out.println("TurnManager Remaining Generic turns: " + genericTurnList.size());

            return message;
//        } else {
//            System.out.println("TurnManager No remaining Turns in Generic");
//        }
//
//        message.setObject(null);
//
//        return message;
    }

    public static Message releaseTurn(Message message){

        Turn turn = (Turn) message.getObject();

//        turn.setDateTimeFinished(new Date());
//        turn.setStatus("Finalizado");
//        turn.setIsActive(false);

        try {
            controlTurn.edit(turn);
        } catch (Exception e) {
            e.printStackTrace();
        }

        message.setObject(turn);

        return message;
    }

//    /**
//     * Called when Server could not connect/send the requested turn
//     * to a Client. Receives the turn and adds it back into the
//     * applicable turn list at index 0. Only applies to a turn requested
//     * via CALL_NEXT_*
//     * @param turn
//     */
//    public void sendBackTurn(Turn turn){
//        //FIXME: refactor to descriptive func name
//        switch (turn.getType()) {
//            case CAJA:
//                cajaTurnList.add(0, turn);
//                break;
//            case MODULO:
//                //TODO: logic modulo
//                moduloTurnList.add(0, turn);
//                break;
//            case GENERIC:
//                //TODO: logic generic
//                genericTurnList.add(0, turn);
//                break;
//            default: throw new IllegalStateException("Unexpected value: " + turn.getType());
//        }
//    }

}
