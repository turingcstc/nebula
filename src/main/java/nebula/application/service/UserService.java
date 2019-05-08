package nebula.application.service;
import nebula.application.config.Constants;
import nebula.application.domain.Authority;
import nebula.application.domain.User;
import nebula.application.repository.AuthorityRepository;
import nebula.application.repository.UserRepository;
import nebula.application.security.SecurityUtils;
import nebula.application.service.dto.UserDTO;
import nebula.application.service.util.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

//import nebula.application.repository.PersistentTokenRepository;

/**
 * Service class for managing users.
 */
@Service
@Transactional
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

//    private final PersistentTokenRepository persistentTokenRepository;

    private final AuthorityRepository authorityRepository;

    private final CacheManager cacheManager;

//    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, PersistentTokenRepository persistentTokenRepository, AuthorityRepository authorityRepository, CacheManager cacheManager) {
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthorityRepository authorityRepository, CacheManager cacheManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
//        this.persistentTokenRepository = persistentTokenRepository;
        this.authorityRepository = authorityRepository;
        this.cacheManager = cacheManager;
    }

//    public Optional<User> activateRegistration(String key) {
//        log.debug("Activating user for activation key {}", key);
//        return userRepository.findOneByActivationKey(key)
//                .map(user -> {
//                    // activate given user for the registration key.
//                    user.setActivated(true);
//                    user.setActivationKey(null);
//                    this.clearUserCaches(user);
//                    log.debug("Activated user: {}", user);
//                    return user;
//                });
//    }
//
//    public Optional<User> completePasswordReset(String newPassword, String key) {
//        log.debug("Reset user password for reset key {}", key);
//        return userRepository.findOneByResetKey(key)
//                .filter(user -> user.getResetDate().isAfter(Instant.now().minusSeconds(86400)))
//                .map(user -> {
//                    user.setPassword(passwordEncoder.encode(newPassword));
//                    user.setResetKey(null);
//                    user.setResetDate(null);
//                    this.clearUserCaches(user);
//                    return user;
//                });
//    }
//
//    public Optional<User> requestPasswordReset(String mail) {
//        return userRepository.findOneByEmailIgnoreCase(mail)
//                .filter(User::getActivated)
//                .map(user -> {
//                    user.setResetKey(RandomUtil.generateResetKey());
//                    user.setResetDate(Instant.now());
//                    this.clearUserCaches(user);
//                    return user;
//                });
//    }

    public User registerUser(UserDTO userDTO, String password) {

//        userRepository.findOneByLoginID(userDTO.getLoginID().toLowerCase()).ifPresent(existingUser -> {
//            boolean removed = removeNonActivatedUser(existingUser);
//            if (!removed) {
//                throw new LoginIDAlreadyUsedException();
//            }
//        });
//        userRepository.findOneByEmailIgnoreCase(userDTO.getEmail()).ifPresent(existingUser -> {
//            boolean removed = removeNonActivatedUser(existingUser);
//            if (!removed) {
//                throw new EmailAlreadyUsedException();
//            }
//        });

        User newUser = new User();
        String encryptedPassword = passwordEncoder.encode(password);
        newUser.setLoginID(userDTO.getLoginID().toLowerCase());
        // new user gets initially a generated password
        newUser.setPassword(encryptedPassword);
        newUser.setUsername(userDTO.getUserName());
        //newUser.setLastName(userDTO.getLastName());
        newUser.setEmail(userDTO.getEmail().toLowerCase());
        //newUser.setImageUrl(userDTO.getImageUrl());
        //newUser.setLangKey(userDTO.getLangKey());
        // new user is not active
        //newUser.setActivated(false);
        // new user gets registration key
        //newUser.setActivationKey(RandomUtil.generateActivationKey());
        Set<Authority> authorities = new HashSet<>();
// 创建一个用户角色，这里先不适用。
//        authorityRepository.findById(AuthoritiesConstants.USER).ifPresent(authorities::add);
//        newUser.setAuthorities(authorities);

        userRepository.save(newUser);
        this.clearUserCaches(newUser);
        log.debug("Created Information for User: {}", newUser);
        return newUser;
    }

//    private boolean removeNonActivatedUser(User existingUser){
//        if (existingUser.getActivated()) {
//            return false;
//        }
//        userRepository.delete(existingUser);
//        userRepository.flush();
//        this.clearUserCaches(existingUser);
//        return true;
//    }

    public User createUser(UserDTO userDTO) {
        User user = new User();
        user.setLoginID(userDTO.getLoginID().toLowerCase());
        //user.setFirstName(userDTO.getFirstName());
        user.setUsername(userDTO.getUserName());
        user.setEmail(userDTO.getEmail().toLowerCase());
        //user.setImageUrl(userDTO.getImageUrl());
//        if (userDTO.getLangKey() == null) {
//            user.setLangKey(Constants.DEFAULT_LANGUAGE); // default language
//        } else {
//            user.setLangKey(userDTO.getLangKey());
//        }
        String encryptedPassword = passwordEncoder.encode(RandomUtil.generatePassword());
        user.setPassword(encryptedPassword);
//        user.setResetKey(RandomUtil.generateResetKey());
//        user.setResetDate(Instant.now());
//        user.setActivated(true);
// 创建角色这里先不用
//        if (userDTO.getAuthorities() != null) {
//            Set<Authority> authorities = userDTO.getAuthorities().stream()
//                    .map(authorityRepository::findById)
//                    .filter(Optional::isPresent)
//                    .map(Optional::get)
//                    .collect(Collectors.toSet());
//            user.setAuthorities(authorities);
//        }
        userRepository.save(user);
        this.clearUserCaches(user);
        log.debug("Created Information for User: {}", user);
        return user;
    }

    /**
     * Update basic information (first name, last name, email, language) for the current user.
     *
     * @param firstName first name of user.
     * @param lastName  last name of user.
     * @param email     email id of user.
     * @param langKey   language key.
     * @param imageUrl  image URL of user.
     */
    public void updateUser(String firstName, String lastName, String email, String langKey, String imageUrl) {
        SecurityUtils.getCurrentUserLogin()
                .flatMap(userRepository::findOneByLoginID)
                .ifPresent(user -> {
                    //user.setFirstName(firstName);
                    user.setUsername(lastName);
                    user.setEmail(email.toLowerCase());
//                    user.setLangKey(langKey);
//                    user.setImageUrl(imageUrl);
                    this.clearUserCaches(user);
                    log.debug("Changed Information for User: {}", user);
                });
    }

    /**
     * Update all information for a specific user, and return the modified user.
     *
     * @param userDTO user to update.
     * @return updated user.
     */
    public Optional<UserDTO> updateUser(UserDTO userDTO) {
        return Optional.of(userRepository
                .findById(userDTO.getId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(user -> {
                    this.clearUserCaches(user);
                    user.setLoginID(userDTO.getLoginID().toLowerCase());
                    //user.setFirstName(userDTO.getFirstName());
                    user.setUsername(userDTO.getUserName());
                    user.setEmail(userDTO.getEmail().toLowerCase());
//                    user.setImageUrl(userDTO.getImageUrl());
//                    user.setActivated(userDTO.isActivated());
//                    user.setLangKey(userDTO.getLangKey());
                    Set<Authority> managedAuthorities = user.getAuthorities();
                    managedAuthorities.clear();
// 处理角色，这里先不用。
//                    userDTO.getAuthorities().stream()
//                            .map(authorityRepository::findById)
//                            .filter(Optional::isPresent)
//                            .map(Optional::get)
//                            .forEach(managedAuthorities::add);
                    this.clearUserCaches(user);
                    log.debug("Changed Information for User: {}", user);
                    return user;
                })
                .map(UserDTO::new);
    }

    public void deleteUser(String login) {
        userRepository.findOneByLoginID(login).ifPresent(user -> {
            userRepository.delete(user);
            this.clearUserCaches(user);
            log.debug("Deleted User: {}", user);
        });
    }

//    public void changePassword(String currentClearTextPassword, String newPassword) {
//        SecurityUtils.getCurrentUserLogin()
//                .flatMap(userRepository::findOneByLogin)
//                .ifPresent(user -> {
//                    String currentEncryptedPassword = user.getPassword();
//                    if (!passwordEncoder.matches(currentClearTextPassword, currentEncryptedPassword)) {
//                        throw new InvalidPasswordException();
//                    }
//                    String encryptedPassword = passwordEncoder.encode(newPassword);
//                    user.setPassword(encryptedPassword);
//                    this.clearUserCaches(user);
//                    log.debug("Changed password for User: {}", user);
//                });
//    }

    @Transactional(readOnly = true)
    public Page<UserDTO> getAllManagedUsers(Pageable pageable) {
        return userRepository.findAllByLoginIDNot(pageable, Constants.ANONYMOUS_USER).map(UserDTO::new);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthoritiesByLogin(String login) {
        return userRepository.findOneWithAuthoritiesByLoginID(login);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthorities(Long id) {
        return userRepository.findOneWithAuthoritiesById(id);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthorities() {
        return SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneWithAuthoritiesByLoginID);
    }

//    /**
//     * Persistent Token are used for providing automatic authentication, they should be automatically deleted after
//     * 30 days.
//     * <p>
//     * This is scheduled to get fired everyday, at midnight.
//     */
//    @Scheduled(cron = "0 0 0 * * ?")
//    public void removeOldPersistentTokens() {
//        LocalDate now = LocalDate.now();
//        persistentTokenRepository.findByTokenDateBefore(now.minusMonths(1)).forEach(token -> {
//            log.debug("Deleting token {}", token.getSeries());
//            User user = token.getUser();
//            user.getPersistentTokens().remove(token);
//            persistentTokenRepository.delete(token);
//        });
//    }

    /**
     * Not activated users should be automatically deleted after 3 days.
     * <p>
     * This is scheduled to get fired everyday, at 01:00 (am).
     */
//    @Scheduled(cron = "0 0 1 * * ?")
//    public void removeNotActivatedUsers() {
//        userRepository
//                .findAllByActivatedIsFalseAndCreatedDateBefore(Instant.now().minus(3, ChronoUnit.DAYS))
//                .forEach(user -> {
//                    log.debug("Deleting not activated user {}", user.getLoginID());
//                    userRepository.delete(user);
//                    this.clearUserCaches(user);
//                });
//    }

    /**
     * Gets a list of all the authorities.
     * @return a list of all the authorities.
     */
    public List<String> getAuthorities() {
        return authorityRepository.findAll().stream().map(Authority::getName).collect(Collectors.toList());
    }

    private void clearUserCaches(User user) {
        Objects.requireNonNull(cacheManager.getCache(UserRepository.USERS_BY_LOGIN_CACHE)).evict(user.getLoginID());
        Objects.requireNonNull(cacheManager.getCache(UserRepository.USERS_BY_EMAIL_CACHE)).evict(user.getEmail());
    }
}