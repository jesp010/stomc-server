package server;

import attentionPoint.AttentionPointManager;
import attentionPoint.CatalogueAttentionPointManager;
import branch.BranchManager;
import branch.CatalogueBranchManager;
import dominio.Message;
import employee.EmployeeManager;
import lysingInformation.LysingInformationManager;
import profile.CatalogueProfileManager;
import profile.ProfileManager;
import turn.TurnManager;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client extends Thread {
    protected Socket socket;
    protected ObjectOutputStream os;
    protected ObjectInputStream is;

    TurnManager turnManager = new TurnManager();
    EmployeeManager employeeManager = new EmployeeManager();
    ProfileManager profileManager = new ProfileManager();
    AttentionPointManager attentionPointManager = new AttentionPointManager();
    BranchManager branchManager = new BranchManager();
    CatalogueProfileManager catalogueProfileManager = new CatalogueProfileManager();
    CatalogueAttentionPointManager catalogueAttentionPointManager = new CatalogueAttentionPointManager();
    CatalogueBranchManager catalogueBranchManager = new CatalogueBranchManager();
    LysingInformationManager lysingInformationManager = new LysingInformationManager();

    public Client(Socket clientSocket) {
        this.socket = clientSocket;
    }

    @Override
    public void run() {
        try {
            os = new ObjectOutputStream(socket.getOutputStream());
            is = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {

            if (!socket.isClosed()) {
                try {
                    Message message = null;

                    assert is != null;
                    message = (Message) is.readObject();

                    handleReceivedMessage(message, os);
                } catch (Exception e) {
                    try {
                        close();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                        break;
                    }
                    e.printStackTrace();
                    break;
                }
            } else {
                break;
            }
        }
        System.out.println("Fin del cliente");
    }

    public void handleReceivedMessage(Message message, ObjectOutputStream os) {

        switch (message.getType()) {
            case CALL_NEXT_CAJA:
            case CALL_NEXT_MODULO:
            case CALL_NEXT_GENERIC:
            case NEW_TURN_CAJA:
            case NEW_TURN_MODULO:
            case NEW_TURN_GENERIC:
            case RELEASE_TURN:
            case GET_TURNS_STATUS:
            case GET_TURNS_START_DATE:
            case GET_TURNS_END_DATE:
            case GET_TURNS_STATUS_START_DATE:
            case GET_TURNS_STATUS_END_DATE:
            case GET_TURNS_START_AND_END_DATE:
            case GET_TURNS_STATUS_START_AND_END_DATE:
            case GET_MANY_TURN:
                message = turnManager.handleRequest(message);
                break;

            case ADD_EMPLOYEE:
            case EDIT_EMPLOYEE:
            case DELETE_EMPLOYEE:
            case GET_MANY_EMPLOYEE:
            case GET_ONE_EMPLOYEE:
            case GET_EMPLOYEE_BY_PROFILE:
            case GET_EMPLOYEE_BY_ATTENTION_POINT:
            case GET_EMPLOYEE_BY_BRANCH:
            case LOGIN:
                message = employeeManager.handleRequest(message);
                break;

            case ADD_PROFILE:
            case EDIT_PROFILE:
            case DELETE_PROFILE:
            case GET_MANY_PROFILE:
            case GET_ONE_PROFILE:
                message = profileManager.handleRequest(message);
                break;

            case ADD_ATTENTION_POINT:
            case EDIT_ATTENTION_POINT:
            case DELETE_ATTENTION_POINT:
            case GET_MANY_ATTENTION_POINT:
            case GET_ONE_ATTENTION_POINT:
                message = attentionPointManager.handleRequest(message);
                break;

            case ADD_BRANCH:
            case EDIT_BRANCH:
            case DELETE_BRANCH:
            case GET_MANY_BRANCH:
            case GET_ONE_BRANCH:
                message = branchManager.handleRequest(message);
                break;

            case ADD_CATALOGUE_PROFILE:
            case EDIT_CATALOGUE_PROFILE:
            case DELETE_CATALOGUE_PROFILE:
            case GET_MANY_CATALOGUE_PROFILE:
            case GET_ONE_CATALOGUE_PROFILE:
                message = catalogueProfileManager.handleRequest(message);
                break;

            case ADD_CATALOGUE_ATTENTION_POINT:
            case EDIT_CATALOGUE_ATTENTION_POINT:
            case DELETE_CATALOGUE_ATTENTION_POINT:
            case GET_MANY_CATALOGUE_ATTENTION_POINT:
            case GET_ONE_CATALOGUE_ATTENTION_POINT:
                message = catalogueAttentionPointManager.handleRequest(message);
                break;

            case ADD_CATALOGUE_BRANCH:
            case EDIT_CATALOGUE_BRANCH:
            case DELETE_CATALOGUE_BRANCH:
            case GET_MANY_CATALOGUE_BRANCH:
            case GET_ONE_CATALOGUE_BRANCH:
                message = catalogueBranchManager.handleRequest(message);
                break;

            case ADD_LYSING_INFORMATION:
            case EDIT_LYSING_INFORMATION:
            case DELETE_LYSING_INFORMATION:
            case GET_MANY_LYSING_INFORMATION:
            case GET_ONE_LYSING_INFORMATION:
                message = lysingInformationManager.handleRequest(message);
                break;
        }

        sendMessageToClient(message, os);
    }

    public void sendMessageToClient(Message message, ObjectOutputStream os) {
        try {
            os.writeObject(message);
            os.flush();
        } catch (IOException e) {
            System.out.println("Could not connect to client.");
//            if (message.getType() == Message.MessageType.CALL_NEXT_CAJA
//                    || message.getType() == Message.MessageType.CALL_NEXT_MODULO
//                    || message.getType() == Message.MessageType.CALL_NEXT_GENERIC) {
//                turnManager.sendBackTurn((Turn) message.getObject());
//            }
        }
    }

    public void close() throws IOException {
        is.close();
        os.close();
        socket.close();

        is = null;
        os = null;
        socket = null;
    }
}

