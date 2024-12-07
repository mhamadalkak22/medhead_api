package com.medhead.medhead.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.medhead.medhead.Exceptions.DataException;
import com.medhead.medhead.Repositories.AccountTypeRepository;
import com.medhead.medhead.Repositories.AddresseRepository;
import com.medhead.medhead.Repositories.AuthorizationRepository;
import com.medhead.medhead.Repositories.GroupeSpecRepository;
import com.medhead.medhead.Repositories.HospitalRepository;
import com.medhead.medhead.Repositories.LitRepository;
import com.medhead.medhead.Repositories.ReservationRepository;
import com.medhead.medhead.Repositories.SpecialiteRepository;
import com.medhead.medhead.Repositories.UserRepository;
import com.medhead.medhead.RequestObjects.AddAuthorizationRequest;
import com.medhead.medhead.RequestObjects.AddGroupSpecRequest;
import com.medhead.medhead.RequestObjects.AddHopitalRequest;
import com.medhead.medhead.RequestObjects.AddLitRequest;
import com.medhead.medhead.RequestObjects.AddOrgRequest;
import com.medhead.medhead.RequestObjects.AddReservationRequest;
import com.medhead.medhead.RequestObjects.AddSpecialiteRequest;
import com.medhead.medhead.RequestObjects.AddSpecialiteToHospital;
import com.medhead.medhead.RequestObjects.AddUserRequestObject;
import com.medhead.medhead.RequestObjects.CountReservationByHopitalRequest;
import com.medhead.medhead.RequestObjects.CountTotalLitByHopital;
import com.medhead.medhead.RequestObjects.DeleteSpecFromHopitalRequest;
import com.medhead.medhead.RequestObjects.GetHopitalByIdRequest;
import com.medhead.medhead.RequestObjects.GetHopitalByUserIDRequest;
import com.medhead.medhead.RequestObjects.GetLitBySpecAndHopitalRequest;
import com.medhead.medhead.RequestObjects.GetRecommandationRequest;
import com.medhead.medhead.RequestObjects.GetSpecByHopitalID;
import com.medhead.medhead.RequestObjects.GetSpecialiteByGroupeIDResponse;
import com.medhead.medhead.RequestObjects.GetSpecialiteByGroupeId;
import com.medhead.medhead.RequestObjects.GetSpecialiteByNameRequest;
import com.medhead.medhead.RequestObjects.GetUserByUsernameRequest;
import com.medhead.medhead.RequestObjects.SetHopitalAdresseRequest;
import com.medhead.medhead.RequestObjects.ValidateAccountRequest;
import com.medhead.medhead.ResponseObjects.AddReservationResponse;
import com.medhead.medhead.ResponseObjects.BaseResponse;
import com.medhead.medhead.ResponseObjects.CountInvalidAccountsResponse;
import com.medhead.medhead.ResponseObjects.CountLitByHopitalResponse;
import com.medhead.medhead.ResponseObjects.CountReservationByHopitalResponse;
import com.medhead.medhead.ResponseObjects.GetLitBySpecAndHopital;
import com.medhead.medhead.ResponseObjects.GetSpecialiteByNameResponse;
import com.medhead.medhead.ResponseObjects.GetAllGroupesResponse;
import com.medhead.medhead.ResponseObjects.GetAllHopitalResponse;
import com.medhead.medhead.ResponseObjects.GetAllSpecResponse;
import com.medhead.medhead.ResponseObjects.GetHopitalByUserIDResponse;
import com.medhead.medhead.ResponseObjects.GetHospitalByIdResponse;
import com.medhead.medhead.ResponseObjects.GetInvalidAccountsResponse;
import com.medhead.medhead.ResponseObjects.GetSpecsByHopitalIDResponse;
import com.medhead.medhead.ResponseObjects.GetUserByUsernameResponse;
import com.medhead.medhead.ResponseObjects.RecommandationResponse;
import com.medhead.medhead.entities.AppAuthorization;
import com.medhead.medhead.entities.AppUser;
import com.medhead.medhead.entities.Groupe;
import com.medhead.medhead.entities.Hopital;
import com.medhead.medhead.entities.Lit;
import com.medhead.medhead.entities.Recommandation;
import com.medhead.medhead.entities.Reservation;
import com.medhead.medhead.entities.Specialite;
import com.medhead.medhead.utils.Utils;
import com.medhead.medhead.utils.WebSocketEnums;

@Service
public class DataService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private AccountTypeRepository accountTypeRepo;

    @Autowired
    private AuthorizationRepository authRepo;

    @Autowired
    private HospitalRepository hopitalRepo;

    @Autowired
    private SpecialiteRepository specialiteRepo;

    @Autowired
    GroupeSpecRepository groupeSpecRepo;

    @Autowired
    private WebSocketService webSocketService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private AddresseRepository addresseRepo;

    @Autowired
    private LitRepository litRepo;

    @Autowired
    private ReservationRepository reservationRepo;

    private void notifyFrontEnd(String path) {
        webSocketService.sendMessage(path);

    }

    public void addUser(AddUserRequestObject paramsAddUser) {
        AppUser user = new AppUser();

        user.setUsername(paramsAddUser.getUsername());

        user.setPassword(passwordEncoder.encode(paramsAddUser.getPassword()));

        AppAuthorization auth = authRepo.findById(paramsAddUser.getAuthId()).get();

        user.setAuthorizations(new ArrayList<AppAuthorization>());
        user.getAuthorizations().add(auth);

        userRepo.save(user);
    }

    public void addAuthorization(AddAuthorizationRequest paramsAddAuth) {
        AppAuthorization auth = new AppAuthorization();

        auth.setName(paramsAddAuth.getName());

        authRepo.save(auth);
    }

    public String authenticate(String username, String password) throws DataException {

        var dbUser = this.userRepo.findByUsername(username)
                .orElseThrow(() -> new DataException("Le nom d'utilisateur est incorrect"));

        if (!dbUser.isValidated()) {
            throw new DataException("Votre compte n'a pas encore été validé, contactez un administrateur");
        }

        if (passwordEncoder.matches(password, dbUser.getPassword())) {
            return Utils.generateAuthenticationJWT(dbUser);
        }

        throw new DataException("Le nom d'utilisateur ou le mot de passe est incorrect");

    }

    public void addHopital(AddHopitalRequest paramsAddHopital) throws DataException {

        AppUser dbUser = this.userRepo.findById(paramsAddHopital.getUserID())
                .orElseThrow(() -> new DataException("Cet utilisateur n'existe pas"));

        var hopital = new Hopital();
        hopital.setName(paramsAddHopital.getName());
        hopital.setUser(dbUser);

        this.hopitalRepo.save(hopital);

    }

    public GetAllHopitalResponse getAllHopital() {

        var data = this.hopitalRepo.findAll();

        GetAllHopitalResponse response = new GetAllHopitalResponse();

        data.stream().forEach(item -> {
            response.getData().add(item);
        });

        response.getData().forEach(item -> {
            item.getUser().setPassword("********");
        });
        return response;
    }

    public GetHospitalByIdResponse getHospitalById(GetHopitalByIdRequest paramsGetHopitalById) {
        GetHospitalByIdResponse response = new GetHospitalByIdResponse();

        var dbHopital = this.hopitalRepo.findById(paramsGetHopitalById.getId()).get();

        dbHopital.getUser().setPassword("********");

        response.setData(dbHopital);

        return response;
    }

    @Transactional
    public BaseResponse addOrg(AddOrgRequest paramsAddOrg) throws DataException {
        BaseResponse response = new BaseResponse();

        AppUser user = new AppUser();
        user.setUsername(paramsAddOrg.getUsername());
        user.setPassword(passwordEncoder.encode(paramsAddOrg.getPassword()));
        user.setValidated(false);

        // pour les organisations et hopitaux le type de compte est toujours 2
        var accountType = this.accountTypeRepo.findById((long) 2).get();

        this.notifyFrontEnd(WebSocketEnums.UPDATE_INVALID_COUNT.getWebSocketPath());

        user.setAccountType(accountType);

        this.userRepo.save(user);

        Hopital hopital = new Hopital();
        hopital.setName(paramsAddOrg.getOrgName());
        hopital.setUser(user);

        this.hopitalRepo.save(hopital);
        return response;

    }

    public BaseResponse addSpecialite(AddSpecialiteRequest paramsAddSpecialite) {
        BaseResponse response = new BaseResponse();

        Specialite spe = new Specialite();
        spe.setName(paramsAddSpecialite.getName());

        Groupe groupe = this.groupeSpecRepo.findById(paramsAddSpecialite.getGroupeID()).get();
        spe.setGroupe(groupe);
        this.specialiteRepo.save(spe);

        return response;
    }

    public CountInvalidAccountsResponse countInvalidAccounts() {
        CountInvalidAccountsResponse response = new CountInvalidAccountsResponse();
        AppUser user = new AppUser();
        user.setValidated(false);
        Example<AppUser> example = Example.of(user);

        var count = this.userRepo.count(example);

        response.setInvalidCount(count);

        return response;
    }

    public GetInvalidAccountsResponse getInvalidAccounts() {
        GetInvalidAccountsResponse response = new GetInvalidAccountsResponse();
        AppUser user = new AppUser();
        user.setValidated(false);
        Example<AppUser> example = Example.of(user);
        List<AppUser> userData = this.userRepo.findAll(example);

        userData.forEach(item -> {
            item.setPassword("*******");
        });

        response.setInvalidUsers(userData);
        return response;

    }

    public GetUserByUsernameResponse getUserByUsername(GetUserByUsernameRequest paramsGetUserByUsername)
            throws DataException {
        GetUserByUsernameResponse response = new GetUserByUsernameResponse();

        try {
            AppUser dbUser = this.userRepo.findByUsername(paramsGetUserByUsername.getUsername())
                    .orElseThrow(() -> new DataException("Ce compte n'existe pas"));
            dbUser.setPassword("********");
            response.setUser(dbUser);
        } catch (Exception e) {
            response.setExceptionMessage(e.getMessage());
        }

        return response;
    }

    public BaseResponse validateAccount(ValidateAccountRequest paramsValidateAccount) throws DataException {
        BaseResponse response = new BaseResponse();

        var account = this.userRepo.findById(paramsValidateAccount.getAccountId())
                .orElseThrow(() -> new DataException("Ce compte n'existe pas"));

        if (account.isValidated()) {
            throw new DataException("Ce compte a déjà été validé");
        } else {
            account.setValidated(true);
            this.userRepo.save(account);

            this.notifyFrontEnd(WebSocketEnums.UPDATE_INVALID_COUNT.getWebSocketPath());
        }

        return response;
    }

    public GetHopitalByUserIDResponse getHopitalByUserID(GetHopitalByUserIDRequest paramsGetHopital)
            throws DataException {
        GetHopitalByUserIDResponse response = new GetHopitalByUserIDResponse();

        var hopital = this.hopitalRepo.findByUserId(paramsGetHopital.getUserId())
                .orElseThrow(() -> new DataException("Une erreur s'est produite"));

        hopital.getUser().setPassword("********");
        response.setHopital(hopital);

        return response;
    }

    public GetAllSpecResponse getAllSpecialite() {
        GetAllSpecResponse response = new GetAllSpecResponse();
        var data = this.specialiteRepo.findAll();

        response.setSpecialites(data);
        return response;

    }

    public BaseResponse addGroupeSpec(AddGroupSpecRequest paramsAddGroupe) {
        BaseResponse response = new BaseResponse();
        Groupe groupe = new Groupe();
        groupe.setName(paramsAddGroupe.getName());
        this.groupeSpecRepo.save(groupe);
        notifyFrontEnd(WebSocketEnums.UPDATE_GROUPS.getWebSocketPath());

        return response;

    }

    public GetAllGroupesResponse getAllGroupes() {
        GetAllGroupesResponse response = new GetAllGroupesResponse();

        var data = this.groupeSpecRepo.findAll();

        response.setGroupes(data);
        return response;
    }

    public GetSpecialiteByGroupeIDResponse getSpecByGroupID(GetSpecialiteByGroupeId paramsGetSpec) {
        GetSpecialiteByGroupeIDResponse response = new GetSpecialiteByGroupeIDResponse();
        var data = this.specialiteRepo.findByGroupeGroupeID(paramsGetSpec.getGroupeID());
        response.setSpecialites(data);

        return response;
    }

    public BaseResponse AddSpecialiteHopital(AddSpecialiteToHospital paramsAddSpec) {
        BaseResponse response = new BaseResponse();

        var specToAdd = this.specialiteRepo.findById(paramsAddSpec.getSpecialiteID()).get();
        var hopital = this.hopitalRepo.findById(paramsAddSpec.getHopitalID()).get();

        hopital.getSpecialites().add(specToAdd);

        this.hopitalRepo.save(hopital);
        this.notifyFrontEnd(WebSocketEnums.UPDATE_HOSPITAL_SPEC_LIST.getWebSocketPath());
        return response;
    }

    public GetSpecsByHopitalIDResponse getSpecByHopitalID(GetSpecByHopitalID paramsGetSpec) {
        GetSpecsByHopitalIDResponse response = new GetSpecsByHopitalIDResponse();

        var hopital = this.hopitalRepo.findById(paramsGetSpec.getHopitalID()).get();

        var specs = hopital.getSpecialites();
        response.setSpecs(specs);
        return response;
    }

    public BaseResponse deleteSpecFromHopital(DeleteSpecFromHopitalRequest paramsDeleteSpec) {
        BaseResponse resposne = new BaseResponse();

        var hopital = this.hopitalRepo.findById(paramsDeleteSpec.getHopitalID()).get();

        var newSpecsList = hopital.getSpecialites()
                .stream().filter(s -> s.getId() != paramsDeleteSpec.getSpecID())
                .collect(Collectors.toList());

        hopital.setSpecialites(newSpecsList);

        this.hopitalRepo.save(hopital);

        this.notifyFrontEnd(WebSocketEnums.UPDATE_HOSPITAL_SPEC_LIST.getWebSocketPath());

        return resposne;
    }

    @Transactional
    public BaseResponse addAddresseToHopital(SetHopitalAdresseRequest paramsAddAddress) {
        BaseResponse response = new BaseResponse();

        this.addresseRepo.save(paramsAddAddress.getAddresse());

        var hopital = this.hopitalRepo.findById(paramsAddAddress.getIdHopital()).get();

        hopital.setAddresse(paramsAddAddress.getAddresse());

        hopitalRepo.save(hopital);

        return response;
    }

    public BaseResponse addLit(AddLitRequest paramsAddLit) {
        BaseResponse response = new BaseResponse();

        List<Lit> lits = new ArrayList<>();

        for (int i = 0; i < paramsAddLit.getNombre(); i++) {
            Lit lit = new Lit();

            var hopital = this.hopitalRepo.findById(paramsAddLit.getHopitalID()).get();
            var specialite = this.specialiteRepo.findById(paramsAddLit.getSpecialiteID()).get();
            lit.setHopital(hopital);
            lit.setSpecialite(specialite);
            lit.setLibre(paramsAddLit.isLibre());
            lits.add(lit);
        }

        this.litRepo.saveAll(lits);
        this.notifyFrontEnd(WebSocketEnums.UPDATE_LISTE_LITS.getWebSocketPath());

        return response;
    }

    public GetLitBySpecAndHopital getLitsByHopitalAndSpec(GetLitBySpecAndHopitalRequest paramsGetLit) {
        GetLitBySpecAndHopital response = new GetLitBySpecAndHopital();

        var data = this.litRepo.findByHopitalIdAndSpecialiteId(paramsGetLit.getHopitalID(),
                paramsGetLit.getSpecID());

        for (Lit lit : data) {
            lit.getHopital().getUser().setPassword("********");
        }

        response.setLits(data);

        return response;
    }

    public CountLitByHopitalResponse countLitByHopital(CountTotalLitByHopital paramsCount) {
        CountLitByHopitalResponse response = new CountLitByHopitalResponse();
        Lit lit = new Lit();
        var hopital = this.hopitalRepo.findById(paramsCount.getHopitaID()).get();
        lit.setHopital(hopital);
        Example<Lit> example = Example.of(lit);
        var count = this.litRepo.count(example);
        response.setCount(count);

        return response;

    }

    public GetSpecialiteByNameResponse getSpecialiteByName(GetSpecialiteByNameRequest paramsGetSpec) {
        GetSpecialiteByNameResponse response = new GetSpecialiteByNameResponse();

        var data = this.specialiteRepo.findByName(paramsGetSpec.getName());

        response.setSpecs(data);

        return response;

    }

    public RecommandationResponse getRecomandations(GetRecommandationRequest paramsRecomandation) {
        RecommandationResponse response = new RecommandationResponse();
        var specID = paramsRecomandation.getSpecID();
        /*
         * Lit conditionBed = new Lit();
         * conditionBed.setLibre(true);
         * conditionBed.setReserved(false);
         */

        var lits = this.litRepo.findBySpecialiteId(specID);

        lits = lits.stream().filter(item -> item.isLibre() && !item.isReserved())
                .collect(Collectors.toList());

        List<Recommandation> resData = new ArrayList<Recommandation>();

        for (Lit lit : lits) {

            Recommandation rec = new Recommandation();

            Hopital h = lit.getHopital();

            // calcul de la distance;
            double latUser = Double.parseDouble(paramsRecomandation.getLatitude());
            double lonUser = Double.parseDouble(paramsRecomandation.getLongitude());

            double hopitalLat = Double.parseDouble(h.getAddresse().getLat());
            double hopitalLon = Double.parseDouble(h.getAddresse().getLon());

            var radLatUser = Math.toRadians(latUser);
            var radLonUser = Math.toRadians(lonUser);

            var radLatHopital = Math.toRadians(hopitalLat);
            var radLonHopital = Math.toRadians(hopitalLon);

            double dlon = radLonHopital - radLonUser;
            double dlat = radLatHopital - radLatUser;

            double a = Math.pow(Math.sin(dlat / 2), 2)
                    + Math.cos(radLatUser) * Math.cos(radLatHopital)
                            * Math.pow(Math.sin(dlon / 2), 2);

            double c = 2 * Math.asin(Math.sqrt(a));

            double r = 6371;

            rec.setDistance(c * r);

            rec.setHopital(lit.getHopital());
            resData.add(rec);
        }

        Collections.sort(resData);

        response.setRecommandations(resData);
        return response;

    }

    public AddReservationResponse addReservation(AddReservationRequest paramsAddReservation) {

        AddReservationResponse response = new AddReservationResponse();

        var hopitalID = paramsAddReservation.getHopitalID();

        var hopital = this.hopitalRepo.findById(hopitalID).get();

        var specialiteId = paramsAddReservation.getSpecID();
        var specialite = this.specialiteRepo.findById(specialiteId).get();

        List<Lit> lits = this.litRepo.findByHopitalIdAndSpecialiteId(hopitalID, specialiteId);

        lits = lits.stream().filter(item -> item.isLibre() && !item.isReserved()).collect(Collectors.toList());

        var choosenBed = lits.get(0);
        this.notifyFrontEnd(WebSocketEnums.UPDATE_LISTE_LITS.getWebSocketPath());

        if (choosenBed != null) {
            choosenBed.setReserved(true);
            this.litRepo.save(choosenBed);
            response.setLit(choosenBed);

            Reservation r = new Reservation();

            r.setHopital(hopital);
            r.setName(paramsAddReservation.getName());
            r.setLit(choosenBed);
            r.setSpec(specialite);

            this.reservationRepo.save(r);

        }

        this.notifyFrontEnd(WebSocketEnums.UPDATE_LISTE_LITS.getWebSocketPath());

        return response;
    }

    public CountReservationByHopitalResponse countReservation(CountReservationByHopitalRequest paramsCount) {
        CountReservationByHopitalResponse response = new CountReservationByHopitalResponse();

        var count = this.reservationRepo.findByHopitalId(paramsCount.getHopitalID()).size();

        response.setCount(count);

        return response;
    }

}
