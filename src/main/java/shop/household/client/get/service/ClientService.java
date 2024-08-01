package shop.household.client.get.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.household.client.get.entity.Client;
import shop.household.client.get.mapper.ClientMapper;
import shop.household.client.get.repository.ClientRepository;
import shop.household.model.ClientGetRequestDto;
import shop.household.model.ClientGetResponseDto;
import shop.household.model.ErrorDto;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientGetResponseDto getClients(ClientGetRequestDto clientDto) {
        List<Client> clients = new ArrayList<>();
        if (Objects.nonNull(clientDto.getId())) {
            return getById(clientDto.getId());
        } else
        if (Objects.nonNull(clientDto.getFirstname())) {
            clients.addAll(clientRepository.findByFirstnameContainingIgnoreCase(clientDto.getFirstname()));
        } else if (Objects.nonNull(clientDto.getLastname())) {
            clients.addAll(clientRepository.findByLastnameContainingIgnoreCase(clientDto.getLastname()));
        } else if (Objects.nonNull(clientDto.getEmail())) {
            clientRepository.findByEmailIgnoreCase(clientDto.getEmail()).ifPresent(clients::add);
        } else if (Objects.nonNull(clientDto.getCreateAt())) {
            if (Objects.isNull(clientDto.getCreateAt().getBegin())) {
                clientDto.getCreateAt().setBegin(new Timestamp(0));
            }
            if (Objects.isNull(clientDto.getCreateAt().getEnd())) {
                clientDto.getCreateAt().setEnd(new Timestamp(System.currentTimeMillis()));
            }
            clients.addAll(clientRepository.findByCreateAtBetween(clientDto.getCreateAt().getBegin(), clientDto.getCreateAt().getEnd()));
        } else {
            clients.addAll(clientRepository.findAll());
        }
        if (clients.isEmpty()) {
            return new ClientGetResponseDto()
                    .status(false)
                    .clients(null)
                    .error(new ErrorDto().code(404).message("Client not found!"));
        }
        return new ClientGetResponseDto()
                .status(true)
                .clients(clients.stream().map(ClientMapper.INSTANCE::clientToClientDto).toList());
    }

    public ClientGetResponseDto getById(Integer id) {
        var client = clientRepository.findById(id);
        if (client.isPresent()) {
            return new ClientGetResponseDto()
                    .status(true)
                    .clients(List.of(ClientMapper.INSTANCE.clientToClientDto(client.get())));
        }
        return new ClientGetResponseDto()
                .status(false)
                .clients(null)
                .error(new ErrorDto().code(404).message("Client with ID " + id + " not found!"));
    }
}
