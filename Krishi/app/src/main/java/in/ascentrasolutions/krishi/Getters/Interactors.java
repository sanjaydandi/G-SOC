package in.ascentrasolutions.krishi.Getters;

public class Interactors {

    private final String interactor_name, interactor_id, interactor_profile, user_type;

    public String getInteractor_name() {
        return interactor_name;
    }

    public String getInteractor_id() {
        return interactor_id;
    }

    public String getInteractor_profile() {
        return interactor_profile;
    }

    public String getUser_type() {
        return user_type;
    }

    public Interactors(String interactorName, String interactorId, String interactorProfile, String user_type) {
        interactor_name = interactorName;
        interactor_id = interactorId;
        this.user_type = user_type;
        interactor_profile = interactorProfile;
    }
}
