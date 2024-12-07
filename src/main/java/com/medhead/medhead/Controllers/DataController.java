package com.medhead.medhead.Controllers;

import java.time.Duration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.medhead.medhead.Exceptions.DataException;
import com.medhead.medhead.RequestObjects.AddAuthorizationRequest;
import com.medhead.medhead.RequestObjects.AddGroupSpecRequest;
import com.medhead.medhead.RequestObjects.AddHopitalRequest;
import com.medhead.medhead.RequestObjects.AddLitRequest;
import com.medhead.medhead.RequestObjects.AddOrgRequest;
import com.medhead.medhead.RequestObjects.AddReservationRequest;
import com.medhead.medhead.RequestObjects.AddSpecialiteRequest;
import com.medhead.medhead.RequestObjects.AddSpecialiteToHospital;
import com.medhead.medhead.RequestObjects.AddUserRequestObject;
import com.medhead.medhead.RequestObjects.AuthenticateRequestObject;
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
import com.medhead.medhead.ResponseObjects.AuthenticationResponse;
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
import com.medhead.medhead.ResponseObjects.RecommandationResponse;
import com.medhead.medhead.Services.DataService;
import com.medhead.medhead.utils.Utils;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@CrossOrigin(origins = { "http://127.0.0.1:8081", "http://127.0.0.1:4200" }, allowCredentials = "true")

public class DataController {

    @Autowired
    private DataService dataService;

    private void validateAuth(BaseResponse response, Map<String, String> headers) throws DataException {
        // cette methode verifie le JWT et rattache un nouveau au cas ou il y a eu
        // refresh
        var tokenValidation = Utils.validateJWT(headers);
        if (tokenValidation.getNewAuthenticationToken() != null) {
            response.setNewAuthenticationToken(tokenValidation.getNewAuthenticationToken());
        }
    }

    @PostMapping("/addAuthorization")
    public BaseResponse addAuthorization(@RequestBody AddAuthorizationRequest paramsAddAuth,
            @RequestHeader Map<String, String> headers) {

        BaseResponse response = new BaseResponse();
        try {

            this.validateAuth(response, headers);
            this.dataService.addAuthorization(paramsAddAuth);
        } catch (Exception e) {
            response.setExceptionMessage(e.getMessage());
        }

        return response;

    }

    @PostMapping("/addUser")
    public BaseResponse addUser(@RequestBody AddUserRequestObject paramsAddUser,
            @RequestHeader Map<String, String> headers) {

        BaseResponse response = new BaseResponse();

        try {
            this.validateAuth(response, headers);
            this.dataService.addUser(paramsAddUser);
        } catch (Exception e) {
            response.setExceptionMessage(e.getMessage());
        }
        return response;
    }

    @PostMapping("/addHopital")
    public BaseResponse addHopital(@RequestBody AddHopitalRequest paramsAddHopital,
            @RequestHeader Map<String, String> headers) {
        BaseResponse response = new BaseResponse();

        try {
            this.validateAuth(response, headers);
            this.dataService.addHopital(paramsAddHopital);

        } catch (Exception e) {
            response.setExceptionMessage(e.getMessage());
        }

        return response;
    }

    @GetMapping("/getAllHopital")
    public GetAllHopitalResponse getAllHopital(@RequestHeader Map<String, String> headers,
            HttpServletRequest request) {
        GetAllHopitalResponse response = new GetAllHopitalResponse();

        try {
            this.validateAuth(response, headers);
            response.setData(this.dataService.getAllHopital().getData());
        } catch (Exception e) {
            response.setExceptionMessage(e.getMessage());
        }

        return response;
    }

    @PostMapping("/getHopitalById")
    public GetHospitalByIdResponse getHopitalById(@RequestBody GetHopitalByIdRequest paramsGetHopitalById,
            @RequestHeader Map<String, String> headers) {
        GetHospitalByIdResponse response = new GetHospitalByIdResponse();

        try {
            this.validateAuth(response, headers);
            response.setData(dataService.getHospitalById(paramsGetHopitalById).getData());
        } catch (Exception e) {
            response.setExceptionMessage(e.getMessage());
        }

        return response;
    }

    @PostMapping("/authenticate")
    public AuthenticationResponse authenticate(@RequestBody AuthenticateRequestObject paramsAutenticate,
            HttpServletResponse servletResponse) {

        AuthenticationResponse response = new AuthenticationResponse();

        try {
            response.setToken(
                    this.dataService.authenticate(paramsAutenticate.getUsername(), paramsAutenticate.getPassword()));
        } catch (Exception e) {
            response.setExceptionMessage(e.getMessage());
        }

        if (response.getExceptionMessage() == null) // l'authentifaction est un sucess
        {
            try {

                GetUserByUsernameRequest getUserParams = new GetUserByUsernameRequest();
                getUserParams.setUsername(paramsAutenticate.getUsername());

                var dbUser = this.dataService.getUserByUsername(getUserParams).getUser();

                var refreshToken = Utils.generateRefreshToken(dbUser);
                ResponseCookie cookie = ResponseCookie.from("refresh-token", refreshToken)
                        .path("/")
                        .domain("127.0.0.1")
                        .maxAge(Duration.ofDays(7))
                        .httpOnly(true)
                        .sameSite("Lax")
                        .secure(false) // Doit être true en production avec HTTPS
                        .build();
                servletResponse.setHeader(org.springframework.http.HttpHeaders.SET_COOKIE, cookie.toString());
            } catch (Exception e) {
                response.setExceptionMessage(e.getMessage());
            }

        }

        return response;
    }

    @PostMapping("/addOrg") // ajouter un compte hopital et un objet hopital dans une seule requete
    public BaseResponse addOrg(@RequestBody AddOrgRequest paramsAddOrg, @RequestHeader Map<String, String> headers) {
        BaseResponse response = new BaseResponse();
        try {

            // this.validateAuth(response, headers); //pas besoin d'authentification car il
            // s'agit de creer un compte
            response = this.dataService.addOrg(paramsAddOrg);

        } catch (Exception e) {
            response.setExceptionMessage(e.getMessage());
        }

        return response;
    }

    @PostMapping("/addSpecialite")
    public BaseResponse addSpecialite(@RequestBody AddSpecialiteRequest paramsAddSpecialite,
            @RequestHeader Map<String, String> headers) {
        BaseResponse response = new BaseResponse();
        try {
            this.validateAuth(response, headers);
            response = this.dataService.addSpecialite(paramsAddSpecialite);
        } catch (Exception e) {
            response.setExceptionMessage(e.getMessage());
        }

        return response;
    }

    @GetMapping("/countInvalid")
    public CountInvalidAccountsResponse countInvalidAccountsResponse(@RequestHeader Map<String, String> headers,
            HttpServletRequest request) {

        CountInvalidAccountsResponse response = new CountInvalidAccountsResponse();
        try {
            validateAuth(response, headers);
            response = this.dataService.countInvalidAccounts();
        } catch (Exception e) {
            response.setExceptionMessage(e.getMessage());
        }

        return response;
    }

    @PutMapping("/validateAccount")
    public BaseResponse validateAccount(@RequestBody ValidateAccountRequest paramsValidateAccount,
            @RequestHeader Map<String, String> headers) {
        BaseResponse response = new BaseResponse();

        try {
            validateAuth(response, headers);

            this.dataService.validateAccount(paramsValidateAccount);
        } catch (Exception e) {
            response.setExceptionMessage(e.getMessage());
        }

        return response;
    }

    @GetMapping("/getInvalidAccounts")
    public GetInvalidAccountsResponse getInvalidAccounts(@RequestHeader Map<String, String> headers) {
        GetInvalidAccountsResponse response = new GetInvalidAccountsResponse();

        try {
            validateAuth(response, headers);
            response = this.dataService.getInvalidAccounts();
        } catch (Exception e) {
            response.setExceptionMessage(e.getMessage());
        }

        return response;
    }

    @PostMapping("/getHopitalByUserID")
    public GetHopitalByUserIDResponse getHopitalByUserID(
            @RequestHeader Map<String, String> headers,
            @RequestBody GetHopitalByUserIDRequest paramsGetHopital) {
        GetHopitalByUserIDResponse response = new GetHopitalByUserIDResponse();

        try {
            validateAuth(response, headers);
            response = this.dataService.getHopitalByUserID(paramsGetHopital);

        } catch (Exception e) {
            response.setExceptionMessage(e.getMessage());
        }

        return response;
    }

    @GetMapping("/getAllSpecialites")
    public GetAllSpecResponse getAllSpecialite(@RequestHeader Map<String, String> headers) {

        GetAllSpecResponse response = new GetAllSpecResponse();

        try {
            // validateAuth(response, headers);
            response = this.dataService.getAllSpecialite();
        } catch (Exception e) {
            response.setExceptionMessage(e.getMessage());
        }

        return response;

    }

    @PostMapping("/addGroupeSpec")
    public BaseResponse addGroupeSpec(@RequestBody AddGroupSpecRequest paramsAddGroupe,
            @RequestHeader Map<String, String> headers) {
        BaseResponse response = new BaseResponse();
        try {
            validateAuth(response, headers);
            response = this.dataService.addGroupeSpec(paramsAddGroupe);
        } catch (Exception e) {
            response.setExceptionMessage(e.getMessage());
        }

        return response;

    }

    @GetMapping("/getAllGroupes")
    public GetAllGroupesResponse getAllGroupes(@RequestHeader Map<String, String> headers) {
        GetAllGroupesResponse response = new GetAllGroupesResponse();
        try {
            this.validateAuth(response, headers);
            response = this.dataService.getAllGroupes();

            System.out.println(response.getGroupes().size());
        } catch (Exception e) {
            response.setExceptionMessage(e.getMessage());
        }

        return response;
    }

    @PostMapping("/getSpecByGroupId")
    public GetSpecialiteByGroupeIDResponse getSpecByGroupID(@RequestBody GetSpecialiteByGroupeId paramsGetSpec,
            @RequestHeader Map<String, String> headers) {

        GetSpecialiteByGroupeIDResponse response = new GetSpecialiteByGroupeIDResponse();

        try {
            this.validateAuth(response, headers);
            response = this.dataService.getSpecByGroupID(paramsGetSpec);

        } catch (Exception e) {
            response.setExceptionMessage(e.getMessage());
        }

        return response;

    }

    @PostMapping("/addSpecialiteToHopital")
    public BaseResponse addSpecToHopital(@RequestBody AddSpecialiteToHospital paramsAddSpec,
            @RequestHeader Map<String, String> headers) {

        BaseResponse response = new BaseResponse();

        try {
            this.validateAuth(response, headers);
            response = this.dataService.AddSpecialiteHopital(paramsAddSpec);
        } catch (Exception e) {
            response.setExceptionMessage(e.getMessage());
        }
        return response;
    }

    @PostMapping("/getSpecByHopitalID")
    public GetSpecsByHopitalIDResponse getSpecByHopitalID(@RequestBody GetSpecByHopitalID paramsGetSpec,
            @RequestHeader Map<String, String> headers) {
        GetSpecsByHopitalIDResponse response = new GetSpecsByHopitalIDResponse();
        try {
            this.validateAuth(response, headers);
            response = this.dataService.getSpecByHopitalID(paramsGetSpec);

        } catch (Exception e) {
            response.setExceptionMessage(e.getMessage());
        }
        return response;

    }

    @PostMapping("/deleteSpecFromHopital")
    public BaseResponse deleteSpecFromHopital(@RequestBody DeleteSpecFromHopitalRequest paramsDeleteSpec,
            @RequestHeader Map<String, String> headers) {
        BaseResponse response = new BaseResponse();

        try {
            this.validateAuth(response, headers);

            this.dataService.deleteSpecFromHopital(paramsDeleteSpec);

        } catch (Exception e) {
            response.setExceptionMessage(e.getMessage());
        }

        return response;
    }

    @PostMapping("/setHopitalAddresse")
    public BaseResponse setHopitalAddress(@RequestBody SetHopitalAdresseRequest paramsSetAddresse,
            @RequestHeader Map<String, String> headers) {
        BaseResponse response = new BaseResponse();

        try {
            this.validateAuth(response, headers);
            this.dataService.addAddresseToHopital(paramsSetAddresse);

        } catch (Exception e) {
            response.setExceptionMessage(e.getMessage());
        }

        return response;
    }

    @PostMapping("/addLit")
    public BaseResponse ajouterLit(@RequestBody AddLitRequest paramsAddLit,
            @RequestHeader Map<String, String> headers) {
        BaseResponse response = new BaseResponse();

        try {
            this.validateAuth(response, headers);
            response = this.dataService.addLit(paramsAddLit);

        } catch (Exception e) {
            response.setExceptionMessage(e.getMessage());
        }

        return response;

    }

    @PostMapping("/getLitsBySpecAndHopital")
    public GetLitBySpecAndHopital getLitBySpecAndHopital(@RequestBody GetLitBySpecAndHopitalRequest paramsGetLit,
            @RequestHeader Map<String, String> headers) {

        GetLitBySpecAndHopital response = new GetLitBySpecAndHopital();

        try {
            this.validateAuth(response, headers);
            response = this.dataService.getLitsByHopitalAndSpec(paramsGetLit);
        } catch (Exception e) {
            response.setExceptionMessage(e.getMessage());

        }

        return response;

    }

    @PostMapping("/countLitByHopital")
    public CountLitByHopitalResponse countLitByHopital(
            @RequestBody CountTotalLitByHopital paramsCount,
            @RequestHeader Map<String, String> headers) {
        CountLitByHopitalResponse response = new CountLitByHopitalResponse();
        try {

            this.validateAuth(response, headers);
            response = this.dataService.countLitByHopital(paramsCount);

        } catch (Exception e) {
            response.setExceptionMessage(e.getMessage());
        }

        return response;
    }

    @PostMapping("/getSpecialiteByName")
    public GetSpecialiteByNameResponse getSpecialiteByName(@RequestBody GetSpecialiteByNameRequest paramsGetSpec,
            @RequestHeader Map<String, String> headers) {

        GetSpecialiteByNameResponse response = new GetSpecialiteByNameResponse();

        try {

            this.validateAuth(response, headers);
            response = this.dataService.getSpecialiteByName(paramsGetSpec);

        } catch (Exception e) {
            response.setExceptionMessage(e.getMessage());
        }

        return response;

    }

    @PostMapping("/getRecommandations")
    public RecommandationResponse getRecommandation(@RequestBody GetRecommandationRequest paramsGetRecommandation) {
        RecommandationResponse response = new RecommandationResponse();

        try {

            response = this.dataService.getRecomandations(paramsGetRecommandation);

        } catch (Exception e) {
            response.setExceptionMessage(e.getMessage());
        }

        return response;
    }

    @PostMapping("/AddReservation")
    public AddReservationResponse addReservation(@RequestBody AddReservationRequest paramsAddReservation,
            @RequestHeader Map<String, String> headers) {

        AddReservationResponse response = new AddReservationResponse();

        try {
            response = this.dataService.addReservation(paramsAddReservation);
            // fixed

        } catch (Exception e) {
            response.setExceptionMessage(e.getMessage());
        }

        return response;

    }

    @PostMapping("/CountReservationByHopital")
    public CountReservationByHopitalResponse countReservation(@RequestBody CountReservationByHopitalRequest paramsCount,
            @RequestHeader Map<String, String> headers) {
        CountReservationByHopitalResponse response = new CountReservationByHopitalResponse();

        try {
            this.validateAuth(response, headers);
            response = this.dataService.countReservation(paramsCount);

        } catch (Exception e) {
            response.setExceptionMessage(e.getMessage());
        }

        return response;
    }


   
    

}
