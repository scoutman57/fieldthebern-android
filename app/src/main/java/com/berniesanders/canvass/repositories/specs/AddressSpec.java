/*
 * Made with love by volunteers
 * Copyright 2015 FeelTheBern.org, BernieSanders.com, Coderly, LostPacketSoftware
 * and the volunteers that wrote this code
 * License: GNU AGPLv3 - https://gnu.org/licenses/agpl.html
 */
package com.berniesanders.canvass.repositories.specs;

import com.berniesanders.canvass.models.ApiAddress;
import com.berniesanders.canvass.models.RequestMultipleAddresses;
import com.berniesanders.canvass.models.RequestSingleAddress;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Used to configure/filter a request to the data layer repository
 */
public class AddressSpec {

    RequestSingleAddress singleAddress;
    RequestMultipleAddresses multipleAddresses;

    public AddressSpec() {
    }

    public RequestSingleAddress singleAddress() {
        return this.singleAddress;
    }

    public RequestMultipleAddresses multipleAddresses() {
        return this.multipleAddresses;
    }

    public AddressSpec singleAddress(final RequestSingleAddress singleAddress) {
        this.singleAddress = singleAddress;
        return this;
    }

    public AddressSpec multipleAddresses(final RequestMultipleAddresses multipleAddresses) {
        this.multipleAddresses = multipleAddresses;
        return this;
    }


    /**
     * Retrofit 2 endpoint definition
     */
    public interface AddressEndpoint {

        @GET("addresses")
        Observable<List<ApiAddress>> getMultiple(@Query("latitude") Double latitude,
                                                 @Query("longitude") Double longitude,
                                                 @Query("radius") Integer radius);

        @GET("addresses")
        Observable<ApiAddress> getSingle(@Query("latitude") Double latitude,
                                         @Query("longitude") Double longitude,
                                         @Query("street_1") String street1,
                                         @Query("street_2") String street2,
                                         @Query("city") String city,
                                         @Query("state_code") String state,
                                         @Query("zip_code") String zip
                                         );
    }
}
