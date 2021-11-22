package br.com.kodeway.daily.storage;

import br.com.kodeway.daily.DailyPlugin;
import br.com.kodeway.daily.model.Profile;
import br.com.kodeway.daily.sql.dao.ProfileDAO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
public class ProfileStorage {

    protected final DailyPlugin plugin;

    protected ProfileDAO profileDAO;

    @Getter private final Map<UUID, Profile> profiles = new LinkedHashMap<>();

    public void init() {
        profileDAO = new ProfileDAO(plugin.getSqlExecutor());
        profileDAO.createTable();
        for (Profile profile : profileDAO.selectAll()) {
            profiles.put(profile.getUniqueId(), profile);
        }
    }

    public Profile get(UUID uniqueId) {
        return profiles.get(uniqueId);
    }

    public Profile getFromDatabase(UUID uniqueId) {
        return profileDAO.selectOne(uniqueId);
    }

    public void storageNew(Profile profile) {
        if (!profiles.containsKey(profile.getUniqueId())) {
            profileDAO.insertOne(profile);
            addToMemory(profile);
        }
    }

    public void saveAndRemove(Profile profile) {
        save(profile);
        removeFromMemory(profile);
    }

    public void save(Profile profile) {
        profileDAO.insertOne(profile);
    }

    public void addToMemory(Profile profile) {
        if (!profiles.containsKey(profile.getUniqueId())) {
            profiles.put(profile.getUniqueId(), profile);
        }
    }

    public void removeFromMemory(Profile profile) {
        if (profiles.containsKey(profile.getUniqueId())) {
            profiles.remove(profile.getUniqueId(), profile);
        }
    }

    public void delete(Profile profile) {
        if (profiles.containsKey(profile.getUniqueId())) {
            removeFromMemory(profile);
        }

        profileDAO.deleteOne(profile);
    }

}
