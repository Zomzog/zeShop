package bzh.zomzog.zeshop.auth.web.rest;

import bzh.zomzog.zeshop.auth.domain.Account;
import bzh.zomzog.zeshop.auth.exception.LoginAlreadyInUseException;
import bzh.zomzog.zeshop.auth.service.AccountService;
import bzh.zomzog.zeshop.auth.service.dto.AccountDTO;
import bzh.zomzog.zeshop.auth.service.dto.ManagedAccountDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
public class AccountResource {

    private final Logger log = LoggerFactory.getLogger(AccountResource.class);

    private final AccountService accountService;

    public AccountResource(final AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * POST /register : register the user.
     *
     * @param managedAccountDTO the managed user View Model
     * @return the ResponseEntity with status 201 (Created) if the user is
     * registered or 400 (Bad Request) if the login or e-mail is already
     * in use
     * @throws LoginAlreadyInUseException
     */
    @PostMapping(path = "/register", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<AccountDTO> registerAccount(@Valid @RequestBody final ManagedAccountDTO managedAccountDTO)
            throws LoginAlreadyInUseException {

        if (managedAccountDTO.getId() != null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        // Lowercase the user login before comparing with database
        final HttpHeaders textPlainHeaders = new HttpHeaders();
        textPlainHeaders.setContentType(MediaType.TEXT_PLAIN);

        final AccountDTO result = this.accountService.registerAccount(managedAccountDTO);

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    /**
     * GET /activate : activate the registered user.
     *
     * @param key the activation key
     * @return the ResponseEntity with status 200 (OK) and the activated user in
     * body, or status 500 (Internal Server Error) if the user couldn't
     * be activated
     */
    @PostMapping("/activate")
    public ResponseEntity<String> activateAccount(@RequestParam(value = "key") final String key) {
        final Account account = this.accountService.activateRegistration(key);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * GET /authenticate : check if the user is authenticated, and return its
     * login.
     *
     * @param request the HTTP request
     * @return the login if the user is authenticated
     */
    @GetMapping("/authenticate")
    public String isAuthenticated(final HttpServletRequest request) {
        this.log.debug("REST request to check if the current user is authenticated");
        return request.getRemoteUser();
    }

    /**
     * GET /account : get the current user.
     *
     * @return the ResponseEntity with status 200 (OK) and the current user in
     *         body, or status 500 (Internal Server Error) if the user couldn't
     *         be returned
     */
    // @GetMapping("/account")
    // public ResponseEntity<UserDTO> getAccount() {
    // return Optional.ofNullable(userService.getUserWithAuthorities())
    // .map(user -> new ResponseEntity<>(new UserDTO(user), HttpStatus.OK))
    // .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    // }

    /**
     * POST /account : update the current user information.
     *
     * @param userDTO
     *            the current user information
     * @return the ResponseEntity with status 200 (OK), or status 400 (Bad
     *         Request) or 500 (Internal Server Error) if the user couldn't be
     *         updated
     */
    // @PostMapping("/account")
    // public ResponseEntity<String> saveAccount(@Valid @RequestBody final
    // UserDTO userDTO) {
    // final Optional<User> existingUser =
    // userRepository.findOneByEmail(userDTO.getEmail());
    // if (existingUser.isPresent() &&
    // (!existingUser.get().getLogin().equalsIgnoreCase(userDTO.getLogin()))) {
    // return ResponseEntity.badRequest()
    // .headers(HeaderUtil.createFailureAlert("user-management", "emailexists",
    // "Email already in use"))
    // .body(null);
    // }
    // return
    // userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).map(u
    // -> {
    // userService.updateUser(userDTO.getFirstName(), userDTO.getLastName(),
    // userDTO.getEmail(),
    // userDTO.getLangKey());
    // return new ResponseEntity<String>(HttpStatus.OK);
    // }).orElseGet(() -> new
    // ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    // }

    /**
     * POST /account/change_password : changes the current user's password
     *
     * @param password
     *            the new password
     * @return the ResponseEntity with status 200 (OK), or status 400 (Bad
     *         Request) if the new password is not strong enough
     */
    // @PostMapping(path = "/account/change_password", produces =
    // MediaType.TEXT_PLAIN_VALUE)
    // public ResponseEntity<String> changePassword(@RequestBody final String
    // password) {
    // if (!checkPasswordLength(password)) {
    // return new ResponseEntity<>("Incorrect password",
    // HttpStatus.BAD_REQUEST);
    // }
    // userService.changePassword(password);
    // return new ResponseEntity<>(HttpStatus.OK);
    // }

    /**
     * POST /account/reset_password/init : Send an e-mail to reset the password
     * of the user
     *
     * @param mail
     *            the mail of the user
     * @return the ResponseEntity with status 200 (OK) if the e-mail was sent,
     *         or status 400 (Bad Request) if the e-mail address is not
     *         registered
     */
    // @PostMapping(path = "/account/reset_password/init", produces =
    // MediaType.TEXT_PLAIN_VALUE)
    // public ResponseEntity<String> requestPasswordReset(@RequestBody final
    // String mail) {
    // return accountService.requestPasswordReset(mail).map(user -> {
    // mailService.sendPasswordResetMail(user);
    // return new ResponseEntity<>("e-mail was sent", HttpStatus.OK);
    // }).orElse(new ResponseEntity<>("e-mail address not registered",
    // HttpStatus.BAD_REQUEST));
    // }

    /**
     * POST /account/reset_password/finish : Finish to reset the password of the
     * user
     *
     * @param keyAndPassword
     *            the generated key and the new password
     * @return the ResponseEntity with status 200 (OK) if the password has been
     *         reset, or status 400 (Bad Request) or 500 (Internal Server Error)
     *         if the password could not be reset
     */
    // @PostMapping(path = "/account/reset_password/finish", produces =
    // MediaType.TEXT_PLAIN_VALUE)
    // public ResponseEntity<String> finishPasswordReset(@RequestBody final
    // KeyAndPasswordVM keyAndPassword) {
    // if (!checkPasswordLength(keyAndPassword.getNewPassword())) {
    // return new ResponseEntity<>("Incorrect password",
    // HttpStatus.BAD_REQUEST);
    // }
    // return userService.completePasswordReset(keyAndPassword.getNewPassword(),
    // keyAndPassword.getKey())
    // .map(user -> new ResponseEntity<String>(HttpStatus.OK))
    // .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    // }
    //
    // private boolean checkPasswordLength(final String password) {
    // return !StringUtils.isEmpty(password) && password.length() >=
    // ManagedUserVM.PASSWORD_MIN_LENGTH
    // && password.length() <= ManagedUserVM.PASSWORD_MAX_LENGTH;
    // }
}
