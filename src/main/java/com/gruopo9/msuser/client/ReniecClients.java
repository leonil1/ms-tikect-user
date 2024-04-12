package com.gruopo9.msuser.client;

import com.gruopo9.msuser.response.ResponseReniec;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "reniec-client", url = "https://api.apis.net.pe/v2/sunat/") //https://api.apis.net.pe/v2/sunat/ruc?numero=10460278975
public interface ReniecClients {
    @GetMapping("/dni")
    ResponseReniec getinfo(@RequestParam("numero") String numero,
                           @RequestHeader("Authorization") String token);
}
