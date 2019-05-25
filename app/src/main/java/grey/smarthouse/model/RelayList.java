package grey.smarthouse.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by GREY on 30.04.2018.
 */

public class RelayList {
    private static RelayList sRelayList;
    List<Relay> mRelays;
    public static List<String> mRelayStates;

    private static Repository mRepo;

    public static RelayList getInstance() {
        if (sRelayList == null) {
            sRelayList = new RelayList();
        }
        return sRelayList;
    }

    private RelayList() {

        mRelays = getRelays();
        if (mRelays.size() == 0) {
            for (int i = 0; i < 4; i++) {
                Relay relay = new Relay();
                relay.setMode(2);
                relay.setNumber(i + 1);
                addRelay(relay);
            }
        }
        mRelayStates = new ArrayList<String>();
        for (int i = 0; i < 5; i++) {
            mRelayStates.add(i, "OFF");
        }
    }

    public static void setRepo(Repository repo) {
        mRepo = repo;
    }

    public Relay getRelay(UUID id) {
        Relay relay = mRepo.get(id);
        return relay;
    }

    public List<Relay> getRelays() {
        return mRepo.getAll();
    }

    public void updateRelay(Relay relay) {
        mRepo.update(relay);
    }

    public void addRelay(Relay relay) {
        mRepo.insert(relay);
    }
}
