package az.ingress.mapper;

import az.ingress.dao.entity.AddressEntity;
import az.ingress.model.request.AddressRequest;
import az.ingress.model.response.AddressResponse;

public enum AddressMapper {
    ADDRESS_MAPPER;

    public AddressEntity mapRequestToEntity(AddressRequest addressRequest) {
        return AddressEntity.builder()
                .city(addressRequest.getCity())
                .district(addressRequest.getDistrict())
                .detail(addressRequest.getDetail())
                .build();
    }

    public AddressResponse mapEntityToResponse(AddressEntity address) {
        return AddressResponse.builder()
                .city(address.getCity())
                .district(address.getDistrict())
                .detail(address.getDetail())
                .build();
    }

    public AddressEntity mapResponseToEntity(AddressResponse addressResponse) {
        return AddressEntity.builder()
                .city(addressResponse.getCity())
                .district(addressResponse.getDistrict())
                .detail(addressResponse.getDetail())
                .build();
    }
}