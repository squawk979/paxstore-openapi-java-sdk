/*
 * *******************************************************************************
 * COPYRIGHT
 *               PAX TECHNOLOGY, Inc. PROPRIETARY INFORMATION
 *   This software is supplied under the terms of a license agreement or
 *   nondisclosure agreement with PAX  Technology, Inc. and may not be copied
 *   or disclosed except in accordance with the terms in that agreement.
 *
 *      Copyright (C) 2017 PAX Technology, Inc. All rights reserved.
 * *******************************************************************************
 */
package com.pax.market.api.sdk.java.api.terminal;


import com.google.gson.Gson;
import com.pax.market.api.sdk.java.api.BaseThirdPartySysApi;
import com.pax.market.api.sdk.java.api.base.dto.EmptyResponse;
import com.pax.market.api.sdk.java.api.base.dto.PageRequestDTO;
import com.pax.market.api.sdk.java.api.base.dto.Result;
import com.pax.market.api.sdk.java.api.base.request.SdkRequest;
import com.pax.market.api.sdk.java.api.base.request.SdkRequest.RequestMethod;
import com.pax.market.api.sdk.java.api.client.ThirdPartySysApiClient;
import com.pax.market.api.sdk.java.api.constant.Constants;
import com.pax.market.api.sdk.java.api.terminal.dto.*;
import com.pax.market.api.sdk.java.api.terminalGroup.dto.TerminalGroupRequest;
import com.pax.market.api.sdk.java.api.util.EnhancedJsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Locale;

/**
 * @author tanjie
 * @date 2018-07-02
 */
public class TerminalApi extends BaseThirdPartySysApi {
    private static final Logger logger = LoggerFactory.getLogger(TerminalApi.class.getSimpleName());
    protected static final String SEARCH_TERMINAL_URL = "/v1/3rdsys/terminals";

    protected static final String GET_TERMINAL_URL = "/v1/3rdsys/terminals/{terminalId}";

    protected static final String ACTIVE_TERMINAL_URL = "/v1/3rdsys/terminals/{terminalId}/active";

    protected static final String DISABLE_TERMINAL_URL = "/v1/3rdsys/terminals/{terminalId}/disable";

    protected static final String MOVE_TERMINAL_URL = "/v1/3rdsys/terminals/{terminalId}/move";

    protected static final String DELETE_TERMINAL_URL = "/v1/3rdsys/terminals/{terminalId}";

    protected static final String CREATE_TERMINAL_URL = "/v1/3rdsys/terminals";

    protected static final String UPDATE_TERMINAL_URL = "/v1/3rdsys/terminals/{terminalId}";

    protected static final String COPY_TERMINAL_URL = "/v1/3rdsys/terminals/copy";

    protected static final String ADD_TERMINAL_TO_GROUP_URL = "/v1/3rdsys/terminals/groups";

    protected static final String UPDATE_TERMINAL_REMOTE_CONFIG_URL = "/v1/3rdsys/terminals/{terminalId}/config";

    protected static final String GET_TERMINAL_REMOTE_CONFIG_URL = "/v1/3rdsys/terminals/{terminalId}/config";

    protected static final String GET_TERMINAL_PED_STATUS_URL = "/v1/3rdsys/terminals/{terminalId}/ped";

    protected static final String PUSH_TERMINAL_ACTION_URL = "/v1/3rdsys/terminals/{terminalId}/operation";


    public TerminalApi(String baseUrl, String apiKey, String apiSecret) {
        super(baseUrl, apiKey, apiSecret);
    }

    public TerminalApi(String baseUrl, String apiKey, String apiSecret, Locale locale) {
        super(baseUrl, apiKey, apiSecret, locale);
    }

    public Result<TerminalDTO> searchTerminal(int pageNo, int pageSize, TerminalSearchOrderBy orderBy, TerminalStatus status, String snNameTID) {
        return searchTerminal(pageNo, pageSize, orderBy, status, snNameTID, false, false, false);
    }

    public Result<TerminalDTO> searchTerminal(int pageNo, int pageSize, TerminalSearchOrderBy orderBy, String resellerName, String merchantName, TerminalStatus status, String snNameTID) {
        return searchTerminal(pageNo, pageSize, orderBy, resellerName, merchantName, status, snNameTID, false, false, false);
    }

    public Result<TerminalDTO> searchTerminal(int pageNo, int pageSize, TerminalSearchOrderBy orderBy, TerminalStatus status, String snNameTID, boolean includeGeoLocation, boolean includeInstalledApks, boolean includeInstalledFirmware) {
        return searchTerminal(pageNo, pageSize, orderBy, null, null, status, snNameTID, includeGeoLocation, includeInstalledApks, includeInstalledFirmware);
    }

    public Result<TerminalDTO> searchTerminal(int pageNo, int pageSize, TerminalSearchOrderBy orderBy, String resellerName, String merchantName, TerminalStatus status, String snNameTID, boolean includeGeoLocation, boolean includeInstalledApks, boolean includeInstalledFirmware) {
        ThirdPartySysApiClient client = new ThirdPartySysApiClient(getBaseUrl(), getApiKey(), getApiSecret());
        PageRequestDTO page = new PageRequestDTO();
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        if (orderBy != null) {
            page.setOrderBy(orderBy.val);
        }
        List<String> validationErrs = validate(page);
        if (validationErrs.size() > 0) {
            return new Result<>(validationErrs);
        }
        SdkRequest request = getPageRequest(SEARCH_TERMINAL_URL, page);
        if (resellerName != null) {
            request.addRequestParam("resellerName", resellerName);
        }
        if (merchantName != null) {
            request.addRequestParam("merchantName", merchantName);
        }
        if (status != null) {
            request.addRequestParam("status", status.val);
        }
        if (snNameTID != null) {
            request.addRequestParam("snNameTID", snNameTID);
        }
		request.addRequestParam("includeGeoLocation", String.valueOf(includeGeoLocation));
		request.addRequestParam("includeInstalledFirmware", String.valueOf(includeInstalledFirmware));
		request.addRequestParam("includeInstalledApks", String.valueOf(includeInstalledApks));

        TerminalPageResponse terminalPageDTO = EnhancedJsonUtils.fromJson(client.execute(request), TerminalPageResponse.class);

        return new Result<>(terminalPageDTO);
    }


    public Result<TerminalDTO> getTerminal(Long terminalId) {
       return getTerminal(terminalId,false,false);
    }

    public Result<TerminalDTO> getTerminal(Long terminalId, boolean includeDetailInfoList) {
        return getTerminal(terminalId, includeDetailInfoList, false);
    }

    public Result<TerminalDTO> getTerminal(Long terminalId, boolean includeDetailInfoList, boolean includeInstalledApks) {
        logger.debug("terminalId=" + terminalId);
        List<String> validationErrs = validateId(terminalId, "parameter.terminalId.invalid");
        if (validationErrs.size() > 0) {
            return new Result<>(validationErrs);
        }
        ThirdPartySysApiClient client = new ThirdPartySysApiClient(getBaseUrl(), getApiKey(), getApiSecret());
        SdkRequest request = createSdkRequest(GET_TERMINAL_URL.replace("{terminalId}", terminalId.toString()));
        request.addRequestParam("includeDetailInfoList", String.valueOf(includeDetailInfoList));
        request.addRequestParam("includeInstalledApks", String.valueOf(includeInstalledApks));
        TerminalResponseDTO terminalResponse = EnhancedJsonUtils.fromJson(client.execute(request), TerminalResponseDTO.class);
        return new Result<>(terminalResponse);
    }

    public Result<String> activateTerminal(Long terminalId) {
        logger.debug("terminalId=" + terminalId);
        List<String> validationErrs = validateId(terminalId, "parameter.terminalId.invalid");
        if (validationErrs.size() > 0) {
            return new Result<>(validationErrs);
        }
        ThirdPartySysApiClient client = new ThirdPartySysApiClient(getBaseUrl(), getApiKey(), getApiSecret());
        SdkRequest request = createSdkRequest(ACTIVE_TERMINAL_URL.replace("{terminalId}", terminalId.toString()));
        request.setRequestMethod(RequestMethod.PUT);
        EmptyResponse emptyResponse = EnhancedJsonUtils.fromJson(client.execute(request), EmptyResponse.class);
        return new Result<>(emptyResponse);
    }

    public Result<String> disableTerminal(Long terminalId) {
        logger.debug("terminalId=" + terminalId);
        List<String> validationErrs = validateId(terminalId, "parameter.terminalId.invalid");
        if (validationErrs.size() > 0) {
            return new Result<>(validationErrs);
        }
        ThirdPartySysApiClient client = new ThirdPartySysApiClient(getBaseUrl(), getApiKey(), getApiSecret());
        SdkRequest request = createSdkRequest(DISABLE_TERMINAL_URL.replace("{terminalId}", terminalId.toString()));
        request.setRequestMethod(RequestMethod.PUT);
        EmptyResponse emptyResponse = EnhancedJsonUtils.fromJson(client.execute(request), EmptyResponse.class);
        return new Result<>(emptyResponse);
    }

    public Result<String> moveTerminal(Long terminalId, String resellerName, String merchantName) {
        logger.debug("terminalId=" + terminalId);
        List<String> validationErrs = validateId(terminalId, "parameter.terminalId.invalid");
        if (validationErrs.size() > 0) {
            return new Result<>(validationErrs);
        }
        validationErrs = validateStr(resellerName, "parameter.resellerName.invalid");
        if (validationErrs.size() > 0) {
            return new Result<>(validationErrs);
        }
        validationErrs = validateStr(merchantName, "parameter.merchantName.invalid");
        if (validationErrs.size() > 0) {
            return new Result<>(validationErrs);
        }
        ThirdPartySysApiClient client = new ThirdPartySysApiClient(getBaseUrl(), getApiKey(), getApiSecret());
        SdkRequest request = createSdkRequest(MOVE_TERMINAL_URL.replace("{terminalId}", terminalId.toString()));
        request.setRequestMethod(RequestMethod.PUT);
        request.addHeader(Constants.CONTENT_TYPE, Constants.CONTENT_TYPE_JSON);
        TerminalMoveRequest terminalMoveRequest = new TerminalMoveRequest();
        terminalMoveRequest.setResellerName(resellerName);
        terminalMoveRequest.setMerchantName(merchantName);
        request.setRequestBody(new Gson().toJson(terminalMoveRequest, TerminalMoveRequest.class));
        EmptyResponse emptyResponse = EnhancedJsonUtils.fromJson(client.execute(request), EmptyResponse.class);
        return new Result<>(emptyResponse);
    }

    public Result<String> deleteTerminal(Long terminalId) {
        logger.debug("terminalId=" + terminalId);
        List<String> validationErrs = validateId(terminalId, "parameter.terminalId.invalid");
        if (validationErrs.size() > 0) {
            return new Result<>(validationErrs);
        }
        ThirdPartySysApiClient client = new ThirdPartySysApiClient(getBaseUrl(), getApiKey(), getApiSecret());
        SdkRequest request = createSdkRequest(DELETE_TERMINAL_URL.replace("{terminalId}", terminalId.toString()));
        request.setRequestMethod(RequestMethod.DELETE);
        EmptyResponse emptyResponse = EnhancedJsonUtils.fromJson(client.execute(request), EmptyResponse.class);
        return new Result<>(emptyResponse);
    }

    public Result<TerminalDTO> createTerminal(TerminalCreateRequest terminalCreateRequest) {
        List<String> validationErrs = validateCreate(terminalCreateRequest, "parameter.terminalCreateRequest.null");
        if (validationErrs.size() > 0) {
            return new Result<>(validationErrs);
        }
        ThirdPartySysApiClient client = new ThirdPartySysApiClient(getBaseUrl(), getApiKey(), getApiSecret());
        SdkRequest request = createSdkRequest(CREATE_TERMINAL_URL);
        request.setRequestMethod(RequestMethod.POST);
        request.setRequestBody(new Gson().toJson(terminalCreateRequest, TerminalCreateRequest.class));
        request.addHeader(Constants.CONTENT_TYPE, Constants.CONTENT_TYPE_JSON);
        TerminalResponseDTO terminalResponse = EnhancedJsonUtils.fromJson(client.execute(request), TerminalResponseDTO.class);
        return new Result<>(terminalResponse);
    }

    public Result<TerminalDTO> updateTerminal(Long terminalId, TerminalUpdateRequest terminalUpdateRequest) {
        logger.debug("terminalId=" + terminalId);
        List<String> validationErrs = validateUpdate(terminalId, terminalUpdateRequest, "parameter.terminalId.invalid", "parameter.terminalUpdateRequest.null");
        if (validationErrs.size() > 0) {
            return new Result<>(validationErrs);
        }
        ThirdPartySysApiClient client = new ThirdPartySysApiClient(getBaseUrl(), getApiKey(), getApiSecret());
        SdkRequest request = createSdkRequest(UPDATE_TERMINAL_URL.replace("{terminalId}", terminalId.toString()));
        request.setRequestMethod(RequestMethod.PUT);
        request.addHeader(Constants.CONTENT_TYPE, Constants.CONTENT_TYPE_JSON);
        request.setRequestBody(new Gson().toJson(terminalUpdateRequest, TerminalUpdateRequest.class));
        TerminalResponseDTO terminalResponse = EnhancedJsonUtils.fromJson(client.execute(request), TerminalResponseDTO.class);
        return new Result<>(terminalResponse);
    }


    public Result<TerminalDTO> copyTerminal(TerminalCopyRequest terminalCopyRequest) {
        List<String> validationErrs = validateCreate(terminalCopyRequest, "parameter.terminalCopyRequest.null");
        if (validationErrs.size() > 0) {
            return new Result<>(validationErrs);
        }
        logger.debug("terminalId=" + terminalCopyRequest.getTerminalId());
        ThirdPartySysApiClient client = new ThirdPartySysApiClient(getBaseUrl(), getApiKey(), getApiSecret());
        SdkRequest request = createSdkRequest(COPY_TERMINAL_URL);
        request.setRequestMethod(RequestMethod.POST);
        request.addHeader(Constants.CONTENT_TYPE, Constants.CONTENT_TYPE_JSON);
        request.setRequestBody(new Gson().toJson(terminalCopyRequest, TerminalCopyRequest.class));
        TerminalResponseDTO terminalResponse = EnhancedJsonUtils.fromJson(client.execute(request), TerminalResponseDTO.class);
        return new Result<>(terminalResponse);
    }

    public Result<String> batchAddTerminalToGroup(TerminalGroupRequest groupRequest){
        List<String> validationErrs = validateCreate(groupRequest, "parameter.terminalGroupRequest.null");
        if (validationErrs.size() > 0) {
            return new Result<>(validationErrs);
        }
        ThirdPartySysApiClient client = new ThirdPartySysApiClient(getBaseUrl(), getApiKey(), getApiSecret());
        SdkRequest request = createSdkRequest(ADD_TERMINAL_TO_GROUP_URL);
        request.setRequestMethod(RequestMethod.POST);
        request.addHeader(Constants.CONTENT_TYPE, Constants.CONTENT_TYPE_JSON);
        request.setRequestBody(new Gson().toJson(groupRequest, TerminalGroupRequest.class));
        EmptyResponse emptyResponse =  EnhancedJsonUtils.fromJson(client.execute(request), EmptyResponse.class);
        return  new Result<>(emptyResponse);

    }

    public Result<String> updateTerminalConfig(Long terminalId, TerminalConfigUpdateRequest terminalConfigUpdateRequest){
        logger.debug("terminalId=" + terminalId);
        List<String> validationErrs = validateUpdate(terminalId, terminalConfigUpdateRequest, "parameter.terminalId.invalid", "parameter.terminalRemoteConfigRequest.null");
        if (validationErrs.size() > 0) {
            return new Result<>(validationErrs);
        }
        ThirdPartySysApiClient client = new ThirdPartySysApiClient(getBaseUrl(), getApiKey(), getApiSecret());
        SdkRequest request = createSdkRequest(UPDATE_TERMINAL_REMOTE_CONFIG_URL.replace("{terminalId}", terminalId.toString()));
        request.setRequestMethod(RequestMethod.PUT);
        request.addHeader(Constants.CONTENT_TYPE, Constants.CONTENT_TYPE_JSON);
        request.setRequestBody(new Gson().toJson(terminalConfigUpdateRequest, TerminalConfigUpdateRequest.class));
        EmptyResponse emptyResponse =  EnhancedJsonUtils.fromJson(client.execute(request), EmptyResponse.class);
        return  new Result<>(emptyResponse);
    }

    public Result<TerminalConfigDTO> getTerminalConfig(Long terminalId){
        logger.debug("terminalId=" + terminalId);
        List<String> validationErrs = validateId(terminalId, "parameter.terminalId.invalid");
        if (validationErrs.size() > 0) {
            return new Result<>(validationErrs);
        }
        ThirdPartySysApiClient client = new ThirdPartySysApiClient(getBaseUrl(), getApiKey(), getApiSecret());
        SdkRequest request = createSdkRequest(GET_TERMINAL_REMOTE_CONFIG_URL.replace("{terminalId}", terminalId.toString()));
        request.setRequestMethod(RequestMethod.GET);
        request.addHeader(Constants.CONTENT_TYPE, Constants.CONTENT_TYPE_JSON);
        TerminalConfigResponse terminalConfigResponse = EnhancedJsonUtils.fromJson(client.execute(request), TerminalConfigResponse.class);
        return new Result<>(terminalConfigResponse);
    }

    public Result<TerminalPedDTO> getTerminalPed(Long terminalId){
        logger.debug("terminalId=" + terminalId);
        List<String> validationErrs = validateId(terminalId, "parameter.terminalId.invalid");
        if (validationErrs.size() > 0) {
            return new Result<>(validationErrs);
        }
        ThirdPartySysApiClient client = new ThirdPartySysApiClient(getBaseUrl(), getApiKey(), getApiSecret());
        SdkRequest request = createSdkRequest(GET_TERMINAL_PED_STATUS_URL.replace("{terminalId}", terminalId.toString()));
        request.setRequestMethod(RequestMethod.GET);
        request.addHeader(Constants.CONTENT_TYPE, Constants.CONTENT_TYPE_JSON);
        TerminalPedResponse terminalPedResponse = EnhancedJsonUtils.fromJson(client.execute(request), TerminalPedResponse.class);
        return  new Result<>(terminalPedResponse);
    }

    public Result<String> pushCmdToTerminal(Long terminalId, TerminalPushCmd command){
        logger.debug("terminalId=" + terminalId);
        List<String> validationErrs = validateUpdate(terminalId, command, "parameter.terminalId.invalid", "parameter.terminalPushCmdRequest.null");
        if (validationErrs.size() > 0) {
            return new Result<>(validationErrs);
        }
        ThirdPartySysApiClient client = new ThirdPartySysApiClient(getBaseUrl(), getApiKey(), getApiSecret());
        SdkRequest request = createSdkRequest(PUSH_TERMINAL_ACTION_URL.replace("{terminalId}", terminalId.toString()));
        request.setRequestMethod(RequestMethod.POST);
        request.addHeader(Constants.CONTENT_TYPE, Constants.CONTENT_TYPE_JSON);
        request.addRequestParam("command", command.val());
        EmptyResponse emptyResponse =  EnhancedJsonUtils.fromJson(client.execute(request), EmptyResponse.class);
        return  new Result<>(emptyResponse);
    }

    public enum TerminalStatus {
        Active("A"),
        Inactive("P"),
        Suspend("S");
        private final String val;

        TerminalStatus(String status) {
            this.val = status;
        }

        public String val() {
            return this.val;
        }
    }

    public enum TerminalSearchOrderBy {
        Name("name"),
        Tid("tid"),
        SerialNo("serialNo");

        private final String val;

        TerminalSearchOrderBy(String orderBy) {
            this.val = orderBy;
        }

        public String val() {
            return this.val;
        }

    }

    public enum TerminalPushCmd {
        Restart("Restart"),
        Lock("Lock"),
        Unlock("Unlock");


        private final String val;
        TerminalPushCmd(String type) {
            this.val = type;
        }
        public String val(){
            return this.val;
        }
    }
}
