package grey.smarthouse.model;

import java.util.List;
import java.util.UUID;


public interface Repository {

    Relay get(UUID id);

    List<Relay> getAll();

    void insert(Relay relay);

    void update(Relay relay);

    void delete(Relay relay);
}
