package shop.household.client.get.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import shop.household.client.get.entity.Client;
import shop.household.model.ClientDto;

@Mapper
public interface ClientMapper {
    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);
    ClientDto clientToClientDto(Client client);
}