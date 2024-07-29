package shop.household.client.get.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import shop.household.client.get.entity.Client;
import shop.household.client.get.mapper.ClientMapper;
import shop.household.client.get.repository.ClientRepository;
import shop.household.model.ClientCreateRequestDto;
import shop.household.model.ClientCreateResponseDto;
import shop.household.model.ErrorDto;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientCreateResponseDto getClients(ClientCreateRequestDto clientDto) throws DataAccessException {
        List<Client> clients;
        Client client = ClientMapper.INSTANCE.requestDtoToClient(clientDto);
        if (Objects.nonNull(client.getId())) {
            return getById(client.getId());
        } else
        if (Objects.nonNull(client.getFirstname())) {
            clients = clientRepository.findByFirstnameContainingIgnoreCase(client.getFirstname());
        } else if (Objects.nonNull(client.getLastname())) {
            clients = clientRepository.findByLastnameContainingIgnoreCase(client.getLastname());
        } else if (Objects.nonNull(client.getEmail())) {
            clients = clientRepository.findByEmailContainingIgnoreCase(client.getEmail());
        } else {
            clients = clientRepository.findAll();
        }
        return new ClientCreateResponseDto()
                .status(true)
                .client(ClientMapper.INSTANCE.clientToClientDto(clients.get(0)));
    }

    public ClientCreateResponseDto getById(Integer id) {
        var client = clientRepository.findById(id);
        if (client.isPresent()) {
            return new ClientCreateResponseDto()
                    .status(true)
                    .client(ClientMapper.INSTANCE.clientToClientDto(client.get()));
        }
        return new ClientCreateResponseDto()
                .status(false)
                .error(new ErrorDto().code(404).message("Client with ID " + id + " not found!"));
    }
}
