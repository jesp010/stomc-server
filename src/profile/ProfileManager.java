package profile;

import control.ControlProfile;
import dominio.Profile;
import dominio.Message;
import negocio.exceptions.NonexistentEntityException;

import java.util.List;

public class ProfileManager {

    private static final ControlProfile controlProfile = new ControlProfile();

    public Message handleRequest(Message message) {

        System.out.println("ProfileManager received request of type: " + message.type);

        switch (message.type) {
            case ADD_CATALOGUE_PROFILE:
                return addProfile(message);
            case EDIT_CATALOGUE_PROFILE:
                return editProfile(message);
            case DELETE_CATALOGUE_PROFILE:
                return deleteProfile(message);
            case GET_MANY_CATALOGUE_PROFILE:
                return getManyProfile(message);
//            case GET_ONE_CATALOGUE_PROFILE:
//                return getOneProfile(message);
            default:
                throw new IllegalStateException("Unexpected value: " + message.type);
        }
    }

    private Message addProfile(Message message) {
        Profile profile = (Profile) message.getObject();

        controlProfile.create(profile);

        List<Profile> profiles = controlProfile.findProfileEntities();
        message.setObject(profiles);

        return message;
    }

    private Message editProfile(Message message) {
        Profile profile = (Profile) message.getObject();

        try {
            controlProfile.edit(profile);
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<Profile> profiles = controlProfile.findProfileEntities();
        message.setObject(profiles);

        return message;
    }

    private Message deleteProfile(Message message) {
        Long idProfile = (Long) message.getObject();

        try {
            controlProfile.destroy(idProfile);
        } catch (NonexistentEntityException e) {
            e.printStackTrace();
        }

        List<Profile> profiles = controlProfile.findProfileEntities();
        message.setObject(profiles);

        return message;
    }

    private Message getManyProfile(Message message) {
        List<Profile> profiles = controlProfile.findProfileEntities();
        message.setObject(profiles);

        return message;
    }

//    private Message getOneProfile(Message message) {
//    }

}
