package com.beaconfire.onboardingservice.service;
import com.beaconfire.onboardingservice.domain.Authentication.RemoteTokenRequest;
import com.beaconfire.onboardingservice.domain.common.ResponseStatus;
import com.beaconfire.onboardingservice.entity.Authentication.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("authentication")
public interface RemoteRegistrationService {

    @PostMapping("/authentication/register/invite")
    ResponseStatus invite(@RequestBody RemoteTokenRequest request);

    @GetMapping("/authentication/test")
    User test();
}