package thesis.http.impl;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import thesis.dto.BlockDto;
import thesis.exception.ServiceException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class RPCBlockRequestServiceImpl extends RESTBlockRequestServiceImpl {

    @Override
    public Integer getCurrentBlockHeight() throws ServiceException {

        int currentBlockHeight;
        // create first part of JSON request object
        JSONObject jsonRequest = new JSONObject();
        jsonRequest.put("id", "block_height_request");
        jsonRequest.put("method", "getblockcount");
        // add parameters to json request
        JSONArray params = new JSONArray();
        jsonRequest.put("params", params);
        // execute json request
        String response;
        try {
            response = httpJsonClient.execute(jsonRequest);

            // parse json object
            Object resultObject = JSONValue.parse(response);

            JSONObject jsonRoot = (JSONObject) resultObject;

            // retrieve blockheight from json object
            currentBlockHeight = Integer.parseInt(jsonRoot.get("result")
                    .toString());

        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }

        return currentBlockHeight;
    }

    @Override
    public List<BlockDto> getBlockHashesByHeight(int start, int end) throws ServiceException {

        List<BlockDto> blockDtos = new ArrayList<>();
        // create first part of JSON request object
        JSONObject jsonRequest = new JSONObject();
        jsonRequest.put("id", "block_request");
        jsonRequest.put("method", "getblockhash");

        // execute json request
        String response;

        for(int height = start; height <= end; height++){

            // add parameters to json request
            JSONArray params = new JSONArray();
            params.add(0, height);
            jsonRequest.put("params", params);
            try {
                response = httpJsonClient.execute(jsonRequest);

                // parse json object
                Object resultObject = JSONValue.parse(response);

                JSONObject jsonRoot = (JSONObject) resultObject;

                // retrieve hash from json object
                String hash = jsonRoot.get("result").toString();

                blockDtos.add(new BlockDto(hash));

            } catch (IOException e) {
                logger.error(e.getMessage());
                e.printStackTrace();
                throw new ServiceException(e.getMessage());
            }
        }

        return blockDtos;
    }
}