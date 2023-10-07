package az.ingress.mapper;

import az.ingress.dao.entity.AddressEntity;
import az.ingress.model.request.CreateAddressRequest;
import az.ingress.model.response.AddressResponse;

public enum AddressMapper {
    ADDRESS_MAPPER;

    public AddressEntity buildAddressEntity(CreateAddressRequest createAddressRequest) {
        return AddressEntity.builder()
                .city(createAddressRequest.getCity())
                .district(createAddressRequest.getDistrict())
                .detail(createAddressRequest.getDetail())
                .build();
    }

    public AddressResponse buildAddressResponse(AddressEntity address) {
        return AddressResponse.builder()
                .city(address.getCity())
                .district(address.getDistrict())
                .detail(address.getDetail())
                .build();
    }

    public AddressEntity mapAddressResponseToAddressEntity(AddressResponse addressResponse) {
        return AddressEntity.builder()
                .city(addressResponse.getCity())
                .district(addressResponse.getDistrict())
                .detail(addressResponse.getDetail())
                .build();
    }
}