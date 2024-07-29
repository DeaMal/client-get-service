package shop.household.client.get.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import shop.household.client.get.entity.Client;
import shop.household.model.ClientCreateRequestDto;
import shop.household.model.ClientDto;

@Mapper
public interface ClientMapper {
    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);

//    @Mapping(target = "id", source = "requestDto.id")
//    @Mapping(target = "firstname", source = "requestDto.firstname")
//    @Mapping(target = "lastname", source = "requestDto.lastname")
//    @Mapping(target = "phone", source = "requestDto.phone")
//    @Mapping(target = "phoneExtra", source = "requestDto.phoneExtra")
//    @Mapping(target = "email", source = "requestDto.email")
//    @Mapping(target = "address", source = "requestDto.address")
//    @Mapping(target = "createAt", source = "requestDto.createAt")
//    @Mapping(target = "updatedAt", source = "requestDto.updatedAt")
//    @Mapping(target = "notes", source = "requestDto.notes")
//    @Mapping(target = "lastOrderDate", source = "requestDto.lastOrderDate")
//    @Mapping(target = "totalOrders", source = "requestDto.totalOrders")
//    @Mapping(target = "totalSpent", source = "requestDto.totalSpent")
    Client requestDtoToClient(ClientCreateRequestDto requestDto);

    @Mapping(target = "phoneExtra", source = "client.phoneExtra")
    ClientDto clientToClientDto(Client client);
}