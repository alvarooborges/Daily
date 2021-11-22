package br.com.kodeway.daily.api;

import br.com.kodeway.daily.storage.ProfileStorage;
import com.google.common.collect.Sets;
import br.com.kodeway.daily.DailyPlugin;
import br.com.kodeway.daily.model.Profile;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DailyRewardAPI {

    @Getter
    private static final DailyRewardAPI instance = new DailyRewardAPI();

    private final ProfileStorage profileStorage = DailyPlugin.getInstance().getProfileManager().getProfileStorage();

    /**
     * Search all profiles to look for one with the entered custom filter.
     *
     * @param filter custom filter to search
     * @return {@link Optional} with the profile found
     */
    public Optional<Profile> findProfileByFilter(Predicate<Profile> filter) {
        return allProfiles().stream()
                .filter(filter)
                .findFirst();
    }

    /**
     * Search all profiles to look for every with the entered custom filter.
     *
     * @param filter custom filter to search
     * @return {@link Set} with all profiles found
     */
    public Set<Profile> findProfilesByFilter(Predicate<Profile> filter) {
        return allProfiles().stream()
                .filter(filter)
                .collect(Collectors.toSet());
    }

    /**
     * Search all profiles to look for every with the entered custom filter.
     *
     * @param owner profile owner name
     * @return {@link Optional} with the profile found
     */
    public Optional<Profile> findProfileByUUID(UUID owner) {
        return allProfiles().stream()
                .filter(profile -> profile.getUniqueId().equals(owner))
                .findFirst();
    }

    /**
     * Search all profiles to look for every with the entered custom filter.
     *
     * @param player an online player
     * @return {@link Optional} with the profile found
     */
    public Optional<Profile> findProfileByPlayer(Player player) {
        return allProfiles().stream()
                .filter(profile -> profile.getUniqueId().equals(player.getUniqueId()))
                .findFirst();
    }

    public Optional<Profile> findProfileByName(String name) {
        return allProfiles().stream()
                .filter(profile -> Bukkit.getOfflinePlayer(profile.getUniqueId()).getName().equals(name))
                .findFirst();
    }

    /**
     * Delete a profile with owner unique Id.
     *
     * @param uniqueId profile owner unique Id
     */
    public void deleteProfileByUUID(UUID uniqueId) {
        Optional<Profile> profile = findProfileByUUID(uniqueId);

        profile.ifPresent(profileStorage::delete);
    }

    /**
     * Retrieve all profiles loaded so far.
     *
     * @return {@link Set} with profiles
     */
    public Set<Profile> allProfiles() {
        return Sets.newLinkedHashSet(profileStorage.getProfiles().values());
    }

}
